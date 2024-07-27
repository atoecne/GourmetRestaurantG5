/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.DrinkDAO;
import DAO.FoodDAO;
import Model.DrinkModel;
import Model.FoodModel;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Admin
 */
@WebServlet(name = "SearchMenu", urlPatterns = {"/searchM"})
public class SearchMenu extends HttpServlet {

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
            out.println("<title>Servlet SearchMenu</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchMenu at " + request.getContextPath() + "</h1>");
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
        String searchQuery = request.getParameter("txtSearch");


        FoodDAO foodDAO = new FoodDAO();
        DrinkDAO drinkDAO = new DrinkDAO();
        List<FoodModel> foodList = foodDAO.searchFood(searchQuery);
        List<DrinkModel> drinkList = drinkDAO.searchDrink(searchQuery);
        int indexF = 0, indexD = 0;


        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        if (!foodList.isEmpty()) {
            out.println("<div id=\"allFoods\">");
            out.println("<h2>List Food</h2>");
            out.println("<div id=\"foodList\">");
            for (FoodModel food : foodList) {
                indexF++;
                if (indexF % 3 == 1) {
                    out.println("<div class=\"item-row\">");
                }
                out.println("<div class=\"product-item " + (food.getQuantity() == 0 ? "dimmed" : "") + "\" onclick=\"showMenuDetails('" + food.getFoodID() + "', 'food')\">");
                out.println("<img class=\"item-image\" src=\"image/" + food.getFoodImg() + "\" alt=\"" + food.getFoodName() + "\">");
                out.println("<div class=\"item-details\">");
                out.println("<div id=\"pname\">" + food.getFoodName() + "</div>");
                out.println("<div id=\"pprice\">Giá: " + food.getPrice() + "</div>");
                out.println("<div id=\"pdes\" class=\"hidden\">" + food.getDescription() + "</div>");
                out.println("</div></div>");
                if (indexF % 3 == 0 || indexF == foodList.size()) {
                    out.println("</div>");
                }
            }
            out.println("</div>");
            out.println("</div>");
        }

        if (!drinkList.isEmpty()) {
            out.println("<div id=\"allDrink\">");
            out.println("<h2>List Drink</h2>");
            out.println("<div id=\"drinkList\">");
            for (DrinkModel drink : drinkList) {
                indexD++;
                if (indexD % 3 == 1) {
                    out.println("<div class=\"item-row\">");
                }
                out.println("<div class=\"product-item " + (drink.getQuantity() == 0 ? "dimmed" : "") + "\" onclick=\"showMenuDetails('" + drink.getDrinkID() + "', 'drink')\">");
                out.println("<img class=\"item-image\" src=\"image/" + drink.getDrinkImg() + "\" alt=\"" + drink.getDrinkName() + "\">");
                out.println("<div class=\"item-details\">");
                out.println("<div id=\"pname\">" + drink.getDrinkName() + "</div>");
                out.println("<div id=\"pprice\">Price: " + drink.getPrice() + "</div>");
                out.println("</div></div>");
                if (indexD % 3 == 0 || indexD == drinkList.size()) {
                    out.println("</div>");
                }
            }
            out.println("</div>");
            out.println("</div>");
        }

        out.close();
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
