package com.example.rentron40;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

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
