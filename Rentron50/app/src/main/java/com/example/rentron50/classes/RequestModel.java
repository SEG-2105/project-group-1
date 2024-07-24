package com.example.rentron50.classes;

import java.util.Date;

public class RequestModel {
    private int requestId;
    private int senderId;
    private int propertyId;
    private int recipientId;
    private int commission;
    private String date;
    private int isActive; //0 for no 1 for yes
    private int type; //0 for renting 1 for requesting propertyManagers

    public RequestModel(int requestId, int senderId, int propertyId, int recipientId, int type, int isActive,int comission,String date) {
        this.requestId = requestId;
        this.senderId=senderId;
        this.propertyId = propertyId;
        this.recipientId = recipientId;
        this.type = type;
        this.isActive=isActive;
        this.commission=comission;
        this.date=date;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public RequestModel(int propertyId, int senderId, int recipientId, int type) {
        this.propertyId = propertyId;
        this.senderId=senderId;
        this.recipientId = recipientId;
        this.type = type;
        this.isActive=1;
        this.requestId=-1;
        this.commission=0;
        this.date=new Date().toString();
    }public RequestModel(int propertyId, int senderId, int recipientId, int type,int commission) {
        this.propertyId = propertyId;
        this.senderId=senderId;
        this.recipientId = recipientId;
        this.type = type;
        this.isActive=1;
        this.requestId=-1;
        this.commission=commission;
        this.date=new Date().toString();
    }

    public int getCommission() {
        return commission;
    }

    public void setCommission(int commission) {
        this.commission = commission;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setActive(int isActive) {
        this.isActive = isActive;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        String active=(isActive==1)?"Active":"Not Active";
        switch(type){
            case 0:
                return date+","+propertyId+","+active;
            case 1:
                return commission+"% "+date+","+propertyId+","+active;
            default:
                return "";
        }
    }
}
