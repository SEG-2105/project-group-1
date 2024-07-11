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

public class PropertyMenu extends AppCompatActivity {
    TextView address,rent,client,landlord,propertyManager,floors,rooms,type,bathrooms,area;
    UserHelper userHelper;
    Button back, getPm,getCl,edit,evict,tickets;
    PropertyHelper propertyHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_property_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        userHelper=new UserHelper(this);
        propertyHelper=new PropertyHelper(this);
        init();
        setTextViews();
        setEventListeners();
    }
    private void init(){
        address=findViewById(R.id.propertyMenuAddress);
        rent=findViewById(R.id.propertyMenuRent);
        client=findViewById(R.id.propertyMenuClientId);
        landlord=findViewById(R.id.propertyMenuLandlordId);
        propertyManager=findViewById(R.id.propertyMenuPmId);
        floors=findViewById(R.id.propertyMenuFloors);
        rooms=findViewById(R.id.propertyMenuRooms);
        type=findViewById(R.id.propertyMenuType);
        back=findViewById(R.id.propertyMenuButtonBack);
        getPm=findViewById(R.id.propertyMenuButtonGetPm);
        getCl=findViewById(R.id.propertyMenuGetClient);
        edit=findViewById(R.id.editButtonPropertyMenu);
        bathrooms=findViewById(R.id.propertyMenuBathrooms);
        area=findViewById(R.id.propertyMenuArea);
        evict=findViewById(R.id.evictButtonPropertyMenu);
        tickets=findViewById(R.id.ticketButtonPropertyMenu);
    }
    private void setTextViews(){
        address.setText("Address :"+LandlordActivity.property.getAddress().toString());
        rent.setText("Rent :"+LandlordActivity.property.getRent());
        client.setText("ClientId :"+userHelper.getName(LandlordActivity.property.getClientId()));
        landlord.setText("LandlordId :"+userHelper.getName(LandlordActivity.property.getLandlordId()));
        propertyManager.setText("PropertyManagerId :"+userHelper.getName(LandlordActivity.property.getPropertyManagerId()));
        floors.setText("Floors :"+LandlordActivity.property.getFloors());
        rooms.setText("Rooms :"+LandlordActivity.property.getRooms());
        type.setText("Type :"+LandlordActivity.property.getType());
        bathrooms.setText("Bathrooms: "+LandlordActivity.property.getBathrooms());
        area.setText("Area :"+LandlordActivity.property.getArea());
    }
    private void setEventListeners(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LandlordActivity.property=null;
                startActivity(new Intent(getApplicationContext(),LandlordActivity.class));
            }
        });

        getPm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PropertyManagerSelector.class));
            }
        });
        getCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ClientFinder.class));
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),EditProperty.class));
            }
        });
        evict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                propertyHelper.updateClient(LandlordActivity.property.getId(),-1);
                LandlordActivity.property=propertyHelper.getPropertyModel(LandlordActivity.property.getId());
                setTextViews();
            }
        });
        tickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),TicketMenuActivity.class));
            }
        });
    }
}