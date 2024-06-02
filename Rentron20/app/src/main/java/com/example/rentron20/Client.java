package com.example.rentron20;

public class Client extends User {
    private int birthYear;

    public Client(String firstName, String lastName, int birthYear, String emailAddress, String password) {
        super(firstName, lastName, emailAddress, password);
        setBirthYear(birthYear);
    }

    public Client() {
        super();
    }

    public boolean setBirthYear(int birthYear) {
        if (birthYear > 1914 && birthYear < 2024) {
            this.birthYear = birthYear;
            return true;
        } else {
            this.birthYear = 0;
            return false;
        }
    }

    public int getBirthYear() {
        return birthYear;
    }

    public static boolean setBirthYear(int birthYear, Client c) {
        return c.setBirthYear(birthYear);
    }
}
