package com.example.rentron35;

import java.util.ArrayList;

public class Landlord extends User{
    Address address;
    private ArrayList<Property> properties;
    public Landlord(String firstName,String lastName,String emailAddress,String password){
        super(firstName, lastName, emailAddress, password);
    }
    public Landlord(){
        super();
    }
    private void createProperty(){

    }
    public void setAddress(Address address){
        this.address=address;
    }
    public Address getAddress(){
        return address;
    }
    private void requestManager(Property property,PropertyManager propertyManager){
        // This method sends a request to the property manager as to a specific property
        // It will send a request which sends the details on the property and the client
    }
    private void assignManager(PropertyManager propertyManager){
        //After the manager accepts the request he is added to the property and an email is sent his account.
    }
    private void addOccupant(Client c,Property p){
        int x=properties.indexOf(p);
        Property a=properties.get(x);
        a.addOccupant(c);
    }
    private void evict(Client c,Property p){
        int x=properties.indexOf(p);
        int y=p.getIndexOf(c);
        properties.get(x).removeOccupant(c);
    }
    private void changeAvailability(Property p){
        p.changeAvailability();
    }
    public String toString(){
        return super.toString()+address.toString();
    }
}
