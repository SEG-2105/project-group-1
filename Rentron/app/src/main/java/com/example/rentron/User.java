package com.example.rentron;

public class User {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private int birthYear;
    private String password;

    public User(String firstName,String lastName,int birthYear,String emailAddress,String password){
        this.firstName=firstName;
        this.lastName=lastName;
        this.birthYear=birthYear;
        this.emailAddress=emailAddress;
        this.password=password;
    }
    public User(String firstName,String lastName,String emailAddress, String password){
        this.firstName=firstName;
        this.lastName=lastName;
        this.emailAddress=emailAddress;
        this.password=password;
    }
    public String toString(){
        return ""+firstName+" "+lastName+" "+emailAddress+" "+birthYear;
    }
}