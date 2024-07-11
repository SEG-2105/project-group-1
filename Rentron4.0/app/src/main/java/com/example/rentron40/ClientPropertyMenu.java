package com.example.rentron40;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class ClientPropertyMenu extends AppCompatActivity {
    Button previous,request,active,inactive;
    TextView rent,rooms,bathrooms,floors,parking,area;
    PropertyHelper propertyHelper;
    RequestHelper requestHelper;
    UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client_property_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        propertyHelper=new PropertyHelper(this);
        userHelper=new UserHelper(this);
        requestHelper=new RequestHelper(this);
        init();
        setEventListeners();
        setTextViews();
    }
    private void setTextViews(){
        rent.setText(String.valueOf(SearchPropertyActivity.property.getRent()));
        rooms.setText(String.valueOf(SearchPropertyActivity.property.getRooms()));
        bathrooms.setText(String.valueOf(SearchPropertyActivity.property.getBathrooms()));
        floors.setText(String.valueOf(SearchPropertyActivity.property.getFloors()));
        parking.setText(String.valueOf(SearchPropertyActivity.property.getParking()));
        area.setText(String.valueOf(SearchPropertyActivity.property.getArea()));
    }

    private void init(){
        previous=findViewById(R.id.clientPropertyMenuPrevious);
        rent=findViewById(R.id.clientPropertyMenuRent);
        request=findViewById(R.id.clientPropertyMenuRequest);
        active=findViewById(R.id.clientRequestMenuActive);
        inactive=findViewById(R.id.clientRequestMenuInactive);
        rooms=findViewById(R.id.clientPropertyMenuRooms);
        bathrooms=findViewById(R.id.clientPropertyMenuBathrooms);
        floors=findViewById(R.id.clientPropertyMenuFloors);
        parking=findViewById(R.id.clientPropertyMenuParking);
        area=findViewById(R.id.clientPropertyMenuArea);
    }
    private void setEventListeners() {
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchPropertyActivity.class));
            }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int propertyId =SearchPropertyActivity.property.getId();
                int senderId=MainActivity.user.getId();
                int recipientId=SearchPropertyActivity.property.getLandlordId();
                int type=0;
                int isActive=1;
                RequestModel request=new RequestModel(propertyId,
                        senderId,recipientId,type,isActive);
                requestHelper.addRequest(request);
                SearchPropertyActivity.property=null;
                startActivity(new Intent(getApplicationContext(),SearchPropertyActivity.class));
            }});
    }


}