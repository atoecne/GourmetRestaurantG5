/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.BeverageModel;
import Model.CategoryModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class BeverageDAO extends DBContext{
    public List<BeverageModel> getAllBeverage() {
        List<BeverageModel> list = new ArrayList<>();
        String query = "SELECT * FROM [dbo].[Beverages] ORDER BY BeverageID ASC";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BeverageModel p = new BeverageModel(rs.getString(1), rs.getString(2));
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("loi getAll");
        }
        return list;
    }
}
