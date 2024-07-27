/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.TableDAO;
import Model.TableModel;
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
import jakarta.servlet.http.Part;

/**
 *
 * @author Nguyen Thanh Huy - CE171915
 */
@WebServlet(name = "updateTable", urlPatterns = {"/updateTable"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class UpdateTableController extends HttpServlet {

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
            out.println("<title>Servlet updateTable</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updateTable at " + request.getContextPath() + "</h1>");
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
        TableDAO tbd = new TableDAO();
        try {
            String TableID = request.getParameter("tableID");
            String Floor = request.getParameter("floorid");
            String TableNumber_raw = request.getParameter("tableNumber");
            System.out.println("T "+TableNumber_raw);
            String Seating_raw = request.getParameter("seatingCapacity");
            String Status_raw = request.getParameter("Status");
            int seatingnumber = 0, tableNumber = 0, status = 0;
            String tableNumber_real = "";
            boolean validInput = true;

            TableModel currentTable = tbd.getTableById(TableID);

            if (TableNumber_raw == null || TableNumber_raw.isEmpty()) {
                tableNumber_real = currentTable.getTableNumber();
                System.out.println("T1 " +tableNumber_real);
            } else {
                try {
                    tableNumber = Integer.parseInt(TableNumber_raw);
                    if (tableNumber <= 0) {
                        validInput = false;
                        request.setAttribute("Message", "Table Number must be greater than 0!");
                    }
                    else{
                        tableNumber_real = "Table " + tableNumber;
                        System.out.println("T2 " +tableNumber_real);
                    }
                } catch (NumberFormatException e) {
                    validInput = false;
                    request.setAttribute("Message", "Invalid Table Number!");
                }
            }

            if (Seating_raw == null || Seating_raw.isEmpty()) {
                seatingnumber = currentTable.getSeatingCapacity();
            } else {
                try {
                    seatingnumber = Integer.parseInt(Seating_raw);
                    if (seatingnumber <= 0) {
                        validInput = false;
                        request.setAttribute("Message", "Seating Capacity must be greater than 0!");
                    }
                } catch (NumberFormatException e) {
                    validInput = false;
                    request.setAttribute("Message", "Invalid Seating Capacity!");
                }
            }

            if (!validInput) {
                request.setAttribute("tableNumber", TableNumber_raw);
                request.setAttribute("seatingCapacity", Seating_raw);
                request.getRequestDispatcher("Views/Table/UpdateTable.jsp").forward(request, response);
                return;
            }

            if (Status_raw == null || Status_raw.isEmpty()) {
                status = currentTable.getStatus();
            } else {
                status = Integer.parseInt(Status_raw);
            }

            String errolImg = null;
            Part file = request.getPart("tableImg");
            String imgFileName = file.getSubmittedFileName();

            if (!imgFileName.isEmpty()) {
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
                    errolImg = "IMG Err";
                }
            } else {
                imgFileName = currentTable.getTableImg();
            }

            if (errolImg != null) {
                request.setAttribute("Message", "File Image Error");
                request.getRequestDispatcher("Views/Table/UpdateTable.jsp").forward(request, response);
            } else {
                TableModel tNew = tbd.checkTableNumber(Floor, tableNumber_real);
                if (tNew == null || tNew.getTableID().equals(TableID)) {
                    TableModel updateT = new TableModel();
                    updateT.setTableID(TableID);
                    updateT.setFloorID(Floor);
                    updateT.setTableImg(imgFileName);
                    updateT.setTableNumber(tableNumber_real);
                    updateT.setSeatingCapacity(seatingnumber);
                    updateT.setStatus(status);
                    System.out.println("T"+ updateT.getTableNumber());

                    boolean success = tbd.updateTable(updateT);
                    if (success) {
                        response.sendRedirect("ShowT");
                    } else {
                        request.setAttribute("Message", "Update table không thành công");
                        request.getRequestDispatcher("Views/Table/UpdateTable.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("Message", "This table number already exists on the " + Floor);
                    request.getRequestDispatcher("Views/Table/UpdateTable.jsp").forward(request, response);
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Errol Servlet: " + e);
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
