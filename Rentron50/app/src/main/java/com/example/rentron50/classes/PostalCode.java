package com.example.rentron50.classes;

public class PostalCode {
    private String postalCode;
    public PostalCode(){
        this.postalCode="";
    }
    public PostalCode(String postalCode){
        setPostalCode(postalCode);
    }
    private void setPostalCode(String postalCode){
        if(postalCode.length()==6){
            this.postalCode=postalCode;
        }
    }
    public String toString(){
        return postalCode;
    }
}
