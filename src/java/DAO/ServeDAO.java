/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.ServeModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nguyen Van Canh
 */
public class ServeDAO extends DBContext {

    public String getRoleByEmail(String email) {
        String role = "";
        String sql = "SELECT Role FROM Accounts WHERE Email = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            
            if (rs.next()) {
                role = rs.getString("Role");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return role;
    }

    public List<ServeModel> getAllServe() {
        List<ServeModel> list = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[Serves]";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String email = rs.getString(1);
                String role = getRoleByEmail(email);
                ServeModel p = new ServeModel(rs.getString(1), rs.getString(2),
                        rs.getDate(3), rs.getString(4), rs.getString(5), role);
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public ServeModel getserveByEmail(String email) {
        String sql = "select * from Serves where Email =?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String role = getRoleByEmail(email);
                ServeModel cM = new ServeModel(rs.getString("email"),
                        rs.getString("fullName"),
                        rs.getDate("birthday"),
                        rs.getString("phoneNumber"),
                        rs.getString("address"),
                        role);
                return cM;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return null;
    }

    public void insertServe(ServeModel s) {
        String sql = "INSERT INTO [dbo].[Serves]\n"
                + "           ([Email]\n"
                + "           ,[FullName]\n"
                + "           ,[Birthday]\n"
                + "           ,[PhoneNumber]\n"
                + "           ,[Address])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, s.getEmail());
            st.setString(2, s.getFullName());
            st.setDate(3, s.getBirthday());
            st.setString(4, s.getPhoneNumber());
            st.setString(5, s.getAddress());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //delete
    public void deleteServe(String emailServe) {
        String query = "DELETE FROM [dbo].[Serves]\n"
                + "      WHERE email=?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, emailServe);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateServe(ServeModel serve) {
        String sql = "UPDATE [dbo].[Serves]\n"
                + "      SET [FullName] = ?\n"
                + "      ,[Birthday] = ?\n"
                + "      ,[PhoneNumber] = ?\n"
                + "      ,[Address] = ?\n"
                + " WHERE [Email] = ?";
        try {

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, serve.getFullName());
            ps.setDate(2, serve.getBirthday());
            ps.setString(3, serve.getPhoneNumber());
            ps.setString(4, serve.getAddress());
            ps.setString(5, serve.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            //System.err.println("SQL Error: " + e.getMessage());
        }
    }

}
