package Controller;

import DAO.TableDAO;
import Model.TableViewModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Nguyen Thanh Huy - CE171915
 */
@WebServlet(name = "SearchTable", urlPatterns = {"/SearchTable"})
public class SearchTableController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SearchTable</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchTable at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String search = request.getParameter("txtSearch");
        String searchErr;
        TableDAO tbd = new TableDAO();
        List<TableViewModel> list = tbd.searchTableNumber(search);

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        for (TableViewModel t : list) {
            out.println("<tr id='infoF'>");
            out.println("<td><img src='image/" + t.getTableImg() + "' width='50'></td>");
            out.println("<td>" + t.getTableID() + "</td>");
            out.println("<td>" + t.getFloor() + "</td>");
            out.println("<td>" + t.getTableNumber() + "</td>");
            out.println("<td>" + t.getSeatingCapacity() + "</td>");
            out.println("<td>" + t.getStatus() + "</td>");
            out.println("<td id='buttonUD'>");
            // Include your HTML content
            out.println("<form action='UpdateTable.jsp' method='get' style='display:inline;'>");
            out.println("<input type='hidden' name='tableID' value='" + t.getTableID() + "'>");
            out.println("<button type='submit' class='btn btn-link'>"
                    + "<svg width=\"24px\" height=\"24px\" viewBox=\"0 0 24.00 24.00\" "
                    + "xmlns=\"http://www.w3.org/2000/svg\" fill=\"#ddd6c5\" stroke=\"#ddd6c5\" "
                    + "transform=\"rotate(0)\"><g id=\"SVGRepo_bgCarrier\" stroke-width=\"0\"></g>"
                    + "<g id=\"SVGRepo_tracerCarrier\" stroke-linecap=\"round\" stroke-linejoin=\"round\" "
                    + "stroke=\"#CCCCCC\" stroke-width=\"0.096\"></g><g id=\"SVGRepo_iconCarrier\">"
                    + " <title></title> <g id=\"Complete\"> <g id=\"edit\"> <g> "
                    + "<path d=\"M20,16v4a2,2,0,0,1-2,2H4a2,2,0,0,1-2-2V6A2,2,0,0,1,4,4H8\" "
                    + "fill=\"none\" stroke=\"#ddd6c5\" stroke-linecap=\"round\" stroke-linejoin=\"round\" "
                    + "stroke-width=\"2\"></path> <polygon fill=\"none\" "
                    + "points=\"12.5 15.8 22 6.2 17.8 2 8.3 11.5 8 16 12.5 15.8\" stroke=\"#ddd6c5\" "
                    + "stroke-linecap=\"round\" stroke-linejoin=\"round\" stroke-width=\"2\"></polygon> "
                    + "</g> </g> </g> </g></svg></button>");
            out.println("</form>");

            out.println("<form id='deleteTableForm' action='" + request.getContextPath() + "/deleteTable' method='post' onsubmit='return confirmDelete(this, \"" + t.getFloor() + "\", \"" + t.getTableNumber() + "\")'>");
            out.println("<input type='hidden' name='tableID' value='" + t.getTableID() + "'>");
            out.println("<button type='submit' class='btn btn-link'><svg width=\"24\" height=\"24\" viewBox=\"0 0 24 24\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                    + "                                                                <g id=\"SVGRepo_bgCarrier\" stroke-width=\"0\"></g>\n"
                    + "                                                                <g id=\"SVGRepo_tracerCarrier\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></g>\n"
                    + "                                                                <g id=\"SVGRepo_iconCarrier\">\n"
                    + "                                                                <path d=\"M10 11V17\" stroke=\"#ddd6c5\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></path>\n"
                    + "                                                                <path d=\"M14 11V17\" stroke=\"#ddd6c5\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></path>\n"
                    + "                                                                <path d=\"M4 7H20\" stroke=\"#ddd6c5\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></path>\n"
                    + "                                                                <path d=\"M6 7H12H18V18C18 19.6569 16.6569 21 15 21H9C7.34315 21 6 19.6569 6 18V7Z\" stroke=\"#ddd6c5\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></path>\n"
                    + "                                                                <path d=\"M9 5C9 3.89543 9.89543 3 11 3H13C14.1046 3 15 3.89543 15 5V7H9V5Z\" stroke=\"#ddd6c5\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></path>\n"
                    + "                                                                </g>\n"
                    + "                                                                </svg></button>");
            out.println("</form>");

            out.println("</td>");
            out.println("</tr>");
        }

        if (list.isEmpty()) {
            searchErr = "Don't Found Table have Table Number is: ";
            out.print("<h2><span style=\"color: red;\">" + searchErr +search+ "</span></h2>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}