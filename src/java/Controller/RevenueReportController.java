/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.FoodDAO;
import DAO.OrderDAO;
import Model.FoodModel;
import Model.RevenueReportModel;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thanh Cong
 */
@WebServlet(name = "RevenueReportController", urlPatterns = {"/revenueReport"})
public class RevenueReportController extends HttpServlet {

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
            out.println("<title>Servlet RevenueReportController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RevenueReportController at " + request.getContextPath() + "</h1>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filter = request.getParameter("filter");
        String filterValue = request.getParameter("filterValue");

        LocalDate startDate;
        LocalDate endDate;

        if ("Month".equals(filter)) {
            int month = Integer.parseInt(filterValue);
            startDate = LocalDate.of(LocalDate.now().getYear(), month, 1);
            endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());
        } else if ("Quarter".equals(filter)) {
            int quarter = Integer.parseInt(filterValue);
            startDate = getStartDateOfQuarter(quarter);
            endDate = getEndDateOfQuarter(quarter);
        } else if ("Year".equals(filter)) {
            int year = Integer.parseInt(filterValue);
            startDate = LocalDate.of(year, 1, 1);
            endDate = LocalDate.of(year, 12, 31);
        } else {
            // Default to current month
            startDate = LocalDate.now().withDayOfMonth(1);
            endDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        }

        OrderDAO orD = new OrderDAO();
        List<RevenueReportModel> dailyReports = new ArrayList<>();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            Date sqlDate = Date.valueOf(date);
            int orderQuantity = orD.getQuatityOfOrder(sqlDate);
            BigDecimal totalPrice = orD.getTotalAmountOfOrder(sqlDate);
            if (totalPrice == null) {
                totalPrice = BigDecimal.ZERO;
            }
            dailyReports.add(new RevenueReportModel(sqlDate, orderQuantity, totalPrice));
        }

        LocalDate currentDate = orD.getMaxDate();
        Date dayR = Date.valueOf(currentDate);
        int orderQuantity = orD.getQuatityOfOrder(dayR);
        BigDecimal totalPrice = orD.getTotalAmountOfOrder(dayR);
        if (totalPrice == null) {
            totalPrice = BigDecimal.ZERO;
        }

        FoodDAO fD = new FoodDAO();
        List<String> listFoodID = orD.getTopFoodOrder();
        List<FoodModel> listFood = new ArrayList<>();

        for (int i = 0; i < listFoodID.size(); i++) {
            listFood.add(fD.getFoodById(listFoodID.get(i)));
        }

        if (filter == null || filterValue == null) {
            filter = "Month";
            filterValue = String.valueOf(LocalDate.now().getMonthValue());
        }

        request.setAttribute("dayR", dayR);
        request.setAttribute("orderQuantity", orderQuantity);
        request.setAttribute("dayRTo", totalPrice);
        request.setAttribute("dailyReports", dailyReports);
        request.setAttribute("filter", filter);
        request.setAttribute("filterValue", filterValue);
        request.setAttribute("startDate", Date.valueOf(startDate));
        request.setAttribute("endDate", Date.valueOf(endDate));
        request.setAttribute("topFood", listFood);

        List<Integer> years = orD.getYearsWithOrders();
        request.setAttribute("years", years);

        request.getRequestDispatcher("Views/RevenueReport/Report.jsp").forward(request, response);
    }

    private LocalDate getStartDateOfQuarter(int quarter) {
        switch (quarter) {
            case 1:
                return LocalDate.of(LocalDate.now().getYear(), 1, 1);
            case 2:
                return LocalDate.of(LocalDate.now().getYear(), 4, 1);
            case 3:
                return LocalDate.of(LocalDate.now().getYear(), 7, 1);
            case 4:
                return LocalDate.of(LocalDate.now().getYear(), 10, 1);
            default:
                return LocalDate.now().withDayOfMonth(1);
        }
    }

    private LocalDate getEndDateOfQuarter(int quarter) {
        switch (quarter) {
            case 1:
                return LocalDate.of(LocalDate.now().getYear(), 3, 31);
            case 2:
                return LocalDate.of(LocalDate.now().getYear(), 6, 30);
            case 3:
                return LocalDate.of(LocalDate.now().getYear(), 9, 30);
            case 4:
                return LocalDate.of(LocalDate.now().getYear(), 12, 31);
            default:
                return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
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
