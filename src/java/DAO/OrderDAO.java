/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.CartModel;
import Model.ItemModel;
import Model.OrderDetailModel;
import Model.OrderModel;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class OrderDAO extends DBContext {

    public boolean addOrder(CartModel c, OrderModel o, String tableID) {
        boolean isSuccess = false;
        try {

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);

            if (o.getOrderID() != null) {
                String sql = "INSERT INTO Orders (OrderID, EmailServe, OrderDate, CouponID, PhoneNumber, TotalAmount, Status) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement st = connection.prepareStatement(sql);
                st.setString(1, o.getOrderID());
                st.setString(2, o.getEmailServe());
                st.setString(3, formattedDateTime);
                st.setString(4, o.getCouponID());
                st.setString(5, o.getPhoneNumber());
                st.setBigDecimal(6, c.getTotalMoney());
                st.setInt(7, 0);
                st.executeUpdate();
                st.close();
            } else {
                String sql = "UPDATE Orders SET TotalAmount = ? WHERE OrderID = ?";
                PreparedStatement st = connection.prepareStatement(sql);
                st.setBigDecimal(1, c.getTotalMoney());
                st.setString(2, o.getOrderID());
                st.executeUpdate();
                st.close();
            }

            for (ItemModel item : c.getItems()) {
                if (isItemExistsInOrder(o.getOrderID(), item, tableID)) {
                    updateItemQuantityInOrder(o.getOrderID(), item, tableID);
                } else {
                    insertNewItemIntoOrder(o.getOrderID(), item, tableID);
                }
            }
            isSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    public void addEmailCashier(String email, String OrderID) throws SQLException {
        String sql = "UPDATE Orders SET EmailCashier = ? WHERE OrderID = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, OrderID);
        ps.executeUpdate();
        ps.close();
    }

    public boolean updateOrder(CartModel c, String orderID, String tableID) {
        boolean isSuccess = false;
        try {
            String sqlUpdateOrder = "UPDATE Orders SET TotalAmount = (SELECT SUM(od.TotalPrice) FROM OrderDetail od WHERE od.OrderID = Orders.OrderID) WHERE OrderID = ?";
            PreparedStatement psUpdateOrder = connection.prepareStatement(sqlUpdateOrder);
            psUpdateOrder.setString(1, orderID);
            psUpdateOrder.executeUpdate();
            psUpdateOrder.close();

            for (ItemModel item : c.getItems()) {
                if (isItemExistsInOrder(orderID, item, tableID)) {
                    updateItemQuantityInOrder(orderID, item, tableID);
                } else {
                    insertNewItemIntoOrder(orderID, item, tableID);
                }
            }
            isSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    private boolean isItemExistsInOrder(String orderID, ItemModel item, String tableID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM OrderDetail WHERE OrderID = ? AND (FoodID = ? OR DrinkID = ?) AND TableID = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, orderID);
        ps.setString(2, item.getFoodModel() != null ? item.getFoodModel().getFoodID() : null);
        ps.setString(3, item.getDrinkModel() != null ? item.getDrinkModel().getDrinkID() : null);
        ps.setString(4, tableID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        boolean exists = rs.getInt(1) > 0;
        ps.close();
        return exists;
    }

    private void updateItemQuantityInOrder(String orderID, ItemModel item, String tableID) throws SQLException {
        String sqlUpdate = "UPDATE OrderDetail SET Quantity = Quantity + ?, TotalPrice = TotalPrice + ? WHERE OrderID = ? AND (FoodID = ? OR DrinkID = ?) AND TableID = ?";
        PreparedStatement ps = connection.prepareStatement(sqlUpdate);
        ps.setInt(1, item.getQuantity());
        ps.setBigDecimal(2, item.getPrice().multiply(new BigDecimal(item.getQuantity())));
        ps.setString(3, orderID);
        ps.setString(4, item.getFoodModel() != null ? item.getFoodModel().getFoodID() : null);
        ps.setString(5, item.getDrinkModel() != null ? item.getDrinkModel().getDrinkID() : null);
        ps.setString(6, tableID);
        ps.executeUpdate();
        ps.close();
    }

    private void insertNewItemIntoOrder(String orderID, ItemModel item, String tableID) throws SQLException {
        String sqlInsert = "INSERT INTO OrderDetail (OrderID, FoodID, DrinkID, TableID, Quantity, TotalPrice) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sqlInsert);
        ps.setString(1, orderID);
        System.out.println(orderID);
        ps.setString(2, item.getFoodModel() != null ? item.getFoodModel().getFoodID() : null);
        ps.setString(3, item.getDrinkModel() != null ? item.getDrinkModel().getDrinkID() : null);
        ps.setString(4, tableID);
        ps.setInt(5, item.getQuantity());
        ps.setBigDecimal(6, item.getPrice().multiply(new BigDecimal(item.getQuantity())));
        ps.executeUpdate();
        ps.close();
    }

    public String getNextOrderID() {
        List<Integer> listID = new ArrayList<>();
        String query = "SELECT OrderID FROM Orders ORDER BY OrderID";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String orderID = rs.getString(1);
                int IDNumber = Integer.parseInt(orderID.substring(1));
                listID.add(IDNumber);
            }
        } catch (NumberFormatException | SQLException e) {
            System.out.println("Add ID error");
        }
        int nextID = 1;
        while (listID.contains(nextID)) {
            nextID++;
        }
        return "O" + String.format("%04d", nextID);
    }

// Thêm phương thức cập nhật trạng thái đơn hàng
    public void updateOrderStatus(String orderID, int status) throws SQLException {
        String sql = "UPDATE Orders SET Status = ? WHERE OrderID = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, status);
        ps.setString(2, orderID);
        ps.executeUpdate();
        ps.close();
    }

    public int getOrderStatus(String orderID) {
        int status = 0;
        String query = "SELECT status FROM Orders Where OrderID=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, orderID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                status = rs.getInt(1);
            }
        } catch (SQLException e) {
        }
        return status;
    }

    public String getOrderIDByTableID(String tableID) {
        String orderID = null;
        String query = "SELECT TOP 1 OrderID FROM OrderDetail WHERE TableID = ? ORDER BY OrderID DESC";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, tableID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                orderID = rs.getString(1);
            }
        } catch (SQLException e) {
        }
        return orderID;
    }

    public List<OrderModel> getOrder() {
        List<OrderModel> listOrder = new ArrayList<>();
        String sql = "SELECT * FROM Orders";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Timestamp timestamp = rs.getTimestamp(4);
                LocalDateTime orderDateTime = null;
                if (timestamp != null) {
                    orderDateTime = timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                }
                OrderModel om = new OrderModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        orderDateTime,
                        rs.getString(5),
                        rs.getString(6),
                        rs.getBigDecimal(7),
                        rs.getInt(8));
                listOrder.add(om);
            }
        } catch (SQLException e) {
            System.out.println("Error");
        }
        return listOrder;
    }

    public List<OrderDetailModel> getOrderDetailsByOrderID(String orderID) {
        List<OrderDetailModel> orderDetails = new ArrayList<>();
        String sql = "SELECT OrderDetailID, OrderID,  FoodID, DrinkID, TableID, Quantity, TotalPrice FROM OrderDetail WHERE OrderID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, orderID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderDetailModel orderDetail = new OrderDetailModel(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getBigDecimal(7));
                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            System.out.println("Error G");
        }
        return orderDetails;
    }

    public List<OrderDetailModel> getOrderDetailsByTableID(String tableID) throws SQLException {
        List<OrderDetailModel> orderDetails = new ArrayList<>();
        String sql = "SELECT OrderDetailID, OrderID,  FoodID, DrinkID, TableID,Quantity, TotalPrice "
                + "FROM OrderDetail "
                + "WHERE TableID = ?";
        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, tableID);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderDetailModel orderDetail = new OrderDetailModel(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getInt(6),
                            rs.getBigDecimal(7));
                    orderDetails.add(orderDetail);
                }
            }
        }
        return orderDetails;
    }

    public OrderModel getOrderById(String orderID) {
        OrderModel orderModel = null;
        String sql = "SELECT * FROM Orders WHERE OrderID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, orderID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                orderModel = new OrderModel();
                orderModel.setEmailServe(rs.getString("EmailServe"));
                orderModel.setTotalAmount(rs.getBigDecimal("TotalAmount"));

                Timestamp orderTimestamp = rs.getTimestamp("OrderDate");
                LocalDateTime orderDateTime = orderTimestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                orderModel.setOrderDate(orderDateTime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderModel;
    }
    
    public void updateTotalPrice(BigDecimal total, String orderID){
        String query = "UPDATE Orders SET TotalAmount = ? WHERE OrderID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setBigDecimal(1, total);
            ps.setString(2, orderID);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public LocalDate getMaxDate() {
        LocalDate maxDate = null;
        String query = "SELECT CONVERT(DATE, MAX(OrderDate)) FROM Orders;";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                maxDate = rs.getDate(1).toLocalDate();
            }
        } catch (SQLException e) {
        }
        return maxDate;
    }

    public int getQuatityOfOrder(Date orderDate) {
        int count = 0;
        String query = "select COUNT(*) from Orders WHERE CONVERT(date, OrderDate) = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setDate(1, orderDate);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
        }
        return count;
    }

    public BigDecimal getTotalAmountOfOrder(Date orderDate) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        String query = "select SUM(TotalAmount) from Orders WHERE CONVERT(date, OrderDate) = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setDate(1, orderDate);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalAmount = rs.getBigDecimal(1);
            }
        } catch (SQLException e) {
        }
        return totalAmount;
    }

    public List<Integer> getYearsWithOrders() {
        List<Integer> years = new ArrayList<>();
        String query = "SELECT DISTINCT YEAR(OrderDate) AS Year FROM Orders ORDER BY Year";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                years.add(rs.getInt("Year"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return years;
    }

    public List<String> getTopFoodOrder() {
        List<String> listFoodID = new ArrayList<>();
        String query = "SELECT TOP 3 FoodID, COUNT(*) as count from OrderDetail GROUP BY FoodID ORDER BY count DESC ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String foodID = rs.getString("FoodID");
                listFoodID.add(foodID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listFoodID;
    }
}
