package com.healthtracker.model;

/**
 * Created by local nidhi on 27-04-2016.
 */
public class Glucose {

    float glucose, hba1c;
    String note, date, time, testingTime;
    int userId, rowId;

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public String getTestingTime() {
        return testingTime;
    }

    public void setTestingTime(String testingTime) {
        this.testingTime = testingTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

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

    public float getGlucose() {
        return glucose;
    }

    public void setGlucose(float glucose) {
        this.glucose = glucose;
    }

    public float getHba1c() {
        return hba1c;
    }

    public void setHba1c(float hba1c) {
        this.hba1c = hba1c;
    }
}
