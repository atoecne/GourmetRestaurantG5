/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.CouponDAO;
import Model.CouponsModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Imtha
 */
@WebServlet(name = "SearchCouponController", urlPatterns = {"/SearchC"})
public class SearchCouponController extends HttpServlet {

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
            out.println("<title>Servlet SearchCouponController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchCouponController at " + request.getContextPath() + "</h1>");
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
        String search = request.getParameter("txtSearch");
        System.out.println("search" + search);
        System.out.println("ggg");
        String searchErr;
        CouponDAO tbd = new CouponDAO();
        List<CouponsModel> list = tbd.searchCoupon(search);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        for (CouponsModel c : list) {
            out.println("<tr id='infoF'>");
            out.println("<td><img src='image/" + c.getCouponImg() + "' width='50'></td>");
            out.println("<td>" + c.getCouponID() + "</td>");
            out.println("<td class='description-cell'>" + c.getCouponDescription() + "</td>");
            out.println("<td>" + c.getDiscountType() + "</td>");
            out.println("<td>" + c.getDiscountValue() + "</td>");
            out.println("<td>" + c.getStartDate() + "</td>");
            out.println("<td>" + c.getEndDate() + "</td>");
            out.println("<td>" + c.getActive() + "</td>");
            out.println("<td>");
            out.println("<form action='updateCoupons.jsp' method='get' style='display:inline;'>");
            out.println("<input type='hidden' name='couponID' value='" + c.getCouponID() + "'>");
            out.println("<button type='submit' class='btn btn-link'>"
                    + "<svg width=\"24px\" height=\"24px\" viewBox=\"0 0 24.00 24.00\" "
                    + "xmlns=\"http://www.w3.org/2000/svg\" fill=\"#ddd6c5\" stroke=\"#ddd6c5\" "
                    + "transform=\"rotate(0)\"><g id=\"SVGRepo_bgCarrier\" stroke-width=\"0\"></g>"
                    + "<g id=\"SVGRepo_tracerCarrier\" stroke-linecap=\"round\" stroke-linejoin=\"round\" "
                    + "stroke=\"#CCCCCC\" stroke-width=\"0.096\"></g><g id=\"SVGRepo_iconCarrier\">"
                    + " <title></title> <g id=\"Complete\"> <g id=\"edit\"> <g> "
                    + "<path d=\"M20,16v4a2,2,0,0,1-2,2H4a2,2,0,0,1-2-2V6A2,2,0,0,1,4,4H8\" "
                    + "fill=\"none\" stroke=\"#ddd6c5\" stroke-linecap=\"round\" stroke-linejoin=\"round\" "
                    + "stroke-width=\"2\"></path> <polygon fill=\"none\" "
                    + "points=\"12.5 15.8 22 6.2 17.8 2 8.3 11.5 8 16 12.5 15.8\" stroke=\"#ddd6c5\" "
                    + "stroke-linecap=\"round\" stroke-linejoin=\"round\" stroke-width=\"2\"></polygon> "
                    + "</g> </g> </g> </g></svg></button>");
            out.println("</form>");
            out.println("<form id='deleteForm_" + c.getCouponID() + "' action='deleteC' method='post' style='display:inline;' onsubmit='return confirmDelete(this, \"" + c.getCouponID() + "\")'>");
            out.println("<input type='hidden' name='couponID' value='" + c.getCouponID() + "'>");
            out.println("<button type='submit' class='btn btn-link'><svg width=\"24\" height=\"24\" viewBox=\"0 0 24 24\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                    + "                                                                <g id=\"SVGRepo_bgCarrier\" stroke-width=\"0\"></g>\n"
                    + "                                                                <g id=\"SVGRepo_tracerCarrier\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></g>\n"
                    + "                                                                <g id=\"SVGRepo_iconCarrier\">\n"
                    + "                                                                <path d=\"M10 11V17\" stroke=\"#ddd6c5\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></path>\n"
                    + "                                                                <path d=\"M14 11V17\" stroke=\"#ddd6c5\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></path>\n"
                    + "                                                                <path d=\"M4 7H20\" stroke=\"#ddd6c5\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></path>\n"
                    + "                                                                <path d=\"M6 7H12H18V18C18 19.6569 16.6569 21 15 21H9C7.34315 21 6 19.6569 6 18V7Z\" stroke=\"#ddd6c5\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></path>\n"
                    + "                                                                <path d=\"M9 5C9 3.89543 9.89543 3 11 3H13C14.1046 3 15 3.89543 15 5V7H9V5Z\" stroke=\"#ddd6c5\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></path>\n"
                    + "                                                                </g>\n"
                    + "                                                                </svg></button>");
            out.println("</form>");
            out.println("</td>");
            out.println("</tr>");
        }
        if (list.isEmpty()) {
            searchErr = "Don't Found Counpon have Between Start Date and End Date: ";
            out.print("<h2><span style=\"color: red;\">" + searchErr+ search + "</span></h2>");
        }
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
        processRequest(request, response);
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
