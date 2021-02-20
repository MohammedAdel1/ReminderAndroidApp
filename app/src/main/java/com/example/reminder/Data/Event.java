package com.example.reminder.Data;

public class Event {

    private int id;
    private String title;
    private String date;
    private String time;
    private float budget;


    public Event (){

    }// end Default Constructor

    public Event(String title, String date , String time, float budget){
        this.title= title;
        this.date=date;
        this.time=time;
        this.budget=budget;
    }// end Constructor 1

    public Event(int id,String title, String date , String time, float budget){
        this.id = id;
        this.title= title;
        this.date=date;
        this.time=time;
        this.budget=budget;
    }// end Constructor 2

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }
}// end Class
