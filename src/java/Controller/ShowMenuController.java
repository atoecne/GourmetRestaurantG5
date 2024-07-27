/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.DrinkDAO;
import DAO.FoodDAO;
import DAO.OrderDAO;
import Model.AccountModel;
import Model.DrinkModel;
import Model.FoodModel;
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

/**
 *
 * @author Admin
 */
@WebServlet(name = "ShowMenuController", urlPatterns = {"/showM"})
public class ShowMenuController extends HttpServlet {

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
            out.println("<title>Servlet ShowMenuController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ShowMenuController at " + request.getContextPath() + "</h1>");
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
        TableModel tableID = (TableModel) session.getAttribute("selectedTable");
        request.setAttribute("selectedTable", tableID);
        DrinkDAO drinkDAO = new DrinkDAO();
        FoodDAO foodDAO = new FoodDAO();
        OrderDAO orD = new OrderDAO();
        List<String> listFoodID = orD.getTopFoodOrder();
        List<FoodModel> listFood = new ArrayList<>();

        for (int i = 0; i < listFoodID.size(); i++) {
            listFood.add(foodDAO.getFoodById(listFoodID.get(i)));
        }

        String bc = request.getParameter("bc");
        if (bc == null || bc.isEmpty()) {
            List<DrinkModel> drinkList = drinkDAO.getAllDrinks();
            request.setAttribute("drinkList", drinkList);
            List<FoodModel> foodList = foodDAO.getAllFoods();
            request.setAttribute("foodList", foodList);
        } else {
            List<DrinkModel> drinkList = drinkDAO.getDrinkBB(bc);
            request.setAttribute("drinkList", drinkList);
            List<FoodModel> foodList = foodDAO.getFoodBC(bc);
            request.setAttribute("foodList", foodList);
        }
        String id = request.getParameter("id");
        String type = request.getParameter("type");

        if (id != null && type.equals("food")) {
            FoodModel foodClick = foodDAO.getFoodById(id);
            request.setAttribute("foodDetails", foodClick);
        } else if (id != null && type.equals("drink")) {
            DrinkModel drinkClick = drinkDAO.getDrinkById(id);
            request.setAttribute("drinkDetails", drinkClick);
        }

        request.setAttribute("topFood", listFood);

        AccountModel getAcc = (AccountModel) session.getAttribute("dataAcc");
        System.out.println("dấ : " + getAcc.getRole());
        if (getAcc.getRole().equalsIgnoreCase("Cashier")) {
            request.getRequestDispatcher("Views/Menu/ViewMenuCas.jsp").forward(request, response);
        } else if (getAcc.getRole().equalsIgnoreCase("Serve")) {
            request.getRequestDispatcher("Views/Menu/ViewMenu.jsp").forward(request, response);
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
