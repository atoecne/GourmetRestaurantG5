/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AccountDAO;
import DAO.CashierDAO;
import Model.AccountModel;
import Model.CashierModel;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

/**
 *
 * @author Nguyen Van Canh
 */
@WebServlet(name = "UpdateCashierController", urlPatterns = {"/updateCashierC"})
public class UpdateCashierController extends HttpServlet {

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
            out.println("<title>Servlet UpdateCashierController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateCashierController at " + request.getContextPath() + "</h1>");
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
    private static final long serialVersionUID = 1L;

    // Regex để kiểm tra số điện thoại
    private static final String PHONE_REGEX = "^\\d{10,12}$"; // Số điện thoại có từ 10 đến 12 chữ số
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("Views/Cashier/UpdateCashier.jsp").forward(request, response);
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
        String email = request.getParameter("email");
        String userName = request.getParameter("userName");
        String fullName = request.getParameter("fullName");
        String birthday = request.getParameter("birthday");
        java.sql.Date date = java.sql.Date.valueOf(birthday);
        String phonenumber = request.getParameter("phoneNumber");
        String address = request.getParameter("address");

        if (!PHONE_PATTERN.matcher(phonenumber).matches()) {
            // Đặt thông báo lỗi vào request và chuyển hướng lại form.jsp
            request.setAttribute("errorMessage", "Invalid phone number. Please re-enter.");
            request.getRequestDispatcher("Views/Cashier/UpdateCashier.jsp").forward(request, response);
            return;
        }
        
        AccountDAO aD = new AccountDAO();
        AccountModel aM = aD.getAccByEmail(email);
        CashierDAO caD = new CashierDAO();
        CashierModel caM = new CashierModel(email, fullName, date, phonenumber, address, aM.getRole());
        caD.updateCashier(caM);
        request.getRequestDispatcher("Views/Login/Login.jsp").forward(request, response);
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
