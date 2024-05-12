package com.example.rentron;

public class Property {
    private Address address;
    private String type;
    private double rooms;
    private double bathrooms;
    private double floors;
    private int area;
    private boolean laundry;
    private int parking;
    private double rent;
    private boolean hydro;
    private boolean heating;
    private boolean water;
    private boolean occupied=false;
    public Property(Address address,String type,
                    double rooms, double bathrooms, double floors,double rent,int parking,
                    int area,boolean laundry,boolean hydro,boolean heating,boolean water){
        setUtilities(hydro,heating,water);
        setMain(address,type,rooms,bathrooms,floors,area,rent);
        setAmenities(parking,laundry);
    }
    private void setUtilities(boolean hydro,boolean heating,boolean water){
        this.hydro=hydro;
        this.heating=heating;
        this.water=water;
    }
    private void setAmenities(int parking,boolean laundry){
        this.laundry=laundry;
        this.parking=parking;
    }
    private void setMain(Address address,String type,double rooms,double bathrooms,double floors,int area,double rent){
        this.address=address;
        this.type=type;
        this.rooms=rooms;
        this.bathrooms=bathrooms;
        this.floors=floors;
        this.area=area;
        this.rent=rent;
    }

}