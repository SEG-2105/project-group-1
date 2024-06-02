package com.example.rentron20;

import androidx.annotation.NonNull;

public class User {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private int birthYear;
    private String password;
    public User(String firstName,String lastName,int birthYear,String emailAddress,String password){
        setName(firstName,lastName);
        setBirthYear(birthYear);
        setCredentials(emailAddress,password);
    }
    public User(String firstName,String lastName,String emailAddress, String password){
        setName(firstName, lastName);
        setCredentials(emailAddress, password);
    }
    public User(String email,String password){
        this.emailAddress=email;
        this.password=password;
    }
    public User(){}
    private void setName(String firstName,String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    private void setCredentials(String emailAddress,String password){
        this.emailAddress=emailAddress;
        this.password=password;
    }
    public boolean setBirthYear(int birthYear){
        if(birthYear>1914 && birthYear<2024){
            this.birthYear=birthYear;
            return true;
        }
        else{
            this.birthYear=0;
            return false;
        }
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public boolean sendEmail(String message,String email){
        return true;
    }
    public String getEmailAddress(){
        return emailAddress;
    }
    public String getPassword(){
        return password;
    }
    @NonNull
    public String toString(){
        return firstName+" "+lastName+" "+emailAddress+" "+birthYear;
    }

    public int getBirthYear() {
        return birthYear;
    }
}