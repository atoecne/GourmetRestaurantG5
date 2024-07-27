/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class CartModel {

    private List<ItemModel> items;

    public CartModel() {
        items = new ArrayList<>();
    }

    public CartModel(List<ItemModel> items) {
        this.items = items;
    }

    public List<ItemModel> getItems() {
        return items;
    }

    public void setItems(List<ItemModel> items) {
        this.items = items;
    }

    private ItemModel getItemByFoodID(String id) {
        for (ItemModel item : items) {
            if (item.getFoodModel() != null && item.getFoodModel().getFoodID().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return null;
    }

    private ItemModel getItemByDrinkID(String id) {
        for (ItemModel item : items) {
            if (item.getDrinkModel() != null && item.getDrinkModel().getDrinkID().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return null;
    }

    public int getQuantityById(String id) {
        ItemModel foodItem = getItemByFoodID(id);
        if (foodItem != null) {
            return foodItem.getQuantity();
        } else {
            ItemModel drinkItem = getItemByDrinkID(id);
            if (drinkItem != null) {
                return drinkItem.getQuantity();
            }
        }
        return 0; // If item not found, return 0
    }

    public void updateItemQuantity(String id, int quantity) {
        for (ItemModel item : items) {
            if (item.getFoodModel() != null && item.getFoodModel().getFoodID().equals(id)) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            } else if (item.getDrinkModel() != null && item.getDrinkModel().getDrinkID().equals(id)) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
    }

    //add them vao gio hang
    public void addItem(ItemModel item) {
        if (item.getFoodModel() != null) {
            ItemModel existingItem = getItemByFoodID(item.getFoodModel().getFoodID());
            if (existingItem != null) {
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
            } else {
                items.add(item);
            }
        } else if (item.getDrinkModel() != null) {
            ItemModel existingItem = getItemByDrinkID(item.getDrinkModel().getDrinkID());
            if (existingItem != null) {
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
            } else {
                items.add(item);
            }
        }
    }

    public void removeItem(String id) {
        ItemModel itemToRemove = getItemByFoodID(id);
        if (itemToRemove != null) {
            items.remove(itemToRemove);
            System.out.println("Removed item: " + itemToRemove);
        } else {
            itemToRemove = getItemByDrinkID(id);
            if (itemToRemove != null) {
                items.remove(itemToRemove);
                System.out.println("Removed item: " + itemToRemove);
            }
        }
    }

    public BigDecimal getTotalMoney() {
        BigDecimal t = BigDecimal.ZERO;
        for (ItemModel i : items) {
            BigDecimal quantity = new BigDecimal(i.getQuantity());
            BigDecimal price = i.getPrice();
            BigDecimal itemTotal = quantity.multiply(price);
            t = t.add(itemTotal);
        }
        return t;
    }
}
