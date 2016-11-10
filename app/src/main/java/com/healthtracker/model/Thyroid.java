package com.healthtracker.model;

/**
 * Created by local nidhi on 27-04-2016.
 */
public class Thyroid {

    float tshLevel;
    int userId, rowId;

    public void setByteArray(String byteArray) {
        this.ImagePath = byteArray;
    }

    public String ImagePath;

    public String getbyteArray() {
        return ImagePath;
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    String note;
    String date;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    String time;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getTshLevel() {
        return tshLevel;
    }

    public void setTshLevel(float tshLevel) {
        this.tshLevel = tshLevel;
    }
}
