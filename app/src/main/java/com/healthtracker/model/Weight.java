package com.healthtracker.model;

/**
 * Created by local nidhi on 27-04-2016.
 */
public class Weight {

    float weight, Abdomen, waist, hips;
    int fat, userId, rowId;

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    String note, date, time;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getAbdomen() {
        return Abdomen;
    }

    public void setAbdomen(float abdomen) {
        Abdomen = abdomen;
    }

    public float getWaist() {
        return waist;
    }

    public void setWaist(float waist) {
        this.waist = waist;
    }

    public float getHips() {
        return hips;
    }

    public void setHips(float hips) {
        this.hips = hips;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }
}
