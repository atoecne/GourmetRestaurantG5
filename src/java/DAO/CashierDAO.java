/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.CashierModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nguyen Van Canh
 */
public class CashierDAO extends DBContext {

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

    public List<CashierModel> getAllCashier() {
        List<CashierModel> list = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[Cashiers]";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String email = rs.getString(1);
                String role = getRoleByEmail(email);
                CashierModel p = new CashierModel(rs.getString(1),  rs.getString(2),
                        rs.getDate(3), rs.getString(4), rs.getString(5), role);
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public CashierModel getCashierByEmail(String email) {
        String sql = "select * from Cashiers where email =?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String role = getRoleByEmail(email);
                CashierModel cM = new CashierModel(rs.getString("email"),
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

    public void insertCashier(CashierModel ca) {
        String sql = "INSERT INTO [dbo].[Cashiers]\n"
                + "           ([Email]\n"
                + "           ,[FullName]\n"
                + "           ,[Birthday]\n"
                + "           ,[PhoneNumber]\n"
                + "           ,[Address])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, ca.getEmail());
            st.setString(2, ca.getFullName());
            st.setDate(3, ca.getBirthday());
            st.setString(4, ca.getPhoneNumber());
            st.setString(5, ca.getAddress());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //delete
    public void deleteCashier(String emailCashier) {
        String query = "DELETE FROM [dbo].[Cashiers]\n"
                + "WHERE email=?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, emailCashier);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateCashier(CashierModel cashier) {
        String sql = "UPDATE [dbo].[Cashiers]\n"
                + "      SET [FullName] = ?\n"
                + "      ,[Birthday] = ?\n"
                + "      ,[PhoneNumber] = ?\n"
                + "      ,[Address] = ?\n"
                + " WHERE [Email] = ?";
        try {

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cashier.getFullName());
            ps.setDate(2, cashier.getBirthday());
            ps.setString(3, cashier.getPhoneNumber());
            ps.setString(4, cashier.getAddress());
            ps.setString(5, cashier.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
