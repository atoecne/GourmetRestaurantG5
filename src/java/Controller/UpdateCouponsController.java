/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.CouponDAO;
import Model.CouponsModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 *
 * @author Nguyen Thanh Huy - CE171915
 */
@WebServlet(name = "updateCoupons", urlPatterns = {"/updateC"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class UpdateCouponsController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet updateCoupons</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updateCoupons at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CouponDAO dao = new CouponDAO();
        try {
            String couponID = request.getParameter("couponID");
            String couponDescription = request.getParameter("couponDescription");
            String discountType = request.getParameter("discountType");
            String discountValueRaw = request.getParameter("discountValue");
            String startDateRaw = request.getParameter("startDate");
            String endDateRaw = request.getParameter("endDate");
            String activeRaw = request.getParameter("active");
            boolean validInput = true;

            CouponsModel currentCoupon = dao.checkCouponID(couponID);

            if (couponDescription == null || couponDescription.isEmpty()) {
                couponDescription = currentCoupon.getCouponDescription();
            }
            if (discountType == null || discountType.isEmpty()) {
                discountType = currentCoupon.getDiscountType();
            }

            int discountValue = currentCoupon.getDiscountValue();
            if (discountValueRaw != null && !discountValueRaw.isEmpty()) {
                try {
                    discountValue = Integer.parseInt(discountValueRaw);
                    if ("VND".equals(discountType) && discountValue < 0) {
                        validInput = false;
                        request.setAttribute("Message", "Discount Value must be non-negative for VND type.");
                    } else if ("%".equals(discountType) && (discountValue <= 0 || discountValue > 100)) {
                        validInput = false;
                        request.setAttribute("Message", "Discount Value must be between 1 and 100 for % type.");
                    }
                } catch (NumberFormatException e) {
                    validInput = false;
                    request.setAttribute("Message", "Invalid Discount Value.");
                }
            }

            Date startDate = Date.valueOf(currentCoupon.getStartDate());
            if (startDateRaw != null && !startDateRaw.isEmpty()) {
                startDate = Date.valueOf(startDateRaw);
            }

            Date endDate = Date.valueOf(currentCoupon.getEndDate());
            if (endDateRaw != null && !endDateRaw.isEmpty()) {
                endDate = Date.valueOf(endDateRaw);
                if (endDate.before(startDate)) {
                    validInput = false;
                    request.setAttribute("Message", "End Date cannot be before Start Date.");
                }
            }

            int active = currentCoupon.getActive();
            if (activeRaw != null && !activeRaw.isEmpty()) {
                try {
                    active = Integer.parseInt(activeRaw);
                    if (active != 0 && active != 1) {
                        validInput = false;
                        request.setAttribute("Message", "Active must be either 0 or 1.");
                    }
                } catch (NumberFormatException e) {
                    validInput = false;
                    request.setAttribute("Message", "Invalid Active value.");
                }
            }

            Part file = request.getPart("couponImg");
            String imgFileName = file.getSubmittedFileName();
            String uploadPath = getServletContext().getRealPath("/image/") + File.separator + imgFileName;
            if (!imgFileName.isEmpty()) {
                try ( FileOutputStream fos = new FileOutputStream(uploadPath);  InputStream is = file.getInputStream()) {
                    byte[] data = new byte[is.available()];
                    is.read(data);
                    fos.write(data);
                } catch (IOException e) {
                    request.setAttribute("Message", "Image upload error.");
                    validInput = false;
                }
            } else {
                imgFileName = currentCoupon.getCouponImg();
            }

            if (validInput) {
                CouponsModel updatedCoupon = new CouponsModel();
                updatedCoupon.setCouponID(couponID);
                updatedCoupon.setCouponImg(imgFileName);
                updatedCoupon.setCouponDescription(couponDescription);
                updatedCoupon.setDiscountType(discountType);
                updatedCoupon.setDiscountValue(discountValue);
                updatedCoupon.setStartDate(startDate.toLocalDate());
                updatedCoupon.setEndDate(endDate.toLocalDate());
                updatedCoupon.setActive(active);

                if (dao.updateCoupon(updatedCoupon)) {
                    response.sendRedirect("showC");
                } else {
                    request.setAttribute("Message", "Update coupon failed.");
                    request.getRequestDispatcher("Views/Coupon/UpdateCoupons.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("couponDescription", couponDescription);
                request.setAttribute("discountType", discountType);
                request.setAttribute("discountValue", discountValueRaw);
                request.setAttribute("startDate", startDateRaw);
                request.setAttribute("endDate", endDateRaw);
                request.setAttribute("active", activeRaw);
                request.getRequestDispatcher("Views/Coupon/UpdateCoupons.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("Message", "Invalid input.");
            request.getRequestDispatcher("Views/Coupon/UpdateCoupons.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
