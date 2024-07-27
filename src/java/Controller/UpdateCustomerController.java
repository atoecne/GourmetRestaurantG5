
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.CustomerDAO;
import Model.CustomerModel;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author LENOVO
 */
@WebServlet(name="UpdateCustomerController", urlPatterns={"/updateCustomer"})
public class UpdateCustomerController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");
        if("update".equals(action) ){
            doPost(request,response);
        }else{
            response.sendRedirect("manageCustomer.jsp?action=update");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String phoneNumber = request.getParameter("phoneNumber");
        String fullName = request.getParameter("fullName");

        CustomerModel updatedCustomer = new CustomerModel();
        updatedCustomer.setPhoneNumber(phoneNumber);
        updatedCustomer.setFullName(fullName);

        CustomerDAO customerDAO = new CustomerDAO();
        boolean updated = customerDAO.updateCustomerFullName(updatedCustomer);

        if (updated) {
            request.setAttribute("message", "Updated customer full name successfully");
        } else {
            request.setAttribute("message", "Update failed");
        }
        
        // Lấy thông tin khách hàng mới cập nhật và chuyển tiếp tới trang updateCustomer.jsp
        request.setAttribute("customer", updatedCustomer);
        request.getRequestDispatcher("listCustomer?action=list").forward(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
