/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

/**
 *
 * @author Tuyet
 */
public class OrderModel {
     private String orderID;
    private String emailServe;
    private String emailCashier;
    private LocalDateTime orderDate;
    private String couponID;
    private String phoneNumber;
    private BigDecimal totalAmount;
    private int status;

    public OrderModel() {
    }

    public OrderModel(String orderID, String emailServe, String emailCashier, LocalDateTime orderDate, String couponID, String phoneNumber, BigDecimal totalAmount, int status) {
        this.orderID = orderID;
        this.emailServe = emailServe;
        this.emailCashier = emailCashier;
        this.orderDate = orderDate;
        this.couponID = couponID;
        this.phoneNumber = phoneNumber;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getEmailServe() {
        return emailServe;
    }

    public void setEmailServe(String emailServe) {
        this.emailServe = emailServe;
    }

    public String getEmailCashier() {
        return emailCashier;
    }

    public void setEmailCashier(String emailCashier) {
        this.emailCashier = emailCashier;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getCouponID() {
        return couponID;
    }

    public void setCouponID(String couponID) {
        this.couponID = couponID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    
}
