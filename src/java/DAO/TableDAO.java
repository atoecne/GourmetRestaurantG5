/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.TableModel;
import Model.TableViewModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nguyen Thanh Huy - CE171915
 */
public class TableDAO extends DBContext {
    
    public List<TableModel> getAllTables() {
        List<TableModel> tableList = new ArrayList<>();
        String query = "select * from Tables";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                TableModel t = new TableModel(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6));
                tableList.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableList;
    }

    public String createTableIDAuto() {
        List<Integer> listID = new ArrayList<>();
        String query = "SELECT TableID FROM Tables ORDER BY TableID";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String tableID = rs.getString(1);
                int IDNumber = Integer.parseInt(tableID.substring(1));
                listID.add(IDNumber);
            }
        } catch (NumberFormatException | SQLException e) {
            System.out.println("Create ID Error: " + e);
        }
        int nextID = 1;
        while (listID.contains(nextID)) {
            nextID++;
        }
        return "T" + String.format("%04d", nextID);
    }

    public TableModel checkTableNumber(String floorID, String tableNumber) {
        String query = "SELECT * FROM Tables WHERE FloorID like ? AND TableNumber like ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, floorID);
            ps.setString(2, "%" + tableNumber + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                TableModel t = new TableModel(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getInt(5), rs.getInt(6));
                return t;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void addNewTable(TableModel t) {
        String query = "INSERT INTO Tables VALUES (?,?,?,?,?,?);";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, t.getTableID());
            ps.setString(2, t.getFloorID());
            ps.setString(3, t.getTableImg());
            ps.setString(4, t.getTableNumber());
            ps.setInt(5, t.getSeatingCapacity());
            ps.setInt(6, t.getStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Add Errol: " + e);
        }
    }

    public List<TableViewModel> getAll() {
        List<TableViewModel> list = new ArrayList<>();
        String query = "SELECT t.TableID,f.Floor,t.TableImg, t.TableNumber, t.SeatingCapacity, t.[Status] "
                + "FROM  [Tables] t JOIN Floors f ON t.FloorID = f.FloorID ORDER BY t.TableID;";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TableViewModel t = new TableViewModel(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getInt(5), rs.getInt(6));
                list.add(t);
            }
        } catch (SQLException e) {
            System.out.println("Errol getAll: " + e);
        }
        return list;
    }

    public TableModel getTableById(String id) {
        String query = "SELECT * FROM Tables WHERE TableID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                TableModel t = new TableModel(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getInt(5), rs.getInt(6));
                System.out.println("ID ne");
                return t;
            }
        } catch (SQLException e) {
            System.out.println("Errol getTableID: " + e);
        }
        return null;
    }

    public boolean updateTable(TableModel t) {
        String query = "UPDATE [dbo].[Tables]  SET [FloorID] = ? ,[TableImg] = ? ,[TableNumber] = ? "
                + "      ,[SeatingCapacity] = ? ,[Status] = ? WHERE [TableID] = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, t.getFloorID());
            ps.setString(2, t.getTableImg());
            ps.setString(3, t.getTableNumber());
            ps.setInt(4, t.getSeatingCapacity());
            ps.setInt(5, t.getStatus());
            ps.setString(6, t.getTableID());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Errol UpdateTable: " + e);
            return false;
        }
    }

    public void deleteTable(String tableID) {
        String query = "DELETE FROM [dbo].[Tables]   WHERE TableID=?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, tableID);
            ps.executeUpdate();
            System.out.println("delete success");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public List<TableViewModel> searchAjexFloor(String floor) {
        List<TableViewModel> list = new ArrayList<>();
        String query = "SELECT T.TableID, F.Floor, T.TableImg, T.TableNumber, T.SeatingCapacity, T.[Status] "
                + "FROM [Tables] T JOIN Floors F ON T.FloorID = F.FloorID "
                + "WHERE F.Floor LIKE ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, "%" + floor + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TableViewModel t = new TableViewModel(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getInt(5), rs.getInt(6));
                list.add(t);
            }
        } catch (SQLException e) {
            System.out.println("Search Errol: " + e);
        }
        return list;
    }

    public List<TableViewModel> searchTableNumber(String tableNumber) {
        List<TableViewModel> list = new ArrayList<>();
        String query = "SELECT T.TableID, F.Floor, T.TableImg, T.TableNumber, T.SeatingCapacity, T.[Status] "
                + "FROM [Tables] T JOIN Floors F ON T.FloorID = F.FloorID "
                + "WHERE T.TableNumber LIKE ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, "%"+tableNumber);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TableViewModel t = new TableViewModel(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getInt(5), rs.getInt(6));
                list.add(t);
            }
        } catch (SQLException e) {
            System.out.println("Search Errol: " + e);
        }
        return list;
    }
    
    public List<TableModel> getTablesByFloor(String floorID) {
    List<TableModel> tables = new ArrayList<>();
        String query = "SELECT * FROM Tables WHERE FloorID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, floorID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TableModel t = new TableModel(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getInt(5), rs.getInt(6));
                tables.add(t);
            }
        } catch (SQLException e) {
            System.out.println("Errol getAll: " + e);
        }
        return tables;
    }
}
