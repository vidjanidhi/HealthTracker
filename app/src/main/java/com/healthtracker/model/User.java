package com.healthtracker.model;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by local nidhi on 24-04-2016.
 */
public class User {

    public static final int MALE = 0;
    public static final int FEMALE = 1;

    int userId, age, height, gender;
    String userName;

    public int getUserId() {
        return userId;
    }

    public int getAge() {
        return age;
    }

    public int getHeight() {
        return height;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setAge(int _year, int _month, int _day) {
        this.age = getAge(_year, _month, _day);
    }

    public void setAge(int age) {
        this.age = age;

    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getGender() {
        return gender;

    }

    public int getAge(int _year, int _month, int _day) {

        GregorianCalendar cal = new GregorianCalendar();
        int y, m, d, a;

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(_year, _month, _day);
        a = y - cal.get(Calendar.YEAR);
        if ((m < cal.get(Calendar.MONTH))
                || ((m == cal.get(Calendar.MONTH)) && (d < cal
                .get(Calendar.DAY_OF_MONTH)))) {
            --a;
        }
        if (a < 0)
            throw new IllegalArgumentException("Age < 0");
        return a;
    }

}
