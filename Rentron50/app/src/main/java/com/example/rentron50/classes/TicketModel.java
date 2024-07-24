package com.example.rentron50.classes;
import java.util.Date;
public class TicketModel {
    private int id;
    private int propertyId;
    private String type;
    private int urgency;
    private String message;
    private String date;
    private int status;
    private int pmID;
    private int rating;

    public TicketModel(int id, int propertyId, int pmID,String type, int urgency, String message, String date, int status, int rating) {
        this.id = id;
        this.propertyId = propertyId;
        this.type = type;
        this.urgency = urgency;
        this.message = message;
        this.date = date;
        this.status = status;
        this.pmID = pmID;
        this.rating = rating;
    }

    public TicketModel(int propertyId,int pmID, String type, int urgency, String message) {
        this.id=-1;
        this.propertyId = propertyId;
        this.type = type;
        this.urgency = urgency;
        this.message = message;
        this.date = new Date().toString();
        this.status =-1;
        this.rating=-1;
        this.pmID=pmID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUrgency() {
        return urgency;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
        String ticket=type+"-"+"Urgency : "+urgency+" ";
        String Status= (status==0)?"RESOLVED":(status==1)?"Rejected":(status==2)?"In Progress":"Limbo";
        ticket=ticket+Status;
        return ticket;
    }

    public int getPmID() {
        return pmID;
    }

    public void setPmID(int pmID) {
        this.pmID = pmID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
