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
import com.example.rentron50.classes.PropertyHelper;
import com.example.rentron50.classes.PropertyModel;
import com.example.rentron50.classes.UserHelper;

public class EditPropertyActivity extends AppCompatActivity {
    EditText streetName,streetNumber,postalCode,aptNumber,rent,rooms,bathrooms,floors,area,parking;
    Spinner hydro, heating,water,type;
    Button edit,back;
    int hydroTemp,heatingTemp,waterTemp;
    String typeTemp;
    PropertyHelper propertyHelper;
    UserHelper userHelper;
    PropertyModel property;
    PropertyModel editedProperty=new PropertyModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_property);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        propertyHelper=new PropertyHelper(this);
        userHelper=new UserHelper(this);
        property=propertyHelper.getPropertyModel(MyPropertyActivity.propertyId);
        init();
        setEventListeners();
        setSpinners();
        setEditTexts();
    }
    private void init(){
        rent=findViewById(R.id.rentEditEditProperty);
        rooms=findViewById(R.id.roomsEditEditProperty);
        bathrooms=findViewById(R.id.bathroomsEditEditProperty);
        floors=findViewById(R.id.floorsEditEditProperty);
        area=findViewById(R.id.areaEditEditProperty);
        parking=findViewById(R.id.parkingEditEditProperty);
        hydro=findViewById(R.id.hydroSpinnerEditProperty);
        heating=findViewById(R.id.heatingSpinnerEditProperty);
        water=findViewById(R.id.waterSpinnerEditProperty);
        type=findViewById(R.id.typeSpinnerEditProperty);
        edit=findViewById(R.id.editButtonEditProperty);
        back=findViewById(R.id.backButtonEditProperty);
    }
    private void setEventListeners(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PropertyMenuActivity.class));
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editedProperty=new PropertyModel();
                if ((rent.getEditableText().toString().isEmpty())) {
                    editedProperty.setRent(-1);
                } else {
                    editedProperty.setRent(Integer.parseInt(rent.getEditableText().toString()));
                }
                if ((floors.getEditableText().toString().isEmpty())) {
                    editedProperty.setFloors(-1);
                } else {
                    editedProperty.setFloors(Double.parseDouble(floors.getEditableText().toString()));
                }
                if ((bathrooms.getEditableText().toString().isEmpty())) {
                    editedProperty.setBathrooms(-1);
                } else {
                    editedProperty.setBathrooms(Double.parseDouble(bathrooms.getEditableText().toString()));
                }
                if ((parking.getEditableText().toString().isEmpty())) {
                    editedProperty.setParking(-1);
                } else {
                    editedProperty.setParking(Integer.parseInt(parking.getEditableText().toString()));
                }if ((rooms.getEditableText().toString().isEmpty())) {
                    editedProperty.setRooms(-1);
                } else {
                    editedProperty.setRooms(Double.parseDouble(rooms.getEditableText().toString()));
                }
                if ((area.getEditableText().toString().isEmpty())) {
                    editedProperty.setArea(-1);
                } else {
                    editedProperty.setArea(Integer.parseInt(area.getEditableText().toString()));
                }
                propertyHelper.editProperty(editedProperty,MyPropertyActivity.propertyId);
                startActivity(new Intent(getApplicationContext(),PropertyMenuActivity.class));
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
    private void setEditTexts(){
        rent.setText(String.valueOf(property.getRent()));
        rooms.setText(String.valueOf(property.getRooms()));
        bathrooms.setText(String.valueOf(property.getBathrooms()));
        floors.setText(String.valueOf(property.getFloors()));
        area.setText(String.valueOf(property.getArea()));
        parking.setText(String.valueOf(property.getParking()));
        hydro.setSelection(property.getHydro());
        water.setSelection(property.getWater());
        heating.setSelection(property.getHeating());

    }
}