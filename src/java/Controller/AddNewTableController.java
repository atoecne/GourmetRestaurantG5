/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.TableDAO;
import Model.TableModel;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

/**
 *
 * @author Nguyen Thanh Huy - CE171915
 */
@WebServlet(name = "AddNewTable", urlPatterns = {"/AddT"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class AddNewTableController extends HttpServlet {

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
            out.println("<title>Servlet CreateNewTable</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateNewTable at " + request.getContextPath() + "</h1>");
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
        TableDAO tbd = new TableDAO();
        String newID = tbd.createTableIDAuto();

        request.setAttribute("newTableID", newID);

        String message = (String) session.getAttribute("Message");
        if (message != null) {
            request.setAttribute("Message", message);
            session.removeAttribute("Message");
        }

        request.getRequestDispatcher("Views/Table/AddTable.jsp").forward(request, response);
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
        TableDAO tbd = new TableDAO();
        try {
            String TableID = request.getParameter("tableID");
            String Floor = request.getParameter("floorid");
            String TableNumber_raw = request.getParameter("tablenumber");
            String Seating_raw = request.getParameter("seatingnumber");
            String Status_raw = request.getParameter("Status");

            int seatingnumber, tableNumber, status;
            seatingnumber = Integer.parseInt(Seating_raw);
            tableNumber = Integer.parseInt(TableNumber_raw);
            status = Integer.parseInt(Status_raw);

            Part file = request.getPart("img");
            String imgFileName = file.getSubmittedFileName();
            System.out.println("Name img: " + imgFileName);

            String upload = getServletContext().getRealPath("/image/");
            String uploadPath = upload + File.separator + imgFileName;

            try {
                FileOutputStream fos = new FileOutputStream(uploadPath);
                InputStream is = file.getInputStream();

                byte[] data = new byte[is.available()];
                is.read(data);
                fos.write(data);
                fos.close();
            } catch (IOException e) {
                System.out.println(e);
                System.out.println("IMG Err");
            }

            if (tableNumber <= 0) {
                String newID = tbd.createTableIDAuto();
                request.setAttribute("newTableID", newID);
                request.setAttribute("Message", "Table Number must be greater than 0!");
                request.getRequestDispatcher("Views/AddTable.jsp").forward(request, response);
            } else if (seatingnumber <= 0) {
                String newID = tbd.createTableIDAuto();
                request.setAttribute("newTableID", newID);
                request.setAttribute("Message", "Seating Capacity must be greater than 0!");
                request.getRequestDispatcher("Views/Table/AddTable.jsp").forward(request, response);
            } else {
                TableNumber_raw = "Table " + tableNumber;
                TableModel tNew = tbd.checkTableNumber(Floor, TableNumber_raw);
                if (tNew == null) {
                    tNew = new TableModel(TableID, Floor, imgFileName, TableNumber_raw, seatingnumber, status);
                    tbd.addNewTable(tNew);
                    request.setAttribute("Message", "Add New Table Successful");
                    response.sendRedirect("ShowT");
                } else {
                    request.setAttribute("Message", "This table already exists on " + Floor);
                    String newID = tbd.createTableIDAuto(); // Tạo lại ID mới
                    request.setAttribute("newTableID", newID);
                    request.getRequestDispatcher("Views/Table/AddTable.jsp").forward(request, response);
                }

            }
        } catch (NumberFormatException e) {
            System.out.println(e);
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
