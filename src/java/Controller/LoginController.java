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
import Model.MD5;
import Model.ServeModel;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Nguyen van canh
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

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
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("Views/Login/Login.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // Regex để kiểm tra định dạng email
    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "Views/Login/Login.jsp";
        String e = request.getParameter("email");
        String p = request.getParameter("pass");
        String r = request.getParameter("role");

        AccountDAO aD = new AccountDAO();
        MD5 m = new MD5();
        HttpSession session = request.getSession();
        AccountModel aM = aD.checkLogin(e, m.MD5Encryption(p));
        
        CashierDAO caD = new CashierDAO();
        ServeDAO seD = new ServeDAO();

        // Check if email exists
        if (!aD.checkEmail(e)) {
            request.setAttribute("errorEmail", "Email does not exist");
            request.getRequestDispatcher(url).forward(request, response);
        } else {
            if (aM == null) {
                request.setAttribute("Error", " Password invalid");
                request.getRequestDispatcher(url).forward(request, response);
            } else {
                String role = aM.getRole();
                if (role.equalsIgnoreCase(r)) {
                    switch (r) {
                        case "Serve":
                            ServeModel seM = seD.getserveByEmail(e);
                            session.setAttribute("dataserve", seM);
                            url = "Views/Serve/ViewServe.jsp";
                            break;
                        case "Cashier":
                            CashierModel caM = caD.getCashierByEmail(e);
                            session.setAttribute("datacashier", caM);
                            url = "ShowT";
                            break;
                        default:
                            url = "listAccount";
                    }
                } else {
                    request.setAttribute("Error", "Role invalid");
                    request.getRequestDispatcher(url).forward(request, response);
                }
                session.setAttribute("role", role);
                session.setAttribute("dataAcc", aM);
                
                response.sendRedirect(url);
            }
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
