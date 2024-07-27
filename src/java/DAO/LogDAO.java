/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.LogModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nguyen Thanh Huy - CE171915
 */
public class LogDAO extends DBContext {

    public List<LogModel> getAll() {
        List<LogModel> list = new ArrayList<>();
        String query = "SELECT*FROM Log ORDER BY LogID ASC";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LogModel l = new LogModel(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getTimestamp(4));
                list.add(l);
            }
        } catch (SQLException e) {
            System.out.println("loi getAll");
        }
        return list;
    }

    public List<LogModel> searchLog(String action) {
        List<LogModel> list = new ArrayList<>();
        String query = "SELECT * FROM Log WHERE Action like ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, action + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LogModel l = new LogModel(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getTimestamp(4));
                list.add(l);
            }
        } catch (SQLException e) {
            System.out.println("search error");
        }
        return list;
    }


    public boolean createLog(LogModel log) {
        String query = "INSERT INTO [Log] (Email, [Action], CreatedAt) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, log.getEmail());
            ps.setString(2, log.getAction());
            ps.setTimestamp(3, log.getCreatedAt());
            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteOldLogs() {
        String query = "DELETE FROM Log WHERE CreatedAt < ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            Timestamp oneMonthAgo = new Timestamp(System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000);
            ps.setTimestamp(1, oneMonthAgo);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
