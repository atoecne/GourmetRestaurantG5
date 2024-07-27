/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.OrderDAO;
import Model.AccountModel;
import Model.CartModel;
import Model.ItemModel;
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
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "ConfirmOrderController", urlPatterns = {"/confirmOrder"})
public class ConfirmOrderController extends HttpServlet {

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
        doPost(request, response);

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
        doPost(request, response);
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
        TableModel selectedTable = (TableModel) session.getAttribute("selectedTable");
        String tableID = (selectedTable != null) ? selectedTable.getTableID() : null;

        if (tableID == null) {
            response.sendRedirect("login");
            return;
        }
        Map<String, CartModel> tableCarts = (Map<String, CartModel>) session.getAttribute("tableCarts");
        if (tableCarts == null) {
            tableCarts = new HashMap<>();
            session.setAttribute("tableCarts", tableCarts);
        }

        CartModel cart = tableCarts.get(tableID);

        if (cart == null || cart.getItems().isEmpty()) {
            response.sendRedirect("Views/Order/AddCart.jsp");
            return;
        }
        OrderDAO orderDAO = new OrderDAO();
        String check = orderDAO.getOrderIDByTableID(tableID);
        if (check != null) {
            int status = orderDAO.getOrderStatus(check);
            if (status == 0) {
                OrderModel orderModel = new OrderModel();
                AccountModel account = (AccountModel) session.getAttribute("dataAcc");
                String emailServe = account != null ? account.getEmail() : null;
                orderModel.setEmailServe(emailServe);   
                System.out.println("");
                boolean updateOrder = orderDAO.updateOrder(cart, check, tableID);
                
                if (updateOrder == true) {
                    Map<String, String> tableOrders = (Map<String, String>) session.getAttribute("tableOrders");
                    if (tableOrders == null) {
                        tableOrders = new HashMap<>();
                    }
                    tableOrders.put(tableID, check);
                    session.setAttribute("tableOrders", tableOrders);

                    tableCarts.put(tableID, new CartModel());
                    
                    session.setAttribute("tableCarts", tableCarts);
                    response.sendRedirect("ShowOrder");
                } else {
                    response.sendRedirect("login");
                }
            } else if (status == 1) {
                OrderModel orderModel = new OrderModel();
                String newOrderID = orderDAO.getNextOrderID();
                orderModel.setOrderID(newOrderID);

                AccountModel account = (AccountModel) session.getAttribute("dataAcc");
                String emailServe = account != null ? account.getEmail() : null;
                orderModel.setEmailServe(emailServe);
                
                boolean isSuccess = orderDAO.addOrder(cart, orderModel, tableID);

                if (isSuccess) {
                    Map<String, String> tableOrders = (Map<String, String>) session.getAttribute("tableOrders");
                    if (tableOrders == null) {
                        tableOrders = new HashMap<>();
                    }
                    tableOrders.put(tableID, newOrderID);
                    session.setAttribute("tableOrders", tableOrders);
                    tableCarts.put(tableID, new CartModel());
                    session.setAttribute("tableCarts", tableCarts);

                    response.sendRedirect("ShowOrder");
                } else {
                    response.sendRedirect("login");
                }
            }
        } else {
            OrderModel orderModel = new OrderModel();
            String newOrderID = orderDAO.getNextOrderID();
            orderModel.setOrderID(newOrderID);

            AccountModel account = (AccountModel) session.getAttribute("dataAcc");
            String emailServe = account != null ? account.getEmail() : null;
            orderModel.setEmailServe(emailServe);
            
            boolean isSuccess = orderDAO.addOrder(cart, orderModel, tableID);
            if (isSuccess) {
                Map<String, String> tableOrders = (Map<String, String>) session.getAttribute("tableOrders");
                if (tableOrders == null) {
                    tableOrders = new HashMap<>();
                }
                tableOrders.put(tableID, newOrderID);
                session.setAttribute("tableOrders", tableOrders);

                tableCarts.put(tableID, new CartModel());
                session.setAttribute("tableCarts", tableCarts);

                response.sendRedirect("ShowOrder");
            } else {
                response.sendRedirect("login");
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



