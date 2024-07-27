/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.DrinkModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class DrinkDAO extends DBContext {

    public void addDrink(DrinkModel drink) {
        try {
            // Chèn dữ liệu vào bảng Foods
            String query = "INSERT INTO Drinks (DrinkID, DrinkImg, DrinkName, BeverageID, Description, Price, Quantity ,Unit) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, drink.getDrinkID());
            ps.setString(2, drink.getDrinkImg());
            ps.setString(3, drink.getDrinkName());
            ps.setString(4, drink.getBeverageID());
            ps.setString(5, drink.getDescription());
            ps.setBigDecimal(6, drink.getPrice());
            ps.setInt(7, drink.getQuantity());
            ps.setString(8, drink.getUnit());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DrinkModel> getAllDrinks() {
        List<DrinkModel> list = new ArrayList<>();
        String query = "SELECT * FROM [dbo].[Drinks] ORDER BY DrinkID ASC";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DrinkModel p = new DrinkModel(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getBigDecimal(6),rs.getInt(7),rs.getString(8));
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("loi getAll");
        }
        return list;
    }

        public List<DrinkModel> getDrinkBB(String beverageName) {
        List<DrinkModel> list = new ArrayList<>();
        String query = "SELECT d.DrinkID, d.DrinkImg, d.DrinkName, d.BeverageID, d.Description, d.Price, d.Quantity, d.Unit, b.BeverageName\n"
                + "FROM Drinks d\n"
                + "JOIN Beverages b ON d.BeverageID = b.BeverageID\n"
                + "WHERE b.BeverageName = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, beverageName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DrinkModel p = new DrinkModel(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getBigDecimal(6), rs.getInt(7), rs.getString(8));
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("loi byID");
        }
        return list;
    }
    
    public void updateDrink(DrinkModel drink) {
        String query = "UPDATE Drinks SET DrinkImg = ?, DrinkName = ?, BeverageID = ?, Description = ?, Price = ?, Quantity = ?, Unit = ? WHERE DrinkID = ?";
        try ( PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, drink.getDrinkImg());
            ps.setString(2, drink.getDrinkName());
            ps.setString(3, drink.getBeverageID());
            ps.setString(4, drink.getDescription());
            ps.setBigDecimal(5, drink.getPrice());
            ps.setInt(6, drink.getQuantity());
            ps.setString(7, drink.getUnit());
            ps.setString(8, drink.getDrinkID()); 

            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public DrinkModel getDrinkById(String id) {
        String query = "select * from Drinks where DrinkID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                DrinkModel p = new DrinkModel(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getBigDecimal(6),rs.getInt(7),rs.getString(8));
                return p;
            }
        } catch (SQLException e) {
            System.out.println("loi byID");
        }
        return null;
    }

    public void deleteDrink(String drinkID) {
        String query = "DELETE FROM [dbo].[Drinks]\n"
                + "      WHERE DrinkID=?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, drinkID);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public List<DrinkModel> searchDrink(String name) {
        List<DrinkModel> list = new ArrayList<>();
        String query = "select * from Drinks where DrinkName like ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DrinkModel p = new DrinkModel(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getBigDecimal(6),rs.getInt(7),rs.getString(8));
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("loi name");
        }
        return list;
    }
    
    public String getNextDrinkID() {
        List<Integer> listID = new ArrayList<>();
        String query = "SELECT DrinkID FROM Drinks ORDER BY DrinkID";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String drinkID = rs.getString(1);
                int IDNumber = Integer.parseInt(drinkID.substring(1));
                listID.add(IDNumber);
            }
        } catch (NumberFormatException | SQLException e) {
            System.out.println("Loi add ID");
        }
        int nextID = 1;
        while (listID.contains(nextID)) {
            nextID++;
        }

        return "D" + String.format("%04d", nextID);
    }
    public List<DrinkModel> getDrinkForOrder(List<String> drinkID) {
        List<DrinkModel> drinks = new ArrayList<>();

        // Nếu danh sách drinkIDs rỗng, trả về danh sách rỗng
        if (drinkID.isEmpty()) {
            return drinks;
        }

        // Tạo câu lệnh SQL với điều kiện IN cho các ID đồ uống
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM Drinks WHERE DrinkID IN (");
        for (int i = 0; i < drinkID.size(); i++) {
            queryBuilder.append("?");
            if (i < drinkID.size() - 1) {
                queryBuilder.append(", ");
            }
        }
        queryBuilder.append(")");

        String query = queryBuilder.toString();

        try ( PreparedStatement ps = connection.prepareStatement(query)) {
            // Thiết lập các tham số cho câu lệnh PreparedStatement
            for (int i = 0; i < drinkID.size(); i++) {
                ps.setString(i + 1, drinkID.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DrinkModel p = new DrinkModel(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getBigDecimal(6),rs.getInt(7),rs.getString(8));
                drinks.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }   

        return drinks;
    }
}
