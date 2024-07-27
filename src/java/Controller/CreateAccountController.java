package Controller;

import DAO.CashierDAO;
import DAO.AccountDAO;
import DAO.ServeDAO;
import Model.AccountModel;
import Model.CashierModel;
import Model.MD5;
import Model.ServeModel;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@WebServlet(name = "CreateAccountController", urlPatterns = {"/createAcc"})
public class CreateAccountController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String PHONE_REGEX = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);
    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CreateAccountController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateAccountController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("Views/Account/CreateAccount.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        String fullName = request.getParameter("name");
        String birthDate = request.getParameter("birthday");
        Date date = null;
        if (birthDate != null && !birthDate.isEmpty()) {
            try {
                date = Date.valueOf(birthDate);
            } catch (IllegalArgumentException e) {
                request.setAttribute("errCreate", "Invalid date format. Please use yyyy-MM-dd.");
                request.getRequestDispatcher("Views/Account/CreateAccount.jsp").forward(request, response);
                return;
            }
        } else {
            request.setAttribute("errCreate", "Birthday cannot be null or empty.");
            request.getRequestDispatcher("Views/Account/CreateAccount.jsp").forward(request, response);
            return;
        }
        String phoneNumber = request.getParameter("phone");
        String address = request.getParameter("address");
        String type = request.getParameter("type");

        String password = randomPassword();
        boolean check = true;

        AccountDAO accountDAO = new AccountDAO();

        // Kiểm tra email trùng lặp
        if (accountDAO.isEmailDuplicate(email)) {
            check = false;
            request.setAttribute("errCreate", "Email already exists. Please use another email.");
            request.getRequestDispatcher("Views/Account/CreateAccount.jsp").forward(request, response);
            return;
        }

        // Kiểm tra định dạng email và phoneNumber
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            check = false;
            request.setAttribute("errCreate", "Invalid email. Please re-enter.");
            request.getRequestDispatcher("Views/Account/CreateAccount.jsp").forward(request, response);
            return;
        }
        if (!PHONE_PATTERN.matcher(phoneNumber).matches()) {
            check = false;
            request.setAttribute("errCreate", "Invalid phone number. Please re-enter.");
            request.getRequestDispatcher("Views/Account/CreateAccount.jsp").forward(request, response);
            return;
        }

        MD5 md5 = new MD5();
        ServeDAO serveDAO = new ServeDAO();
        CashierDAO cashierDAO = new CashierDAO();

        AccountModel accountModel;
        ServeModel serveModel;
        CashierModel cashierModel;

        switch (type) {
            case "Serve":
                accountModel = new AccountModel(email, md5.MD5Encryption(password), type);
                accountDAO.insertAccount(accountModel);
                serveModel = new ServeModel(email, fullName, date, phoneNumber, address, type);
                serveDAO.insertServe(serveModel);
                break;
            case "Cashier":
                accountModel = new AccountModel(email, md5.MD5Encryption(password), type);
                accountDAO.insertAccount(accountModel);
                cashierModel = new CashierModel(email, fullName, date, phoneNumber, address, type);
                cashierDAO.insertCashier(cashierModel);
                break;
        }

        if (check) {
            sendMail(email, password, fullName, date, phoneNumber, address, type);
        }

        response.sendRedirect("listAccount");
    }

    public String randomPassword() {
        int length = 6;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rd = new Random();
        StringBuilder otp = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            otp.append(characters.charAt(rd.nextInt(characters.length())));
        }

        return otp.toString();
    }

    public void sendMail(String toEmail, String pass, String fullname, Date date, String phoneNumber, String address, String type) throws UnsupportedEncodingException {
        final String from = "congnttce171869@fpt.edu.vn";
        final String password = "ksfjgerggkgznfxa";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //smtp host
        props.put("mail.smtp.port", "587"); // TLS 587 SSL 465
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            }
        };

        Session session = Session.getDefaultInstance(props, auth);

        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from, "Gourmet Restaurant"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("YOUR INFORMATION");

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = dateFormat.format(date);

            message.setText("Full name: " + fullname
                    + "\nPassword : " + pass
                    + "\nBirthday: " + formattedDate
                    + "\nPhone number: " + phoneNumber
                    + "\nAddress: " + address
                    + "\nDepartment: " + type);

            Transport.send(message);
        } catch (MessagingException e) {
            System.out.println("Error");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
