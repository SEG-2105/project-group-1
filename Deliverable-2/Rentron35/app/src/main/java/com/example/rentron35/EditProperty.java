package com.example.rentron35;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditProperty extends AppCompatActivity {
    EditText rent,floors,area,bathrooms,rooms,parking;
    Button edit;

    PropertyHelper propertyHelper;
    UserHelper userHelper;
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
        userHelper=new UserHelper(this);
        propertyHelper=new PropertyHelper(this);
        init();
        setEventListeners();
    }
    private void init(){
        rent=findViewById(R.id.rentEditProperty);
        floors=findViewById(R.id.floorsEditProperty);
        edit=findViewById(R.id.EditProperty);
        area=findViewById(R.id.areaEditProperty);
        rooms=findViewById(R.id.roomsEditProperty);
        bathrooms=findViewById(R.id.bathroomEditProperty);
        parking=findViewById(R.id.parkingEditProperty);

    }
    private void setEventListeners(){
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                propertyHelper.updateProperty(Integer.parseInt(rent.getEditableText().toString()),
                        Double.parseDouble(rooms.getEditableText().toString()),
                        Double.parseDouble(bathrooms.getEditableText().toString()),
                        Double.parseDouble(floors.getEditableText().toString()),
                        Integer.parseInt(area.getEditableText().toString()),
                        Integer.parseInt(parking.getEditableText().toString()),
                        LandlordActivity.property.getId(),LandlordActivity.property.isOccupied());
                LandlordActivity.property=propertyHelper.getPropertyModel(LandlordActivity.property.getId());
                startActivity(new Intent(getApplicationContext(),PropertyMenu.class));
            }
        });
    }

}