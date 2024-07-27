/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AccountDAO;
import DAO.CashierDAO;
import DAO.ServeDAO;
import Model.AccountModel;
import Model.CashierModel;
import Model.ServeModel;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author 
 */
@WebServlet(name = "UpdateRoleController", urlPatterns = {"/updateRole"})
public class UpdateRoleController extends HttpServlet {

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
            out.println("<title>Servlet UpdateChef</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateChef at " + request.getContextPath() + "</h1>");
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
        String e = request.getParameter("email");

        AccountDAO aD = new AccountDAO();
        AccountModel aM = aD.getAccByEmail(e);

        switch (aM.getRole()) {
            case "Serve":
                ServeDAO sD = new ServeDAO();
                ServeModel sM = sD.getserveByEmail(e);
                request.setAttribute("dataRole", sM);
                break;
            case "Cashier":
                CashierDAO caD = new CashierDAO();
                CashierModel caM = caD.getCashierByEmail(e);
                request.setAttribute("dataRole", caM);
                break;
        }

        request.setAttribute("role", aM.getRole());

        request.getRequestDispatcher("Views/Account/UpdateRole.jsp").forward(request, response);
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
        AccountDAO aD = new AccountDAO();
        ServeDAO sD = new ServeDAO();
        CashierDAO caD = new CashierDAO();

        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        String birthday = request.getParameter("birthday");
        java.sql.Date date = java.sql.Date.valueOf(birthday);
        String phonenumber = request.getParameter("phoneNumber");
        String address = request.getParameter("address");
        String role = request.getParameter("role");
        System.out.println(role);
        switch (role) { 
            case "Cashier":
                if (caD.getCashierByEmail(email) == null) {
                    aD.updateRole(email, role);
                    AccountModel newAcc = aD.getAccByEmail(email);
                    aD.deleteAccount(email);
                    aD.insertAccount(newAcc);
                    caD.insertCashier(new CashierModel(email, fullName, date, phonenumber, address, role));
                } else {
                    System.out.println("Error");
                    request.getRequestDispatcher("listAccount").forward(request, response);
                }
                break;
            case "Serve":
                if (sD.getserveByEmail(email) == null) {
                    aD.updateRole(email, role);
                    AccountModel newAcc = aD.getAccByEmail(email);
                    aD.deleteAccount(email);
                    aD.insertAccount(newAcc);
                    sD.insertServe(new ServeModel(email, fullName, date, phonenumber, address, role));
                } else {
                    System.out.println("Error");
                    request.getRequestDispatcher("listAccount").forward(request, response);
                }
                break;
        }

        response.sendRedirect("listAccount");

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
