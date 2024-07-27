/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.math.BigDecimal;

/**
 *
 * @author LENOVO
 */
public class ItemModel {
    private FoodModel foodModel;
    private DrinkModel DrinkModel;
    private int quantity;
    private BigDecimal price;

    public ItemModel() {
    }

    public ItemModel(FoodModel foodModel, DrinkModel DrinkModel, int quantity, BigDecimal price) {
        this.foodModel = foodModel;
        this.DrinkModel = DrinkModel;
        this.quantity = quantity;
        this.price = price;
    }

    public FoodModel getFoodModel() {
        return foodModel;
    }

    public void setFoodModel(FoodModel foodModel) {
        this.foodModel = foodModel;
    }

    public DrinkModel getDrinkModel() {
        return DrinkModel;
    }

    public void setDrinkModel(DrinkModel DrinkModel) {
        this.DrinkModel = DrinkModel;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
}
