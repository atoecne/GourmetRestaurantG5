/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AccountDAO;
import DAO.FoodDAO;
import DAO.LogDAO;
import Model.AccountModel;
import Model.FoodModel;
import Model.LogModel;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Admin
 */
@WebServlet(name = "UpdateFoodController", urlPatterns = {"/updateF"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class UpdateFoodController extends HttpServlet {

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
            out.println("<title>Servlet UpdateFoodController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateFoodController at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        FoodDAO foodDAO = new FoodDAO();
        String foodID = request.getParameter("foodID");
        String foodName = request.getParameter("foodName");
        String categoryID = request.getParameter("categoryID");
        String description = request.getParameter("description");
        String priceParam = request.getParameter("price");
        String unit = request.getParameter("unit");
        String quantityParam = request.getParameter("quantity");
        
        
        if (foodName.length() > 50) {
            request.setAttribute("update", "Food Name should not exceed 50 characters");
            request.getRequestDispatcher("Views/Food/UpdateFood.jsp").forward(request, response);
            return;
        }

        if (description.length() <= 20) {
            request.setAttribute("update", "Food Description must be at least 11 characters long");
            request.getRequestDispatcher("Views/Food/UpdateFood.jsp").forward(request, response);
            return;
        }

        if (unit.length() > 30) {
            request.setAttribute("update", "Unit should not exceed 30 characters");
            request.getRequestDispatcher("Views/Food/UpdateFood.jsp").forward(request, response);
            return;
        }
        

        
        
        Part file = request.getPart("foodImg");
        String imgFileName = file.getSubmittedFileName();

        String fileExtension = imgFileName.substring(imgFileName.lastIndexOf(".") + 1).toLowerCase();

        if (!fileExtension.equals("jpg") && !fileExtension.equals("jpeg") && !fileExtension.equals("png")) {
            request.setAttribute("update", "Only JPG and PNG images are allowed");
            request.getRequestDispatcher("Views/Food/UpdateFood.jsp").forward(request, response);
            return;
        }

        String uploadDirectory = getServletContext().getRealPath("/image/");
        String uploadPath = uploadDirectory + File.separator + imgFileName;

        try ( FileOutputStream fos = new FileOutputStream(uploadPath);  InputStream is = file.getInputStream()) {
            byte[] data = new byte[is.available()];
            is.read(data);
            fos.write(data);
        } catch (IOException e) {
            e.printStackTrace();
            request.setAttribute("update", "Failed to upload food image");
            request.getRequestDispatcher("Views/Food/UpdateFood.jsp").forward(request, response);
            return;
        }

        BigDecimal price = null;
        int quantity = 0;
        try {
            if (priceParam != null && !priceParam.isEmpty()) {
                price = new BigDecimal(priceParam);
                if (price.compareTo(BigDecimal.ONE) < 0) {
                    request.setAttribute("update", "Price must be at larger than 0");
                    request.getRequestDispatcher("Views/Food/UpdateFood.jsp").forward(request, response);
                    return;
                }
            } else {
                request.setAttribute("update", "Invalid Input: Price is required");
                request.getRequestDispatcher("Views/Food/UpdateFood.jsp").forward(request, response);
                return;
            }

            if (quantityParam != null && !quantityParam.isEmpty()) {
                quantity = Integer.parseInt(quantityParam);
            } else {
                request.setAttribute("update", "Check Quantity Format");
                request.getRequestDispatcher("Views/Food/UpdateFood.jsp").forward(request, response);
                return;
            }

            if (quantity <= 0) {
                throw new NumberFormatException();
            }
        

            FoodModel updatedFood = new FoodModel();
            updatedFood.setFoodID(foodID);
            updatedFood.setFoodImg(imgFileName);
            updatedFood.setFoodName(foodName);
            updatedFood.setCategoryID(categoryID);
            updatedFood.setDescription(description);
            updatedFood.setQuantity(quantity);
            updatedFood.setPrice(price);
            updatedFood.setUnit(unit);

            foodDAO.updateFood(updatedFood);

            AccountModel checkLog = new AccountModel();
            AccountDAO aD = new AccountDAO();
            checkLog = (AccountModel) session.getAttribute("dataAcc");
            String email, action;
            if (checkLog != null) {
                foodDAO.updateFood(updatedFood);
                email = checkLog.getEmail();
                action = "Updated food has name: " + foodName;
                LogDAO logDAO = new LogDAO();
                LogModel log = new LogModel(0, email, action, new Timestamp(System.currentTimeMillis()));
                logDAO.createLog(log);
            } else {
                request.getRequestDispatcher("Views/Login/Login.jsp");
            }

            request.setAttribute("update", "Updated food successfully");
            response.sendRedirect("showF");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("update", "Updated food failed");
            request.getRequestDispatcher("Views/Food/UpdateFood.jsp").forward(request, response);
            return;
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