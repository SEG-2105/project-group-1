package com.example.rentron20;

public class PostalCode {
    private String postalCode;
    public PostalCode(String postalCode){
        setPostalCode(postalCode);
    }
    private void setPostalCode(String postalCode){
        if(postalCode.length()==6){
            this.postalCode=postalCode;
        }
    }
}
