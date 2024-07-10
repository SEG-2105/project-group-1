package com.example.rentron40;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PropertyManagerActivity extends AppCompatActivity {
    TextView welcomeMessage;
    Button logoff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_property_manager);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        setEventListeners();
    }
    private void init(){
        welcomeMessage=findViewById(R.id.textViewWelcomeMessage);
        welcomeMessage.setText("Welcome oh Property Manager");
        logoff=findViewById(R.id.pmLogOff);
    }
    private void setEventListeners(){
        logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
    private void logout(){
        MainActivity.logout();
    }
}