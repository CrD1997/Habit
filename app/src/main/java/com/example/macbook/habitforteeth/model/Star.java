package com.example.macbook.habitforteeth.model;

/**
 * Created by MacBook on 2018/12/1.
 */

public class Star {
    public int id;
    public int week;
    public int day;
    public int number;

    public Star(){}

    public Star(int id,int w,int d,int n) {
        this.id=id;
        this.week=w;
        this.day=d;
        this.number=n;
    }

    public int getId() {
        return id;
    }

    public int getWeek(){
        return week;
    }

    public int getDay() {
        return day;
    }

    public int getNumber() {
        return number;
    }
}
