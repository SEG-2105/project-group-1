package com.example.rentron50.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rentron50.R;
import com.example.rentron50.classes.Address;
import com.example.rentron50.classes.PostalCode;
import com.example.rentron50.classes.PropertyHelper;
import com.example.rentron50.classes.PropertyModel;
import com.example.rentron50.classes.UserHelper;
import com.example.rentron50.classes.UserModel;

public class RegisterPropertyActivity extends AppCompatActivity {
    EditText streetName,streetNumber,postalCode,aptNumber,rent,rooms,bathrooms,floors,area,parking,
    city,country;
    Spinner hydro, heating,water,type;
    Button register,back;
    int hydroTemp,heatingTemp,waterTemp;
    String typeTemp;
    UserHelper userHelper;
    PropertyHelper propertyHelper;
    UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_property);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        propertyHelper=new PropertyHelper(this);
        userHelper=new UserHelper(this);
        user=userHelper.getUserModel(MainActivity.eMail);
        init();
        setEventListeners();
        setSpinners();
    }
    private void init(){
        streetName=findViewById(R.id.streetNameEditRegisterProperty);
        streetNumber=findViewById(R.id.streetNumberEditRegisterProperty);
        postalCode=findViewById(R.id.postalCodeEditRegisterProperty);
        aptNumber=findViewById(R.id.aptNumberEditRegisterProperty);
        rent=findViewById(R.id.rentEditRegisterProperty);
        rooms=findViewById(R.id.roomsEditRegisterProperty);
        bathrooms=findViewById(R.id.bathroomsEditRegisterProperty);
        floors=findViewById(R.id.floorsEditRegisterProperty);
        area=findViewById(R.id.areaEditRegisterProperty);
        parking=findViewById(R.id.parkingEditRegisterProperty);
        hydro=findViewById(R.id.hydroSpinnerRegisterProperty);
        heating=findViewById(R.id.heatingSpinnerRegisterProperty);
        water=findViewById(R.id.waterSpinnerRegisterProperty);
        type=findViewById(R.id.typeSpinnerRegisterProperty);
        register=findViewById(R.id.registerButtonRegisterProperty);
        back=findViewById(R.id.backButtonRegisterProperty);
        city=findViewById(R.id.cityEditRegisterProperty);
        country=findViewById(R.id.countryEditRegisterProperty);
    }
    private void setEventListeners(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LandlordActivity.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    Address address=new Address(
                            Integer.parseInt(streetNumber.getEditableText().toString()),
                            streetName.getEditableText().toString(),
                            new PostalCode(postalCode.getEditableText().toString()),
                            aptNumber.getEditableText().toString(),
                            city.getEditableText().toString(),
                            country.getEditableText().toString()
                    );
                    PropertyModel property=new PropertyModel(
                            Integer.parseInt(rent.getEditableText().toString()),
                            typeTemp,
                            address.toString(),
                            Double.parseDouble(rooms.getEditableText().toString()),
                            Double.parseDouble(bathrooms.getEditableText().toString()),
                            Double.parseDouble(floors.getEditableText().toString()),
                            Integer.parseInt(area.getEditableText().toString()),
                            Integer.parseInt(parking.getEditableText().toString()),
                            hydroTemp
                            ,heatingTemp,
                            waterTemp,
                            user.getId()
                    );
                    propertyHelper.addProperty(property);
                    startActivity(new Intent(getApplicationContext(),LandlordActivity.class));
                }
                else{
                    Toast.makeText(getApplicationContext(),"Make sur all fields are filled correctly",Toast.LENGTH_LONG).show();
                }
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
    private boolean validate(){
        return !(streetName.getEditableText().toString().isEmpty()|| streetNumber.getEditableText().toString().isEmpty() ||
                postalCode.getEditableText().toString().isEmpty() || rent.getEditableText().toString().isEmpty() ||
                city.getEditableText().toString().isEmpty() || country.getEditableText().toString().isEmpty() ||
                rooms.getEditableText().toString().isEmpty() || bathrooms.getEditableText().toString().isEmpty() ||
                floors.getEditableText().toString().isEmpty() || area.getEditableText().toString().isEmpty() ||
                parking.getEditableText().toString().isEmpty());
    }

}