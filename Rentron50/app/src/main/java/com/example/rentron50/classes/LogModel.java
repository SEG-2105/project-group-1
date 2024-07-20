package com.example.rentron50.classes;

import java.util.Date;

public class LogModel {
    private int id;
    private int ticketId;
    private String date;
    private int status;

    public LogModel(int id, int ticketId, String date, int status) {
        this.id = id;
        this.ticketId = ticketId;
        this.date = date;
        this.status = status;
    }

    public LogModel(int ticketId, int status) {
        this.id=-1;
        this.ticketId = ticketId;
        this.status = status;
        this.date=new Date().toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        String log=date+"-";
        String Status=(status==0)?"RESOLVED":(status==1)?"Rejected":(status==2)?"In Progress":"Limbo";
        return log+Status;
    }
}
