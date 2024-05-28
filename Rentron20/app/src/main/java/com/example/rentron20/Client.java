package com.example.rentron20;

public class Client extends User{
    public Client(String firstName,String lastName,int birthYear,String emailAddress,String password){
        super(firstName,lastName,birthYear,emailAddress,password);
    }
    public Client(){
        super();
    }
}
