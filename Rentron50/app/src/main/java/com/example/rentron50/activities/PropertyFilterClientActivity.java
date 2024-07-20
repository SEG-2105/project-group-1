package com.example.rentron50.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rentron50.R;
import com.example.rentron50.classes.Client;
import com.example.rentron50.classes.PropertyModel;

import java.util.Locale;

public class PropertyFilterClientActivity extends AppCompatActivity {
    EditText streetName,streetNumber,postalCode,aptNumber,rent,rooms,bathrooms,floors,area,parking;
    Spinner hydro, heating,water,type;
    Button filter,back;
    int hydroTemp,heatingTemp,waterTemp;
    String typeTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_property_filter_client);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        setEventListeners();
        setSpinners();
    }
    private void init(){
        rent=findViewById(R.id.rentEditFilter);
        rooms=findViewById(R.id.roomsEditFilter);
        bathrooms=findViewById(R.id.bathroomsEditFilter);
        floors=findViewById(R.id.floorsEditFilter);
        area=findViewById(R.id.areaEditFilter);
        parking=findViewById(R.id.parkingEditFilter);
        hydro=findViewById(R.id.hydroSpinnerFilter);
        heating=findViewById(R.id.heatingSpinnerFilter);
        water=findViewById(R.id.waterSpinnerFilter);
        type=findViewById(R.id.typeSpinnerFilter);
        filter=findViewById(R.id.filterButtonFilter);
        back=findViewById(R.id.backButtonFilter);
    }
    private void setEventListeners(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ClientActivity.class));
            }
        });
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientActivity.propertyFilter=new PropertyModel();
                if ((rent.getEditableText().toString().isEmpty())) {
                    ClientActivity.propertyFilter.setRent(-1);
                } else {
                    ClientActivity.propertyFilter.setRent(Integer.parseInt(rent.getEditableText().toString()));
                }
                if ((floors.getEditableText().toString().isEmpty())) {
                    ClientActivity.propertyFilter.setFloors(-1);
                } else {
                    ClientActivity.propertyFilter.setFloors(Double.parseDouble(floors.getEditableText().toString()));
                }
                if ((bathrooms.getEditableText().toString().isEmpty())) {
                    ClientActivity.propertyFilter.setBathrooms(-1);
                } else {
                    ClientActivity.propertyFilter.setBathrooms(Double.parseDouble(bathrooms.getEditableText().toString()));
                }
                if ((parking.getEditableText().toString().isEmpty())) {
                    ClientActivity.propertyFilter.setParking(-1);
                } else {
                    ClientActivity.propertyFilter.setParking(Integer.parseInt(parking.getEditableText().toString()));
                }if ((rooms.getEditableText().toString().isEmpty())) {
                    ClientActivity.propertyFilter.setRooms(-1);
                } else {
                    ClientActivity.propertyFilter.setRooms(Double.parseDouble(rooms.getEditableText().toString()));
                }
                if ((area.getEditableText().toString().isEmpty())) {
                    ClientActivity.propertyFilter.setArea(-1);
                } else {
                    ClientActivity.propertyFilter.setArea(Integer.parseInt(area.getEditableText().toString()));
                }

                ClientActivity.propertyFilter.setType(typeTemp);
                ClientActivity.propertyFilter.setHeating(heatingTemp);
                ClientActivity.propertyFilter.setHeating(hydroTemp);
                ClientActivity.propertyFilter.setWater(waterTemp);

                startActivity(new Intent(getApplicationContext(),ClientActivity.class));
            }
        });
        hydro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hydroTemp=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        water.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                waterTemp=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        heating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                heatingTemp=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeTemp=(String)type.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void setSpinners(){
        ArrayAdapter<CharSequence> hydroAdapter=ArrayAdapter.createFromResource(
                this,R.array.hydro, android.R.layout.simple_spinner_item
        );
        ArrayAdapter<CharSequence> heatingAdapter=ArrayAdapter.createFromResource(
                this,R.array.heating, android.R.layout.simple_spinner_item
        );
        ArrayAdapter<CharSequence> waterAdapter=ArrayAdapter.createFromResource(
                this,R.array.water, android.R.layout.simple_spinner_item
        );
        ArrayAdapter<CharSequence> typeAdapter=ArrayAdapter.createFromResource(
                this,R.array.propertyType, android.R.layout.simple_spinner_item
        );
        hydro.setAdapter(hydroAdapter);
        water.setAdapter(waterAdapter);
        heating.setAdapter(heatingAdapter);
        type.setAdapter(typeAdapter);
    }
}