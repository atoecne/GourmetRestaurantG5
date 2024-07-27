/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.math.BigDecimal;

/**
 *
 * @author Admin
 */
public class DrinkModel {

    private String drinkID;
    private String drinkImg;
    private String drinkName;
    private String beverageID;
    private String description;
    private BigDecimal price;
    private int quantity;
    private String unit;

    public DrinkModel() {
    }

    public DrinkModel(String drinkID, String drinkImg, String drinkName, String beverageID, String description, BigDecimal price, int quantity, String unit) {
        this.drinkID = drinkID;
        this.drinkImg = drinkImg;
        this.drinkName = drinkName;
        this.beverageID = beverageID;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getDrinkID() {
        return drinkID;
    }

    public void setDrinkID(String drinkID) {
        this.drinkID = drinkID;
    }

    public String getDrinkImg() {
        return drinkImg;
    }

    public void setDrinkImg(String drinkImg) {
        this.drinkImg = drinkImg;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public String getBeverageID() {
        return beverageID;
    }

    public void setBeverageID(String beverageID) {
        this.beverageID = beverageID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

   
}
