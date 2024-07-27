/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.CouponsModel;
import Model.TableModel;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nguyen Thanh Huy - CE171915
 */
public class CouponDAO extends DBContext {

    public CouponsModel checkCouponID(String couponID) {
        String query = "Select * from Coupons where CouponID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, couponID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                CouponsModel c = new CouponsModel(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getInt(5), rs.getDate(6).toLocalDate(), rs.getDate(7).toLocalDate(), rs.getInt(8));
                return c;
            }
        } catch (Exception e) {
            System.out.println("CouponID is exist");
        }
        return null;
    }

    public boolean createCoupons(CouponsModel c) throws SQLException {
        String query = "INSERT INTO Coupons VALUES (?,?,?,?,?,?,?,?);";
        try ( PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, c.getCouponID());
            ps.setString(2, c.getCouponImg());
            ps.setString(3, c.getCouponDescription());
            ps.setString(4, c.getDiscountType());
            ps.setInt(5, c.getDiscountValue());
            ps.setDate(6, Date.valueOf(c.getStartDate()));
            ps.setDate(7, Date.valueOf(c.getEndDate()));
            ps.setInt(8, c.getActive());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new SQLException("Error creating coupon", e);
        }
    }

    public List<CouponsModel> getAll() {
        List<CouponsModel> list = new ArrayList<>();
        String query = "Select * From Coupons";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CouponsModel c = new CouponsModel(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getInt(5), rs.getDate(6).toLocalDate(), rs.getDate(7).toLocalDate(), rs.getInt(8));
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println("loi getAll");
        }
        return list;
    }

    public boolean updateCoupon(CouponsModel c) {
        String query = "UPDATE Coupons\n"
                + "   SET CouponImg = ?\n"
                + "      ,Description = ?\n"
                + "      ,DiscountType = ?\n"
                + "      ,DiscountValue = ?\n"
                + "      ,StartDate = ?\n"
                + "      ,EndDate = ?\n"
                + "      ,Active = ?\n"
                + " WHERE CouponID = ?";  
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, c.getCouponImg());
            ps.setString(2, c.getCouponDescription());
            ps.setString(3, c.getDiscountType());
            ps.setInt(4, c.getDiscountValue());
            ps.setDate(5, Date.valueOf(c.getStartDate()));
            ps.setDate(6, Date.valueOf(c.getEndDate()));
            ps.setInt(7, c.getActive());
            ps.setString(8, c.getCouponID()); 
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;  
        }
    }

    public void deleteCoupon(String couponID) {
        String query = "DELETE FROM [dbo].[Coupons]\n"
                + "      WHERE CouponID=?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, couponID);
            ps.executeUpdate();
            System.out.println("delete success");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
     public List<CouponsModel> searchCoupon(String coupon) {
        List<CouponsModel> list = new ArrayList<>();
        String query = "SELECT * FROM Coupons WHERE StartDate <= ? AND EndDate >= ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, coupon);
            ps.setString(2, coupon);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CouponsModel c = new CouponsModel(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getInt(5), rs.getDate(6).toLocalDate(), rs.getDate(7).toLocalDate(), rs.getInt(8));
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Search Errol: " + e);
        }
        return list;
    }

}
