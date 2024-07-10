package com.example.rentron40;

import androidx.annotation.NonNull;

public class Address {
    private int streetNumber;
    private String streetName;
    private PostalCode postalCode;
    private String aptNumber;
    public Address(){
        this.streetName="";
        this.streetNumber=0;
        this.aptNumber="";
        this.postalCode=new PostalCode();
    }
    public Address(String address_to_make){
        String[] str1=address_to_make.split("-");
        this.aptNumber=str1[0];
        String[] str=str1[1].split(",");
        this.streetNumber=Integer.parseInt(str[0]);
        this.streetName=str[1];
        this.postalCode=new PostalCode(str[2]);
    }
    public Address(int streetNumber,String streetName,PostalCode postalCode){
        this.streetName=streetName;
        this.postalCode=postalCode;
        this.streetNumber=streetNumber;
    }
    public Address(int streetNumber,String streetName,PostalCode postalCode,String aptNumber){
        this.streetName=streetName;
        this.postalCode=postalCode;
        this.streetNumber=streetNumber;
        this.aptNumber=aptNumber;
    }


    @NonNull
    public String toString(){
        if(aptNumber!=null){
            return aptNumber+"-"+streetNumber+","+streetName+","+postalCode.toString();
        }
        return streetNumber+","+streetName+","+postalCode.toString();
    }
}
