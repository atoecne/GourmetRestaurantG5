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
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "OrderDetailController", urlPatterns = {"/orderDetail"})
public class OrderDetailController extends HttpServlet {

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
            out.println("<title>Servlet OrderDetailController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderDetailController at " + request.getContextPath() + "</h1>");
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
       String orderID = request.getParameter("orderID");
        OrderDAO orderDAO = new OrderDAO();
        List<OrderDetailModel> orderDetails = orderDAO.getOrderDetailsByOrderID(orderID);  

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

    // Lấy thông tin giá từ Order
    OrderModel orderModel = orderDAO.getOrderById(orderID); 
    request.setAttribute("orderDetails", orderDetails);
    request.setAttribute("foods", foods);
    request.setAttribute("drinks", drinks);
    request.setAttribute("orderModel", orderModel); 

    request.getRequestDispatcher("Views/Order/OrderDetail.jsp").include(request, response);
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
