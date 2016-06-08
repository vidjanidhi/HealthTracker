package com.healthtracker.model;

/**
 * Created by local nidhi on 27-04-2016.
 */
public class cholesterol {

    float ldl, hdl, total, triglyceride;
    String note, date, time;
    int userId, rowId;

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
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

    public float getLdl() {
        return ldl;
    }

    public void setLdl(float ldl) {
        this.ldl = ldl;
    }

    public float getHdl() {
        return hdl;
    }

    public void setHdl(float hdl) {
        this.hdl = hdl;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getTriglyceride() {
        return triglyceride;
    }

    public void setTriglyceride(float triglyceride) {
        this.triglyceride = triglyceride;
    }
}
