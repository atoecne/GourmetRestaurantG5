/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AccountDAO;
import DAO.DrinkDAO;
import DAO.LogDAO;
import Model.AccountModel;
import Model.LogModel;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
@WebServlet(name = "DeleteDrinkController", urlPatterns = {"/deleteD"})
public class DeleteDrinkController extends HttpServlet {

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
            out.println("<title>Servlet DeleteDrinkController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeleteDrinkController at " + request.getContextPath() + "</h1>");
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

        String drinkID = request.getParameter("drinkID");
        DrinkDAO drinkDAO = new DrinkDAO();
        HttpSession session = request.getSession();
        AccountModel checkLog = new AccountModel();
        AccountDAO aD = new AccountDAO();
        checkLog = (AccountModel) session.getAttribute("dataAcc");
        String email, username, action;
        if (checkLog != null) {
            drinkDAO.deleteDrink(drinkID);
            email = checkLog.getEmail();
            action = "Deleted drink has ID: " + drinkID;
            LogDAO logDAO = new LogDAO();
            LogModel log = new LogModel(0, email, action, new Timestamp(System.currentTimeMillis()));
            logDAO.createLog(log);
        } else {
            request.getRequestDispatcher("Views/Login/Login.jsp");
        }
        // Chuyển hướng về trang listFoods.jsp
        response.sendRedirect("showD");
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
