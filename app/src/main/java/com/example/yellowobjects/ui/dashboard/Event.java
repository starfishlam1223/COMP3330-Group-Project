package com.example.yellowobjects.ui.dashboard;

public class Event {
    String image, title, desc, id;
    int year, month, day, hour, minute, second;

    public Event(String image, String title, String desc, String id, int year, int month, int day, int hour, int minute, int second) {
        this.image = image;
        this.title = title;
        this.desc = desc;
        this.id = id;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
    }

    public String getDesc() {
        return desc;
    }

    public String getId() {
        return id;
    }
}