/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.DrinkDAO;
import DAO.FoodDAO;
import Model.CartModel;
import Model.DrinkModel;
import Model.FoodModel;
import Model.ItemModel;
import Model.TableModel;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "CreateOrderController", urlPatterns = {"/CreateOrder"})
public class CreateOrderController extends HttpServlet {

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
            out.println("<title>Servlet CreateOrder</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateOrder at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession(true);
        TableModel selectedTable = (TableModel) session.getAttribute("selectedTable");
        String tableID = (selectedTable != null) ? selectedTable.getTableID() : null;

        String tnum = request.getParameter("num");
        String foodID = request.getParameter("foodID");
        String drinkID = request.getParameter("drinkID");

        Map<String, CartModel> tableCarts = (Map<String, CartModel>) session.getAttribute("tableCarts");
        if (tableCarts == null) {
            tableCarts = new HashMap<>();
            session.setAttribute("tableCarts", tableCarts);
        }

        CartModel cart = tableCarts.getOrDefault(tableID, new CartModel());

        int num = 1;
        try {
            if (tnum != null) {
                num = Integer.parseInt(tnum);
            }

            FoodDAO foodDAO = new FoodDAO();
            DrinkDAO drinkDAO = new DrinkDAO();
            ItemModel item = null;

            if (foodID != null && !foodID.isEmpty()) {
                FoodModel food = foodDAO.getFoodById(foodID);
                if (food != null) {
                    item = new ItemModel(food, null, num, food.getPrice());
                }
            } else if (drinkID != null && !drinkID.isEmpty()) {
                DrinkModel drink = drinkDAO.getDrinkById(drinkID);
                if (drink != null) {
                    item = new ItemModel(null, drink, num, drink.getPrice());
                }
            }
            if (item != null) {
                cart.addItem(item);
            }
            tableCarts.put(tableID, cart);
            session.setAttribute("tableCarts", tableCarts);
            session.setAttribute("size", cart.getItems().size());
        } catch (NumberFormatException e) {
            e.printStackTrace();  
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



