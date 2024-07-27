package Controller;

import DAO.AccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "CheckEmailServlet", urlPatterns = {"/checkEmail"})
public class CheckEmailServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");

        AccountDAO accountDAO = new AccountDAO();
        boolean isDuplicate = accountDAO.isEmailDuplicate(email);

        response.setContentType("text/plain");
        response.getWriter().write(isDuplicate ? "duplicate" : "available");
    }
}
