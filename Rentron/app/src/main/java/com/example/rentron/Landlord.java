package com.example.rentron;

import java.util.ArrayList;
import java.util.Properties;

public class Landlord extends User{
    private ArrayList<Property> properties;
    public Landlord(String firstName,String lastName,int birthYear,String emailAddress,String password){
        super(firstName, lastName, birthYear, emailAddress, password);
    }
    private void createProperty(){

    }
    private void requestManager(Property property,PropertyManager propertyManager){
        // This method sends a request to the property manager as to a specific property
        // It will send a request which sends the cred
    }
    private void assignManager(PropertyManager propertyManager){

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
        p.changeAvalibility();
    }
}
