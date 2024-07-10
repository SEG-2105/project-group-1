package com.example.rentron40;

public class RequestModel {
    private int requestId;
    private int senderId;
    private int propertyId;
    private int recipientId;
    private int isActive; //0 for no 1 for yes
    private int type; //0 for renting 1 for requesting propertyManagers

    public RequestModel(int requestId,int senderId, int propertyId, int recipientId, int type,int isActive) {
        this.requestId = requestId;
        this.senderId=senderId;
        this.propertyId = propertyId;
        this.recipientId = recipientId;
        this.type = type;
        this.isActive=isActive;
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

    public RequestModel(int propertyId, int senderId, int recipientId, int type, int isActive) {
        this.propertyId = propertyId;
        this.senderId=senderId;
        this.recipientId = recipientId;
        this.type = type;
        this.isActive=1;
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
        return "RequestModel{" +
                "requestId=" + requestId +
                ", senderId=" + senderId +
                ", propertyId=" + propertyId +
                ", recipientId=" + recipientId +
                ", isActive=" + isActive +
                ", type=" + type +
                '}';
    }
}
