package com.example.rentron50.classes;

import androidx.annotation.NonNull;

public class Address {
    private int streetNumber;
    private String streetName;
    private PostalCode postalCode;
    private String aptNumber;
    private String city;
    private String country;
    public Address(){
        this.streetName="";
        this.streetNumber=0;
        this.aptNumber="";
        this.postalCode=new PostalCode();
        this.country="";
        this.city="";
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Address(String address_to_make){
        if(!address_to_make.contains("-")){
            String[] str=address_to_make.split(",");
            this.streetNumber=Integer.parseInt(str[0]);
            this.streetName=str[1];
            this.postalCode=new PostalCode(str[2]);
            this.city=str[3];
            this.country=str[4];
        }else{
            String[] str1=address_to_make.split("-");
            this.aptNumber=str1[0];
            String[] str=str1[1].split(",");
            this.streetNumber=Integer.parseInt(str[0]);
            this.streetName=str[1];
            this.postalCode=new PostalCode(str[2]);
            this.city=str[3];
            this.country=str[4];
        }

    }
    public Address(int streetNumber, String streetName, PostalCode postalCode,String city,String country){
        this.streetName=streetName;
        this.postalCode=postalCode;
        this.streetNumber=streetNumber;
        this.country=country;
        this.city=city;
    }
    public Address(int streetNumber, String streetName, PostalCode postalCode, String aptNumber,String city,String country){
        this.streetName=streetName;
        this.postalCode=postalCode;
        this.streetNumber=streetNumber;
        this.aptNumber=aptNumber;
        this.country=country;
        this.city=city;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setPostalCode(PostalCode postalCode) {
        this.postalCode = postalCode;
    }

    public void setAptNumber(String aptNumber) {
        this.aptNumber = aptNumber;
    }


    public PostalCode getPostalCode() {
        return postalCode;
    }

    public String getAptNumber() {
        return aptNumber;
    }

    @NonNull
    public String toString(){
        if(aptNumber!=null){
            return aptNumber+"-"+streetNumber+","+streetName+","+postalCode.toString()+","+city+","+country;
        }
        return streetNumber+","+streetName+","+postalCode.toString()+","+city+","+country;
    }
}
