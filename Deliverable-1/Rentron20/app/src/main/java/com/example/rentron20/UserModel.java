package com.example.rentron20;

import androidx.annotation.NonNull;

public class UserModel {

    private int id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String type;
    public static final int UNASSIGNED_ID=-1;
    public static final int UNNASSIGNED_BIRTHYEAR=-1;
    public static final Address UNASSIGNED_ADDRESS=new Address();


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserModel(int id, String type, String firstName, String lastName, String emailAddress) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.type=type;
    }
    public UserModel(String type,String firstName, String lastName, String emailAddress) {
        this.id = UNASSIGNED_ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.type=type;
    }
    //This constructor is the user model corresponding to a Landlord

    @NonNull
    @Override
    public String toString() {
        return " "+type+": "+firstName+" , "+lastName+" , "+emailAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
