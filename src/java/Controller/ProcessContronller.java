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
import java.util.List;
import java.util.Map;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "ProcessContronller", urlPatterns = {"/process"})
public class ProcessContronller extends HttpServlet {

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
            out.println("<title>Servlet BuyContronller</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BuyContronller at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession(true);
        TableModel tableModel = (TableModel) session.getAttribute("selectedTable");

        if (tableModel != null) {
            String tableID = tableModel.getTableID();
            Map<String, CartModel> tableCarts = (Map<String, CartModel>) session.getAttribute("tableCarts");
            if (tableCarts == null) {
                tableCarts = new HashMap<>();
                session.setAttribute("tableCarts", tableCarts);
            }

            CartModel cart = tableCarts.getOrDefault(tableID, new CartModel());
            String tnum = request.getParameter("num");
            String id = request.getParameter("id");
            int num;

            try {
                num = Integer.parseInt(tnum);
                if (id != null && !id.isEmpty()) {
                    if (cart == null) {
                        cart = new CartModel();
                        session.setAttribute("cart", cart);
                    }

                    if (num == -1) {
                        if (cart.getQuantityById(id) <= 1) {
                            cart.removeItem(id);
                        } else {
                            cart.updateItemQuantity(id, num);
                        }
                    } else if (num == 1) {
                        FoodDAO foodDAO = new FoodDAO();
                        FoodModel food = foodDAO.getFoodById(id);
                        if (food != null) {
                            ItemModel item = new ItemModel(food, null, num, food.getPrice());
                            cart.addItem(item);
                        } else {
                            DrinkDAO drinkDAO = new DrinkDAO();
                            DrinkModel drink = drinkDAO.getDrinkById(id);
                            if (drink != null) {
                                ItemModel item = new ItemModel(null, drink, num, drink.getPrice());
                                cart.addItem(item);
                            }
                        }
                    }
                }

                tableCarts.put(tableID, cart);
                session.setAttribute("cart" + tableID, cart);

                String referer = request.getHeader("Referer");
                if (referer != null && referer.contains("Views/Order/AddCart.jsp")) {
                    response.sendRedirect("Views/Order/AddCart.jsp");
                } else {
                    response.sendRedirect("Views/Order/ViewOrder?tableID=" + tableID);
                }

            } catch (NumberFormatException e) {
                e.printStackTrace(); // Log error for debugging
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "TableModel is missing from session.");
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
        HttpSession session = request.getSession(true);
        TableModel tableModel = (TableModel) session.getAttribute("selectedTable");

        if (tableModel != null) {
            String tableID = tableModel.getTableID();
            Map<String, CartModel> tableCarts = (Map<String, CartModel>) session.getAttribute("tableCarts");
            CartModel cart = tableCarts.getOrDefault(tableID, new CartModel());
            if (tableCarts == null) {
                tableCarts = new HashMap<>();
                session.setAttribute("tableCarts", tableCarts);
            }

            if (cart == null) {
                cart = new CartModel();
                session.setAttribute("cart", cart);
            }

            String foodID = request.getParameter("foodID");
            String drinkID = request.getParameter("drinkID");

            if (foodID != null && !foodID.isEmpty()) {
                cart.removeItem(foodID);
            }

            if (drinkID != null && !drinkID.isEmpty()) {
                cart.removeItem(drinkID);
            }

            // Cập nhật lại session với cartTemp mới
            session.setAttribute("cart", cart);

            String referer = request.getHeader("Referer");
            if (referer != null && referer.contains("Views/Order/AddCart.jsp")) {
                response.sendRedirect("Views/Order/AddCart.jsp");
            } else {
                response.sendRedirect("viewOrder?tableID=" + tableID);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Table not found.");
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



