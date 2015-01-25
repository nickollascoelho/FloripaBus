package com.arctouch.floripabus.model;

public class Departure extends AbstractEntity {

    private String calendar;
    private String time;

    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
