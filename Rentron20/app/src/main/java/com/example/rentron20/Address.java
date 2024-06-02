package com.example.rentron20;

public class Address {
    private int streetNumber;
    private String streetName;
    private PostalCode postalCode;
    private String aptNumber;
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
    public String toString(){
        return aptNumber+"-"+streetNumber+" "+streetName+" "+postalCode.toString();
    }
}
