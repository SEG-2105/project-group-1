package com.example.rentron50.classes;

import com.example.rentron50.classes.Address;

public class PropertyModel {
    private int id;
    private int rent;
    private String type;
    private String address;
    private double rooms;
    private double bathrooms;
    private double floors;
    private int area;
    private int parking;

    public int getHydro() {
        return hydro;
    }

    public void setHydro(int hydro) {
        this.hydro = hydro;
    }

    public int getHeating() {
        return heating;
    }

    public int getWater() {
        return water;
    }

    private int hydro;
    private int heating;
    private int water;
    private int occupied;
    private int landlordId;
    private int clientId;
    private int propertyManagerId;
    public static int UNASSIGNED_ID=-1;
    public static int UNASSIGNED_CLIENTID=-1;
    public static int UNASSIGNED_LANDLORDID=-1;
    public static int UNASSIGNED_PROPERTYMANAGER=-1;
    public static int UNASSIGNED_OCCUPANCY=0; // 0 for false, 1 for true;

    public PropertyModel() {
        this.id=UNASSIGNED_ID;
        this.propertyManagerId=UNASSIGNED_PROPERTYMANAGER;
        this.clientId=UNASSIGNED_CLIENTID;
        this.occupied=UNASSIGNED_OCCUPANCY;
        type="";
        address="";
    }

    public PropertyModel(int id, int rent,
                         String type, String address,
                         double rooms, double bathrooms, double floors,
                         int area, int parking,
                         int hyrdo, int heating, int water, int occupied,
                         int landlordId, int clientId, int propertyManagerId) {
        this.id = id;
        this.rent = rent;
        this.type = type;
        this.address = address;
        this.rooms = rooms;
        this.bathrooms = bathrooms;
        this.floors = floors;
        this.area = area;
        this.parking = parking;
        this.hydro = hyrdo;
        this.heating = heating;
        this.water = water;
        this.landlordId = landlordId;
        this.clientId = clientId;
        this.occupied=occupied;
        this.propertyManagerId = propertyManagerId;
    }
    public PropertyModel(int rent,
                         String type, String address,
                         double rooms, double bathrooms, double floors,
                         int area, int parking,
                         int hydro, int heating, int water,
                         int landlordId) {
        this.id = UNASSIGNED_ID;
        this.rent = rent;
        this.type = type;
        this.address = address;
        this.rooms = rooms;
        this.bathrooms = bathrooms;
        this.floors = floors;
        this.area = area;
        this.parking = parking;
        this.hydro = hydro;
        this.heating = heating;
        this.water = water;
        this.landlordId = landlordId;
        this.clientId = UNASSIGNED_CLIENTID;
        this.propertyManagerId = UNASSIGNED_PROPERTYMANAGER;
        this.occupied=UNASSIGNED_OCCUPANCY;
    }
    public PropertyModel(int rent,
                         String type,
                         double rooms, double bathrooms, double floors,
                         int area, int parking,
                         int hydro, int heating, int water
                         ) {
        this.id = UNASSIGNED_ID;
        this.rent = rent;
        this.type = type;
        this.address = address;
        this.rooms = rooms;
        this.bathrooms = bathrooms;
        this.floors = floors;
        this.area = area;
        this.parking = parking;
        this.hydro = hydro;
        this.heating = heating;
        this.water = water;
        this.landlordId = UNASSIGNED_LANDLORDID;
        this.clientId = UNASSIGNED_CLIENTID;
        this.propertyManagerId = UNASSIGNED_PROPERTYMANAGER;
        this.occupied=UNASSIGNED_OCCUPANCY;
    }

    @Override
    public String toString() {
        return "PropertyModel{" +
                "id=" + id +
                ", rent=" + rent +
                ", type='" + type + '\'' +
                ", address='" + address + '\'' +
                ", rooms=" + rooms +
                ", bathrooms=" + bathrooms +
                ", floors=" + floors +
                ", area=" + area +
                ", parking=" + parking +
                ", hyrdo=" + hydro +
                ", heating=" + heating +
                ", water=" + water +
                ", occupied=" + occupied +
                ", landlordId=" + landlordId +
                ", clientId=" + clientId +
                ", propertyManagerId=" + propertyManagerId +
                '}';
    }

    public PropertyModel(int rent,
                         String address, String type,
                         double rooms, double bathrooms, double floors,
                         int area, int parking,
                         int landlordId) {
        this.id = UNASSIGNED_ID;
        this.rent = rent;
        this.type = type;
        this.address = address;
        this.rooms = rooms;
        this.bathrooms = bathrooms;
        this.floors = floors;
        this.area = area;
        this.parking = parking;
        this.landlordId = landlordId;
        this.clientId = UNASSIGNED_CLIENTID;
        this.propertyManagerId = UNASSIGNED_PROPERTYMANAGER;
        this.occupied=UNASSIGNED_OCCUPANCY;
    }

    public int isOccupied() {
        return occupied;
    }

    public void setOccupied(int occupied) {
        this.occupied = occupied;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address.toString();
    }

    public double getRooms() {
        return rooms;
    }

    public void setRooms(double rooms) {
        this.rooms = rooms;
    }

    public double getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(double bathrooms) {
        this.bathrooms = bathrooms;
    }

    public double getFloors() {
        return floors;
    }

    public void setFloors(double floors) {
        this.floors = floors;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getParking() {
        return parking;
    }

    public void setParking(int parking) {
        this.parking = parking;
    }

    public int isHydro() {
        return hydro;
    }

    public void setHyrdo(int hyrdo) {
        this.hydro = hyrdo;
    }

    public int isHeating() {
        return heating;
    }

    public void setHeating(int heating) {
        this.heating = heating;
    }

    public int isWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getLandlordId() {
        return landlordId;
    }

    public void setLandlordId(int landlordId) {
        this.landlordId = landlordId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getPropertyManagerId() {
        return propertyManagerId;
    }

    public void setPropertyManagerId(int propertyManagerId) {
        this.propertyManagerId = propertyManagerId;
    }
}
