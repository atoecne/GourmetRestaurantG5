/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.DrinkDAO;
import DAO.FoodDAO;
import DAO.OrderDAO;
import Model.DrinkModel;
import Model.FoodModel;
import Model.OrderDetailModel;
import Model.OrderModel;
import Model.TableModel;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "ShowOrderController", urlPatterns = {"/ShowOrder"})
public class ShowOrderController extends HttpServlet {

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
            out.println("<title>Servlet ShowOrderController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ShowOrderController at " + request.getContextPath() + "</h1>");
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
        try {
            HttpSession session = request.getSession();
            TableModel selectedTable = (TableModel) session.getAttribute("selectedTable");
            String tableID = selectedTable.getTableID();

            if (tableID != null) {
                OrderDAO orderDAO = new OrderDAO();
                String check = orderDAO.getOrderIDByTableID(tableID);
                if (check != null) {
                    int status = orderDAO.getOrderStatus(check);

                    if (status == 0) {
                        OrderModel orderModel = new OrderModel();

                        Map<String, String> tableOrders = (Map<String, String>) session.getAttribute("tableOrders");
                        String OrderID = tableOrders.get(tableID);

                        if (OrderID != null) {
                            List<OrderDetailModel> orderDetails = orderDAO.getOrderDetailsByOrderID(OrderID);

                            List<String> foodID = new ArrayList<>();
                            List<String> drinkID = new ArrayList<>();

                            for (OrderDetailModel detail : orderDetails) {
                                if (detail.getFoodID() != null) {
                                    foodID.add(detail.getFoodID());
                                }
                                if (detail.getDrinkID() != null) {
                                    drinkID.add(detail.getDrinkID());
                                }
                            }
                            FoodDAO foodDAO = new FoodDAO();
                            List<FoodModel> foods = foodDAO.getFoodsForOrder(foodID);

                            DrinkDAO drinkDAO = new DrinkDAO();
                            List<DrinkModel> drinks = drinkDAO.getDrinkForOrder(drinkID);

                            request.setAttribute("foods", foods);
                            request.setAttribute("drinks", drinks);
                            request.setAttribute("showO", orderDetails);
                            request.getRequestDispatcher("Views/Order/ViewOrder.jsp").forward(request, response);
                        } else {
                            request.getRequestDispatcher("Views/Order/ViewOrder.jsp").forward(request, response);
                        }
                    } else {
                        request.getRequestDispatcher("Views/Order/ViewOrder.jsp").forward(request, response);
                    }
                } else {
                    request.getRequestDispatcher("Views/Order/ViewOrder.jsp").forward(request, response);
                }
            }
        } catch (Exception e) {
            request.getRequestDispatcher("Views/Order/ViewOrder.jsp").forward(request, response);
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
        processRequest(request, response);
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
