/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.CustomerModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class CustomerDAO extends DBContext {

    public boolean checkPhone(String phoneNumber) {
        try {
            String query = "SELECT COUNT(*) AS count FROM Customers WHERE PhoneNumber = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, phoneNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Trả về false nếu có lỗi xảy ra hoặc không tìm thấy số điện thoại
    }

    public void addCustomer(CustomerModel customer) {  
        try {

            String query = "INSERT INTO Customers VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, customer.getPhoneNumber());
            ps.setString(2, customer.getFullName());
            ps.setInt(3, customer.getPoint());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<CustomerModel> getAllCustomer() {
        List<CustomerModel> listCustomer = new ArrayList<>();
        String query = "select * from Customers";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CustomerModel c = new CustomerModel(
                        rs.getString(1), 
                        rs.getString(2), 
                        rs.getInt(3));
                listCustomer.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listCustomer;
    }

    public boolean updateCustomerFullName(CustomerModel customer) {
        try {
            String query = "UPDATE Customers SET FullName = ? WHERE PhoneNumber = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, customer.getFullName());
            ps.setString(2, customer.getPhoneNumber());

            int rowsAffected = ps.executeUpdate();

            // Kiểm tra xem có bản ghi nào được cập nhật không
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deleteCustomer(String phoneNumber) {
        System.out.println("ss");
        String query = "DELETE FROM Customers WHERE PhoneNumber=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, phoneNumber);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public CustomerModel getCustomerByPhone(String phoneNumber) {
String query = "SELECT * FROM Customers WHERE PhoneNumber = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, phoneNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                CustomerModel customer = new CustomerModel();
                customer.setPhoneNumber(rs.getString("PhoneNumber"));
                customer.setFullName(rs.getString("FullName"));
                customer.setPoint(rs.getInt("Point"));               
                return customer;
            }
        } catch (SQLException e) {
            System.out.println("Error in getCustomerByPhone: " + e.getMessage());
        }
        return null; // Trả về null nếu không tìm thấy khách hàng
    }

    public List<CustomerModel> findCustomerByPhoneNumber(String phoneNumber) {
        List<CustomerModel> listCustomer = new ArrayList<>();
        String query = "SELECT * FROM Customers WHERE PhoneNumber LIKE ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, "%" + phoneNumber + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CustomerModel c = new CustomerModel(                      
                        rs.getString("PhoneNumber"),
                        rs.getString("FullName"),
                        rs.getInt("Point")                       
                );
                System.out.println("Number of customers found: " + listCustomer.size());
                listCustomer.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listCustomer;
    }
}
