package com.example.rentron35;

import androidx.annotation.NonNull;

import java.util.ArrayList;

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
    private boolean availability=false;
    private ArrayList<Ticket> t;
    private ArrayList<Client> c;
    private Landlord LLord;
    private PropertyManager pm;
    public Property(Address address,String type,
                    double rooms, double bathrooms, double floors,double rent,int parking,
                    int area,boolean laundry,boolean hydro,boolean heating,boolean water){
        setUtilities(hydro,heating,water);
        setMain(address,type,rooms,bathrooms,floors,area,rent);
        setAmenities(parking,laundry);
    }
    private void setLandlord(Landlord landlord){
        this.LLord=new Landlord();
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
    protected void changeAvailability(){
        if(!availability & pm!=null){
            this.availability=true;
        } else{
            this.availability=false;
            System.out.println("No property manager was found thus you cannot make it available");
        }
    }
    protected int getIndexOf(Client c){
        return this.c.indexOf(c);
    }
    protected void addOccupant(Client client){
        this.c.add(client);
    }
    public void setType(String type){
        boolean test;
        for(String t:new String[]{"Basement","Studio","Apartment","Townhouse","House"}){
            if(type.equals(t)){
                this.type=type;
            }
        }
    }
    protected void removeOccupant(Client client){
        this.c.remove(client);
    }
    protected void assignManager(PropertyManager pm){
        this.pm=pm;
    }
    @NonNull
    public String toString(){
        return "Address"+address+"\nNumber of Rooms:"+rooms+"\n Number of Bathrooms"+bathrooms;
    }
}