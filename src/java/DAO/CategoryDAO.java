/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

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
public class CategoryDAO extends DBContext {

    public List<CategoryModel> getAllCategory() {
        List<CategoryModel> list = new ArrayList<>();
        String query = "SELECT * FROM [dbo].[Categories] ORDER BY CategoryID ASC";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CategoryModel p = new CategoryModel(rs.getString(1), rs.getString(2));
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("loi getAll");
        }
        return list;
    }
}
