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
import java.sql.SQLException;
import java.util.regex.Pattern;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "CreateCustomerController", urlPatterns = {"/createCustomer"})
public class CreateCustomerController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String PHONE_REGEX = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);
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
        String action = request.getParameter("create");
        if ("create".equals(action)) {
            doPost(request, response);
        } else {
            response.sendRedirect("Views/Cashier/ManageCustomer.jsp?action=create");
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
        CustomerDAO cd = new CustomerDAO();
        int customerID = 0;
        String phoneNumber = request.getParameter("phoneNumber");
        String fullName = request.getParameter("fullName");
        int point = 0;
        if (!PHONE_PATTERN.matcher(phoneNumber).matches()) {
            request.setAttribute("errorPhone", "Invalid phone number. Please re-enter.");
            request.getRequestDispatcher("Views/Cashier/ManageCustomer.jsp?action=create").forward(request, response);
            return;
        }
        
        else if (cd.checkPhone(phoneNumber)) {
            request.setAttribute("message", "Phone number already exists.");
        } else {
            CustomerModel newCustomer = new CustomerModel(phoneNumber, fullName, point);
            cd.addCustomer(newCustomer);
            request.setAttribute("newCustomer", newCustomer);
            request.setAttribute("message", "Customer created successfully!"); // Chỉ đặt thông báo khi thành công
        }

        request.getRequestDispatcher("Views/Cashier/ManageCustomer.jsp?action=create").forward(request, response);  }

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
