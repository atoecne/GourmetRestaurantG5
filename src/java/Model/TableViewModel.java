/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Nguyen Thanh Huy - CE171915
 */
public class TableViewModel {

    private String tableID;
    private String floor;
    private String tableImg;
    private String tableNumber;
    private int seatingCapacity;
    private int status;

    public TableViewModel() {
    }

    public TableViewModel(String tableID, String floor, String tableImg, String tableNumber, int seatingCapacity, int status) {
        this.tableID = tableID;
        this.floor = floor;
        this.tableImg = tableImg;
        this.tableNumber = tableNumber;
        this.seatingCapacity = seatingCapacity;
        this.status = status;
    }

    public String getTableID() {
        return tableID;
    }

    public void setTableID(String tableID) {
        this.tableID = tableID;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getTableImg() {
        return tableImg;
    }

    public void setTableImg(String tableImg) {
        this.tableImg = tableImg;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    
}
