/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author Thanh Cong
 */
public class RevenueReportModel {
      private Date date;
    private int orderQuantity;
    private BigDecimal totalPrice;

    public RevenueReportModel() {
    }

    public RevenueReportModel(Date date, int orderQuantity, BigDecimal totalPrice) {
        this.date = date;
        this.orderQuantity = orderQuantity;
        this.totalPrice = totalPrice;
    }



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    
}
