/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AccountDAO;
import DAO.LogDAO;
import DAO.TableDAO;
import Model.AccountModel;
import Model.LogModel;
import Model.TableModel;
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
 * @author LENOVO
 */
@WebServlet(name = "SelectTableController", urlPatterns = {"/SelectTableController"})
public class SelectTableController extends HttpServlet {

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
            out.println("<title>Servlet SelectTableController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SelectTableController at " + request.getContextPath() + "</h1>");
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
        String tableID = request.getParameter("tableID");

        AccountModel checkLog = new AccountModel();
        AccountDAO aD = new AccountDAO();
        checkLog = (AccountModel) session.getAttribute("dataAcc");
        String email, username, action;
        
        if (checkLog != null) {
            if (tableID != null && !tableID.isEmpty()) {
                TableDAO tableDAO = new TableDAO();
                TableModel selectedTable = tableDAO.getTableById(tableID);
                session.setAttribute("selectedTable", selectedTable);
            }
            email = checkLog.getEmail();
            action = "Select table has ID: " + tableID;
            LogDAO logDAO = new LogDAO();
            LogModel log = new LogModel(0, email, action, new Timestamp(System.currentTimeMillis()));
            logDAO.createLog(log);
        } else {
            request.getRequestDispatcher("Views/Login/Login.jsp");
        }
        response.sendRedirect("showM");
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
