package com.example.yellowobjects.ui.schedule;

import java.util.Date;

public class EventObject {
    String title, desc, venue;
    int id;
    Date startdt, enddt;
    public String getTitle(){
        return title;
    }
    public String getDesc(){
        return desc;
    }
    public String getVenue(){
        return venue;
    }
    public Date getStartdt(){
        return startdt;
    }
    public Date getEnddt() {
        return enddt;
    }
    public EventObject(int id, String title, String desc, String venue, Date startdt, Date enddt){
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.venue = venue;
        this.startdt = startdt;
        this.enddt = enddt;
    }
}
