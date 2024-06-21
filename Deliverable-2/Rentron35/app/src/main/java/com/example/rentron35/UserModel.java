package com.example.rentron35;

public class UserModel {
    private int id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
    private String type;
    private String address;
    private int birthYear;
    public static final int UNASSIGNED_ID = -1 ;

    public UserModel(){
        this.id=UNASSIGNED_ID;
    }
    public UserModel(int id, String type,String firstName, String lastName, String emailAddress, String password, String address, int birthYear) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.type = type;
        this.address = address;
        this.birthYear = birthYear;
    }
    public UserModel(String type,String firstName,String lastName,String emailAddress,String password){
        this.id=UNASSIGNED_ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.type=type;
    }
    public UserModel(String type,String firstName,String lastName,String emailAddress,String password,int birthYear){
        this.id=UNASSIGNED_ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.birthYear=birthYear;
        this.type=type;
    }
    public UserModel(String type,String firstName,String lastName,String emailAddress,String password,String address){
        this.id=UNASSIGNED_ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.type=type;
        this.address=address;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
    public String getName(){
        return firstName+" "+lastName;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                ", type='" + type + '\'' +
                ", address='" + address + '\'' +
                ", birthYear=" + birthYear +
                '}';
    }
}
