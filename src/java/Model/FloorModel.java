/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Nguyen Thanh Huy - CE171915
 */
public class FloorModel {

    private String FloorID;
    private int Floor;

    public FloorModel() {
    }

    public FloorModel(String FloorID, int Floor) {
        this.FloorID = FloorID;
        this.Floor = Floor;
    }

    public String getFloorID() {
        return FloorID;
    }

    public void setFloorID(String FloorID) {
        this.FloorID = FloorID;
    }

    public int getFloor() {
        return Floor;
    }

    public void setFloor(int Floor) {
        this.Floor = Floor;
    }

}
