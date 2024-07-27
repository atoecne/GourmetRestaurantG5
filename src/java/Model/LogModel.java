/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class LogModel {

    private int logID;
    private String email;
    private String action;
    private Timestamp createdAt;

    public LogModel() {
    }

    public LogModel(int logID, String email, String action, Timestamp createdAt) {
        this.logID = logID;
        this.email = email;
        this.action = action;
        this.createdAt = createdAt;
    }

    public int getLogID() {
        return logID;
    }

    public void setLogID(int logID) {
        this.logID = logID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "LogModel{"
                + "logID=" + logID
                + ", email='" + email + '\''
                + ", action='" + action + '\''
                + ", createdAt=" + createdAt
                + '}';
    }
}
