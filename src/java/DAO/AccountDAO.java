
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.AccountModel;
import Model.StaffModel;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thanh Cong
 */
public class AccountDAO extends DBContext {

    public AccountModel checkLogin(String email, String password) {
        try {
            String sql = "select * from Accounts where Email=? and Password=?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return new AccountModel(rs.getString(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException e) {
            System.out.println("Error Getting Data!");
        }
        return null;
    }

    public boolean changePassword(String email, String password) {
        String sql = "UPDATE Accounts SET [Password] = ? WHERE Email = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, password);
            st.setString(2, email);

            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Change password fail");
        }
        return false;
    }

    public boolean checkEmail(String email) {
        try {
            String sql = "Select Email from Accounts where Email = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error");
        }
        return false;
    }

    public AccountModel getAccByEmail(String email) {
        String sql = "select * from Accounts Where Email = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                AccountModel aM = new AccountModel(rs.getString("email"), rs.getString("password"), rs.getString("role"));
                return aM;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void updateRole(String email, String role) {
        String sql = "UPDATE Accounts SET Role = ? WHERE Email = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, role);
            st.setString(2, email);

            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update role fail");
        }
    }

    //insert accounts
    public void insertAccount(AccountModel a) {
        String sql = "INSERT INTO [dbo].[Accounts]\n"
                + "           ([Email]\n"
                + "           ,[Password]\n"
                + "           ,[Role])\n"
                + "     VALUES\n"
                + "           (?,?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, a.getEmail());
            st.setString(2, a.getPassword());
            st.setString(3, a.getRole());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void deleteAccount(String email) {
        String query = "DELETE FROM [dbo].[Accounts]\n"
                + "WHERE email=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public List<StaffModel> searchStaff(String search) {
        List<StaffModel> list = new ArrayList<>();
        String query = 
                 "SELECT Email, 'Serve' AS Role, FullName AS CommonFullName, Birthday AS CommonBirthday, PhoneNumber AS CommonPhoneNumber, Address AS CommonAddress FROM Serves "
                + "WHERE FullName LIKE ?  "
                + "UNION ALL "
                + "SELECT Email, 'Cashier' AS Role, FullName AS CommonFullName, Birthday AS CommonBirthday, PhoneNumber AS CommonPhoneNumber, Address AS CommonAddress FROM Cashiers "
                + "WHERE FullName LIKE ? ";
        try {
            // Chuẩn bị truy vấn
            PreparedStatement ps = connection.prepareStatement(query);
            // Thiết lập các tham số cho truy vấn
            ps.setString(1, "%" + search + "%");
            ps.setString(2, "%" + search + "%");
            // Thực thi truy vấn và nhận kết quả
            ResultSet rs = ps.executeQuery();

            // Xử lý từng dòng trong kết quả
            while (rs.next()) {
                // Đọc dữ liệu từ các cột của dòng hiện tại
                String email = rs.getString("Email");
                String role = rs.getString("Role");
                String fullName = rs.getString("CommonFullName");
                Date birthday = rs.getDate("CommonBirthday");
                String phoneNumber = rs.getString("CommonPhoneNumber");
                String address = rs.getString("CommonAddress");

                // Tạo đối tượng StaffModel và thêm vào danh sách
                StaffModel nS = new StaffModel(email, fullName, birthday, phoneNumber, address, role);
                list.add(nS);
            }
        } catch (SQLException e) {
            // In thông báo lỗi nếu có vấn đề xảy ra
            System.out.println("Lỗi tìm kiếm: " + e.getMessage());
        }
        return list;
    }
    
    
     // Method to check if an email is duplicate
    public boolean isEmailDuplicate(String email) {
        String query = "SELECT COUNT(*) FROM Accounts WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
