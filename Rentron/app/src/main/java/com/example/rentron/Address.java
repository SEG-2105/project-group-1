package com.example.rentron;

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
}
