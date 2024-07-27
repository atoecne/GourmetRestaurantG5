/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.DrinkDAO;
import DAO.FoodDAO;
import DAO.OrderDAO;
import DAO.ServeDAO;
import DAO.TableDAO;
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
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "ShowBillController", urlPatterns = {"/ShowBill"})
public class ShowBillController extends HttpServlet {

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
            out.println("<title>Servlet ShowBillController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ShowBillController at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        String tableID = (String) session.getAttribute("tableFB");
        if (tableID == null) {
            response.getWriter().write("<p>Please select a table first.</p>");
            return;
        }

        try {
            if (tableID != null) {
                OrderDAO orderDAO = new OrderDAO();
                String check = orderDAO.getOrderIDByTableID(tableID);
                System.out.println(check);
                if (check != null) {
                    int status = orderDAO.getOrderStatus(check);
                    System.out.println(status);
                    if (status == 0) {
                        OrderModel orderModel = new OrderModel();
                        List<OrderDetailModel> orderDetails = orderDAO.getOrderDetailsByOrderID(check);

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

                        TableDAO table = new TableDAO();
                        String tableName = table.getTableById(tableID).getTableNumber();

                        LocalDateTime time = orderDAO.getOrderById(check).getOrderDate();
                        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

                        String orderDateStr = time.format(dateFormatter);
                        String orderTimeStr = time.format(timeFormatter);


                        ServeDAO serve = new ServeDAO();
                        String serveName = serve.getserveByEmail(orderDAO.getOrderById(check).getEmailServe()).getFullName();

                        request.setAttribute("foods", foods);
                        request.setAttribute("drinks", drinks);
                        request.setAttribute("showOB", orderDetails);
                        String referer = request.getHeader("Referer");
                        request.setAttribute("tableID", tableID);
                        request.setAttribute("tableName", tableName);
                        request.setAttribute("serveName", serveName);
                        request.setAttribute("orderDate", orderDateStr);
                        request.setAttribute("orderTime", orderTimeStr);
                        request.getRequestDispatcher("Views/Cashier/Bill.jsp").forward(request, response);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("error vieworderbytable");
            request.getRequestDispatcher("Views/Cashier/ViewOrderByTable.jsp").forward(request, response);
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
