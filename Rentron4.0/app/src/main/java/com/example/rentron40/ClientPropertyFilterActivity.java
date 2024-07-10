package com.example.rentron40;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ClientPropertyFilterActivity extends AppCompatActivity {
    EditText rent,rooms, bathrooms, floors, area, parking;
    Spinner type;
    Button previous, submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client_property_filter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        setEventListeners();
    }
    private void init(){
        rent=findViewById(R.id.filtersRent);
        rooms=findViewById(R.id.filtersRooms);
        bathrooms=findViewById(R.id.filtersBathrooms);
        floors=findViewById(R.id.filtersFloors);
        area=findViewById(R.id.filtersArea);
        parking=findViewById(R.id.filtersParking);
        type=findViewById(R.id.filtersType);
        previous=findViewById(R.id.filtersPrevious);
        submit=findViewById(R.id.filtersSet);
    }
    private void setEventListeners(){
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SearchPropertyActivity.class));
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchPropertyActivity.propertyFilter.setRent(Integer.parseInt(rent.getEditableText().toString()));
                startActivity(new Intent(getApplicationContext(),SearchPropertyActivity.class));
            }
        });
    }
}