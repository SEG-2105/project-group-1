package com.example.rentron50.activities;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rentron50.R;
import com.example.rentron50.classes.PropertyHelper;
import com.example.rentron50.classes.PropertyModel;
import com.example.rentron50.classes.UserHelper;
import com.example.rentron50.classes.UserModel;

public class PropertyMenuActivity extends AppCompatActivity {
    TextView address,rent,client,landlord,propertyManager
            ,floors,rooms,type,bathrooms,area,hydro,water,heating;
    UserHelper userHelper;
    Button back, getPm,getCl,edit,evict,tickets;
    PropertyHelper propertyHelper;
    PropertyModel property;

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
        property=propertyHelper.getPropertyModel(MyPropertyActivity.propertyId);
        init();
        setTextViews();
        setEventListeners();
        setInvisible();
    }
    protected void onResume(){
        super.onResume();
        setTextViews();
    }


    private void init() {
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
        hydro=findViewById(R.id.propertyMenuHydro);
        water=findViewById(R.id.propertyMenuWater);
        heating=findViewById(R.id.propertyMenuHeating);
    }
    private void setEventListeners(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MyPropertyActivity.class));
            }
        });
        getCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestMenu.mode="ClientFinder";
                startActivity(new Intent(getApplicationContext(),RequestMenu.class));
                setTextViews();
            }
        });
        getPm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestMenu.mode="PropertyManagerFinder";
                startActivity(new Intent(getApplicationContext(),PropertyManagerFinderActivity.class));
                setTextViews();
            }
        });
        evict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(propertyHelper.evictClient(property.getId())){
                    setTextViews();
                }else{
                    Toast.makeText(getApplicationContext(),"Client Could Not Be Evicted For Some Reason",Toast.LENGTH_LONG).show();
                }
                property=propertyHelper.getPropertyModel(MyPropertyActivity.propertyId);
                setTextViews();
            }
        });
        tickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPropertyActivity.propertyId=property.getId();
                startActivity(new Intent(getApplicationContext(),TicketMenuActivity.class));
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EditPropertyActivity.class));
            }
        });
    }

    private void setTextViews() {
        address.setText("Address :"+property.getAddress().toString());
        rent.setText("Rent :"+property.getRent());
        client.setText("ClientId :"+userHelper.getName(property.getClientId()));
        landlord.setText("LandlordId :"+userHelper.getName(property.getLandlordId()));
        propertyManager.setText("PropertyManagerId :"+userHelper.getName(property.getPropertyManagerId()));
        floors.setText("Floors :"+property.getFloors());
        rooms.setText("Rooms :"+property.getRooms());
        type.setText("Type :"+property.getType());
        bathrooms.setText("Bathrooms: "+property.getBathrooms());
        area.setText("Area :"+property.getArea());
        hydro.setText(getResources().getStringArray(R.array.hydro)[property.getHydro()]);
        heating.setText(getResources().getStringArray(R.array.heating)[property.getHeating()]);
        water.setText(getResources().getStringArray(R.array.water)[property.getWater()]);
    }
    private void setInvisible(){
        UserModel user=userHelper.getUserModel(MainActivity.eMail);
        switch(user.getType()){
            case "Client":
                evict.setVisibility(View.INVISIBLE);
                getCl.setVisibility(View.INVISIBLE);
                getPm.setVisibility(View.INVISIBLE);
                edit.setVisibility(View.INVISIBLE);
                break;
            case "Landlord":
                evict.setVisibility(View.VISIBLE);
                getCl.setVisibility(View.VISIBLE);
                getPm.setVisibility(View.VISIBLE);
                edit.setVisibility(View.VISIBLE);
                break;
            case "PropertyManager":
                evict.setVisibility(View.VISIBLE);
                getCl.setVisibility(View.VISIBLE);
                getPm.setVisibility(View.INVISIBLE);
                edit.setVisibility(View.INVISIBLE);
                break;
        }
    }
}