package com.example.rentron50.activities;

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

import com.example.rentron50.R;
import com.example.rentron50.classes.PropertyHelper;
import com.example.rentron50.classes.PropertyModel;
import com.example.rentron50.classes.RequestHelper;
import com.example.rentron50.classes.RequestModel;
import com.example.rentron50.classes.User;
import com.example.rentron50.classes.UserHelper;
import com.example.rentron50.classes.UserModel;

public class PropertyRequestActivity extends AppCompatActivity {
    TextView address,rent,client,landlord,propertyManager,
            floors,rooms,type,bathrooms,area,hydro,heating,water;
    UserHelper userHelper;
    Button back,request;
    PropertyHelper propertyHelper;
    RequestHelper requestHelper;
    public static PropertyModel property;
    UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_property_request);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        userHelper=new UserHelper(this);
        requestHelper=new RequestHelper(this);
        propertyHelper=new PropertyHelper(this);
        user=userHelper.getUserModel(MainActivity.eMail);
        init();
        setTextViews();
        setEventListeners();
    }
    private void init() {
        address=findViewById(R.id.addressTextPropertyRequest);
        rent=findViewById(R.id.requestButtonPropertyRequest);
        client=findViewById(R.id.clientTextPropertyRequest);
        landlord=findViewById(R.id.landlordTextPropertyRequest);
        propertyManager=findViewById(R.id.pmTextPropertyRequest);
        floors=findViewById(R.id.floorsTextPropertyRequest);
        rooms=findViewById(R.id.roomsTextPropertyRequest);
        type=findViewById(R.id.typeTextPropertyRequest);
        back=findViewById(R.id.backButtonPropertyRequest);
        bathrooms=findViewById(R.id.bathroomsTextPropertyRequest);
        area=findViewById(R.id.areaTextPropertyRequest);
        request=findViewById(R.id.requestButtonPropertyRequest);
        hydro=findViewById(R.id.hydroTextPropertyRequest);
        water=findViewById(R.id.waterTextPropertyRequest);
        heating=findViewById(R.id.heatingTextPropertyRequest);
    }
    private void setEventListeners(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MyPropertyActivity.class));
            }
        });
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestModel request=new RequestModel(
                       property.getId(),user.getId(),property.getLandlordId(),0,1
                );
                requestHelper.addRequest(request);
                startActivity(new Intent(getApplicationContext(),ClientActivity.class));
            }
        });
    }

    private void setTextViews() {
        address.setText("Address :"+PropertyRequestActivity.property.getAddress().toString());
        rent.setText("Rent :"+PropertyRequestActivity.property.getRent());
        client.setText("ClientId :"+userHelper.getName(PropertyRequestActivity.property.getClientId()));
        landlord.setText("LandlordId :"+userHelper.getName(PropertyRequestActivity.property.getLandlordId()));
        propertyManager.setText("PropertyManagerId :"+userHelper.getName(PropertyRequestActivity.property.getPropertyManagerId()));
        floors.setText("Floors :"+PropertyRequestActivity.property.getFloors());
        rooms.setText("Rooms :"+PropertyRequestActivity.property.getRooms());
        type.setText("Type :"+PropertyRequestActivity.property.getType());
        bathrooms.setText("Bathrooms: "+PropertyRequestActivity.property.getBathrooms());
        area.setText("Area :"+PropertyRequestActivity.property.getArea());
        hydro.setText(getResources().getStringArray(R.array.hydro)[property.getHydro()]);
        heating.setText(getResources().getStringArray(R.array.heating)[property.getHeating()]);
        water.setText(getResources().getStringArray(R.array.water)[property.getWater()]);
    }
}