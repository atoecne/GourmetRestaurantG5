package Controller;

import DAO.CouponDAO;
import Model.CouponsModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet(name = "createCoupons", urlPatterns = {"/createCoupons"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class CreateCouponsController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet createCoupons</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet createCoupons at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CouponDAO cpd = new CouponDAO();

        try {
            String couponID = request.getParameter("couponID");
            String couponDescription = request.getParameter("couponDescription");
            String discountType = request.getParameter("discountType");
            int discountValue = Integer.parseInt(request.getParameter("discountValue"));
            LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
            LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));
            int active = Integer.parseInt(request.getParameter("active"));
            Part file = request.getPart("couponImg");
            String imgFileName = file.getSubmittedFileName();
            System.out.println("Name img: " + imgFileName);
            String upload = getServletContext().getRealPath("/image/");
            String uploadPath = upload + File.separator + imgFileName;

            try {
                FileOutputStream fos = new FileOutputStream(uploadPath);
                InputStream is = file.getInputStream();

                byte[] data = new byte[is.available()];
                is.read(data);
                fos.write(data);
                fos.close();
            } catch (IOException e) {
                System.out.println(e);
                System.out.println("IMG Err");
            }

         if (discountType.equalsIgnoreCase("%")) {
            if (discountValue <= 0 || discountValue > 100) {
                request.setAttribute("Mess", "Discount not less than 0% or greater than 100%");
                request.getRequestDispatcher("Views/Coupon/CreateCoupons.jsp").forward(request, response);
                return;
            }
        } else if (discountType.equalsIgnoreCase("VND")) {
            if (discountValue <= 0) {
                request.setAttribute("Mess", "Discount not less than 0");
                request.getRequestDispatcher("Views/Coupon/CreateCoupons.jsp").forward(request, response);
                return;
            }
        }

        if (endDate.isBefore(startDate)) {
            request.setAttribute("Mess", "Invalid date range");
            request.getRequestDispatcher("Views/Coupon/CreateCoupons.jsp").forward(request, response);
            return;
        }

        CouponsModel cNew = cpd.checkCouponID(couponID);
        if (cNew == null) {
            cNew = new CouponsModel(couponID, imgFileName, couponDescription, discountType, discountValue, startDate, endDate, active);
            boolean isCreated = cpd.createCoupons(cNew);
            if (isCreated) {
                request.setAttribute("Mess", "Create Coupon Successful");
                response.sendRedirect("showC");
            } else {
                request.setAttribute("Mess", "Create Coupon Failed");
                request.getRequestDispatcher("Views/Coupon/CreateCoupons.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("Mess", "CouponID already exists");
            request.getRequestDispatcher("Views/Coupon/CreateCoupons.jsp").forward(request, response);
        }

        } catch (Exception e) {
            System.out.println("Create Coupon error");
            e.printStackTrace();
            request.setAttribute("Mess", "An error occurred while creating the coupon. Please try again.");
            request.getRequestDispatcher("Views/Coupon/CreateCoupons.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
