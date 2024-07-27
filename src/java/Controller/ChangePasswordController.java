/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AccountDAO;
import Model.AccountModel;
import Model.MD5;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Nguyen Van canh
 */
@WebServlet(name = "ChangePassword", urlPatterns = {"/cPassword"})
public class ChangePasswordController extends HttpServlet {

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
            out.println("<title>Servlet ChangePassword</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangePassword at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        String o_password = request.getParameter("oPassword");
        String n_password = request.getParameter("nPassword");
        String re_password = request.getParameter("rePassword");

        MD5 md5 = new MD5();
        AccountDAO aD = new AccountDAO();
        AccountModel aM = (AccountModel) session.getAttribute("dataAcc");

        if (aM != null) {
            if (md5.MD5Encryption(o_password).equals(aM.getPassword())) {
            } else {
                request.setAttribute("errorOldP", "The old password is incorrect");
                request.getRequestDispatcher("Views/Password/ChangePassword.jsp").forward(request, response);
                return;
            }
        }

        if (n_password.length() < 6) {
            request.setAttribute("errorPassword", "Password must have at least 6 characters");
            request.getRequestDispatcher("Views/Password/ChangePasswordF.jsp").forward(request, response);
            return;
        }
        
        if (n_password.equals(re_password)) {
            if (aM != null) {
                String email = aM.getEmail();
                if (aD.changePassword(email, md5.MD5Encryption(n_password))) {
                    request.setAttribute("changeSuccess", "Password changed successfully!");
                }
            }
        } else {
            request.setAttribute("changeError", "Passwords do not match!");
        }
        

        request.getRequestDispatcher("Views/Password/ChangePassword.jsp").forward(request, response);
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
