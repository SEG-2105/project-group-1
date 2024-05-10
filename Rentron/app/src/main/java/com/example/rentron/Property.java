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
    public Property(Address address,String type,
                    double rooms, double bathrooms, double floors,double rent,int parking,
                    int area,boolean laundry,boolean hydro,boolean heating,boolean water){
        setUtilties(hydro,heating,water);
        setMain(address,type,rooms,bathrooms,floors,area,rent);
        setAmenities(parking,laundry);
    }
    public void setUtilties(boolean hydro,boolean heating,boolean water){
        this.hydro=hydro;
        this.heating=heating;
        this.water=water;
    }
    public void setAmenities(int parking,boolean laundry){
        this.laundry=laundry;
        this.parking=parking;
    }
    public void setMain(Address address,String type,double rooms,double bathrooms,double floors,int area,double rent){
        this.address=address;
        this.type=type;
        this.rooms=rooms;
        this.bathrooms=bathrooms;
        this.floors=floors;
        this.area=area;
        this.rent=rent;
    }

}