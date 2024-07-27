/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.FoodModel;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class FoodDAO extends DBContext {

    public void addFood(FoodModel food) {
        try {
            // Chèn dữ liệu vào bảng Foods
            String query = "INSERT INTO Foods (FoodID, FoodImg, FoodName, CategoryID, Description, Price, Quantity, Unit) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, food.getFoodID());
            ps.setString(2, food.getFoodImg());
            ps.setString(3, food.getFoodName());
            ps.setString(4, food.getCategoryID());
            ps.setString(5, food.getDescription());
            ps.setBigDecimal(6, food.getPrice());
            ps.setInt(7, food.getQuantity());
            ps.setString(8, food.getUnit());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<FoodModel> getAllFoods() {
        List<FoodModel> list = new ArrayList<>();
        String query = "SELECT * FROM [dbo].[Foods] ORDER BY FoodID ASC";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FoodModel p = new FoodModel(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getBigDecimal(6), rs.getInt(7), rs.getString(8));
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("loi getAll");
        }
        return list;
    }

    public List<FoodModel> getFoodBC(String categoryName) {
        List<FoodModel> list = new ArrayList<>();
        String query = "SELECT f.FoodID, f.FoodImg, f.FoodName, f.CategoryID, f.Description, f.Price, f.Quantity, f.Unit, c.CategoryName\n"
                + "FROM Foods f\n"
                + "JOIN Categories c ON f.CategoryID = c.CategoryID\n"
                + "WHERE c.CategoryName = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, categoryName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FoodModel p = new FoodModel(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getBigDecimal(6), rs.getInt(7), rs.getString(8));
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("loi byID");
        }
        return list;
    }

    public void updateFood(FoodModel food) {
        try {
            String query = "UPDATE Foods SET FoodImg = ?, FoodName = ?, CategoryID = ?, Description = ?, Price = ?, Quantity = ?, Unit = ? WHERE FoodID = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, food.getFoodImg());
            ps.setString(2, food.getFoodName());
            ps.setString(3, food.getCategoryID());
            ps.setString(4, food.getDescription());
            ps.setBigDecimal(5, food.getPrice());
            ps.setInt(6, food.getQuantity());
            ps.setString(7, food.getUnit());
            ps.setString(8, food.getFoodID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public FoodModel getFoodById(String id) {
        String query = "select * from Foods where FoodID=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                FoodModel p = new FoodModel(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getBigDecimal(6), rs.getInt(7), rs.getString(8));
                return p;
            }
        } catch (SQLException e) {
            System.out.println("loi byID");
        }
        return null;
    }

    public void deleteFood(String foodID) {
        String query = "DELETE FROM [dbo].[Foods]\n"
                + "      WHERE FoodID=?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, foodID);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public List<FoodModel> searchFood(String name) {
        List<FoodModel> list = new ArrayList<>();
        String query = "select * from Foods where FoodName like ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FoodModel p = new FoodModel(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getBigDecimal(6), rs.getInt(7), rs.getString(8));
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("loi name");
        }
        return list;
    }

    public String getNextFoodID() {
        List<Integer> listID = new ArrayList<>();
        String query = "SELECT FoodID FROM Foods ORDER BY FoodID";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String foodID = rs.getString(1);
                int IDNumber = Integer.parseInt(foodID.substring(1));
                listID.add(IDNumber);
            }
        } catch (NumberFormatException | SQLException e) {
            System.out.println("Loi add ID");
        }
        int nextID = 1;
        while (listID.contains(nextID)) {
            nextID++;
        }

        return "F" + String.format("%04d", nextID);
    }

    public List<FoodModel> getFoodsForOrder(List<String> foodID) {
        List<FoodModel> foods = new ArrayList<>();

        if (foodID.isEmpty()) {
            return foods;
        }

        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM Foods WHERE FoodID IN (");
        for (int i = 0; i < foodID.size(); i++) {
            queryBuilder.append("?");
            if (i < foodID.size() - 1) {
                queryBuilder.append(", ");
            }
        }
        queryBuilder.append(")");

        String query = queryBuilder.toString();

        try ( PreparedStatement ps = connection.prepareStatement(query)) {
            // Thiết lập các tham số cho câu lệnh PreparedStatement
            for (int i = 0; i < foodID.size(); i++) {
                ps.setString(i + 1, foodID.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FoodModel p = new FoodModel(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getBigDecimal(6), rs.getInt(7), rs.getString(8));
                foods.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foods;
    }
}
