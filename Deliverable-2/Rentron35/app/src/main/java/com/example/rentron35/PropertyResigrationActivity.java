package com.example.rentron35;

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

public class PropertyResigrationActivity extends AppCompatActivity {
    EditText streetName,streetNumber,postalCode,aptNumber,rent,rooms,bathrooms,floors,area,parking;
    Button register;
    Spinner hydro, heating,water,type;
    PropertyHelper propertyHelper;
    String temp;
    UserHelper userHelper;
    int temp1,temp2,temp3;
    String email=MainActivity.user.getEmailAddress();
    PropertyModel property=new PropertyModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_property_resigration_acitvity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        propertyHelper=new PropertyHelper(this);
        userHelper=new UserHelper(this);
        init();
        setEventListeners();
    }
    private void init(){
        streetName=findViewById(R.id.editPropertyStreetName);
        streetNumber=findViewById(R.id.editPropertyStreetNumber);
        postalCode=findViewById(R.id.editPropertyPostalCode);
        aptNumber=findViewById(R.id.editPropertyApartmentNumber);
        rent=findViewById(R.id.editPropertyRent);
        rooms=findViewById(R.id.editPropertyRooms);
        floors=findViewById(R.id.editPropertyFloors);
        area=findViewById(R.id.editPropertyArea);
        register=findViewById(R.id.buttonPropertyRegister);
        hydro=findViewById(R.id.spinnerPropertyHydro);
        heating=findViewById(R.id.spinnerPropertyHeating);
        water=findViewById(R.id.spinnerPropertyWater);
        type=findViewById(R.id.spinnerPropertyType);
        bathrooms=findViewById(R.id.editPropertyBathrooms);
        parking=findViewById(R.id.editPropertyParking);
        ArrayAdapter<CharSequence> hydroAdapter=ArrayAdapter.createFromResource(
                this,R.array.hydro,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> heatingAdapter=ArrayAdapter.createFromResource(
                this,R.array.heating,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> waterAdapter=ArrayAdapter.createFromResource(
                this,R.array.water,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> typeAdapter=ArrayAdapter.createFromResource(
                this,R.array.property_type, android.R.layout.simple_spinner_item);
        hydroAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        heatingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hydro.setAdapter(hydroAdapter);
        heating.setAdapter(heatingAdapter);
        water.setAdapter(waterAdapter);
        type.setAdapter(typeAdapter);
    }

    private void setEventListeners(){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                property.setAddress((new Address(Integer.parseInt(streetNumber.getText().toString()),
                        streetNumber.getText().toString(),
                        new PostalCode(postalCode.getText().toString()),
                        aptNumber.getText().toString())));
                property.setRent(Integer.parseInt(rent.getText().toString()));
                property.setRooms(Double.parseDouble(rooms.getText().toString()));
                property.setArea(Integer.parseInt(area.getText().toString()));
                property.setBathrooms(Double.parseDouble(bathrooms.getText().toString()));
                property.setFloors(Double.parseDouble(floors.getText().toString()));
                property.setParking(Integer.parseInt(parking.getText().toString()));
                property.setLandlordId((userHelper.getId(email)));
                propertyHelper.addProperty(property);
                property.setPropertyManagerId(userHelper.getPms().get(0).getId());
                startActivity(new Intent(getApplicationContext(),LandlordActivity.class));
            }
        });
        heating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                property.setHeating(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                property.setType((String)parent.getItemAtPosition(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        hydro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                property.setHyrdo(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        water.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                property.setWater(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }
}