package com.originals.meraqi;

public class scheduleDetails {
    private int month;
    private int year;
    private int date;
    private int hour;
    private int minute;
    private String location;
    private String title;
    private String duration;
    private String description;
    private boolean isExpanded;

    public scheduleDetails(int month, int year, int date, int hour, int minute, String location, String title, String duration, String description) {
        this.month = month;
        this.year = year;
        this.date = date;
        this.hour = hour;
        this.minute = minute;
        this.location = location;
        this.title = title;
        this.duration = duration;
        this.description = description;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
