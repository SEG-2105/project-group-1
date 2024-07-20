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

public class RequestActivity extends AppCompatActivity {
    TextView sender,recipient,address,rent,rooms,bathrooms,
            floors,parking,hydro,heating,water,active;
    Button back, accept, decline;
    PropertyHelper propertyHelper;
    RequestHelper requestHelper;
    UserHelper userHelper;
    public UserModel user;
    public static RequestModel request;
    public PropertyModel property;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_request);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        propertyHelper=new PropertyHelper(this);
        requestHelper=new RequestHelper(this);
        userHelper=new UserHelper(this);
        user=userHelper.getUserModel(MainActivity.eMail);
        property=propertyHelper.getPropertyModel(request.getPropertyId());
        init();
        setTextViews();
        setEventListeners();
        setVisibility();
    }
    protected void onResume(){
        super.onResume();
        user=userHelper.getUserModel(MainActivity.eMail);
        property=propertyHelper.getPropertyModel(request.getPropertyId());
    }

    private void setTextViews() {
        PropertyModel property = propertyHelper.getPropertyModel(request.getPropertyId());
        sender.setText("Sender : "+userHelper.getName(request.getSenderId()));
        recipient.setText("Recipient :"+userHelper.getName(request.getRecipientId()));
        address.setText("Address : "+property.getAddress());
        rent.setText("Rent : $"+ String.valueOf(property.getRent()));
        rooms.setText("Rooms :"+String.valueOf(property.getRooms()));
        bathrooms.setText("Bathrooms :" +String.valueOf(property.getBathrooms()));
        floors.setText("Floors :"+String.valueOf(property.getFloors()));
        parking.setText("Parking :"+String.valueOf(property.getParking()));
        hydro.setText(getResources().getStringArray(R.array.hydro)[property.getHydro()]);
        heating.setText(getResources().getStringArray(R.array.heating)[property.getHeating()]);
        water.setText(getResources().getStringArray(R.array.water)[property.getWater()]);
        if(request.getIsActive()==0){
            active.setText(RequestMenu.mode);
        }

    }

    private void init(){
        sender=findViewById(R.id.senderTextRequest);
        recipient=findViewById(R.id.recipientTextRequest);
        address=findViewById(R.id.addressTextRequest);
        rent=findViewById(R.id.rentTextRequest);
        rooms=findViewById(R.id.roomsTextRequest);
        bathrooms=findViewById(R.id.bathroomsTextRequest);
        floors=findViewById(R.id.floorsTextRequest);
        parking=findViewById(R.id.parkingTextRequest);
        hydro=findViewById(R.id.hydroTextRequest);
        heating=findViewById(R.id.heatingTextRequest);
        water=findViewById(R.id.waterTextRequest);
        back=findViewById(R.id.backButtonRequest);
        accept=findViewById(R.id.acceptButtonRequest);
        decline=findViewById(R.id.declineButtonRequest);
        active=findViewById(R.id.activeTextRequest);
    }

    private void setEventListeners(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RequestMenu.class));
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (user.getType()){
                    case "Landlord":
                        requestHelper.acceptClient(property.getId(), request.getSenderId());
                        propertyHelper.updateClient(property.getId(),request.getSenderId());
                        back.callOnClick();
                    case "PropertyManager":
                        switch(RequestMenu.mode){
                            case "ClientFinder":
                                requestHelper.acceptClient(request.getPropertyId(),request.getSenderId());
                                propertyHelper.updateClient(request.getPropertyId(),request.getSenderId());
                                back.callOnClick();
                                break;
                            case "Normal":
                                requestHelper.manageProperty(user.getId(),request.getRequestId());
                                propertyHelper.updatePropertyManager(request.getPropertyId(),user.getId());
                                back.callOnClick();
                                break;
                        }

                }

            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestHelper.updateActive(request.getPropertyId(),request.getSenderId());
                startActivity(new Intent(getApplicationContext(),RequestMenu.class));
            }
        });
    }
    private void setVisibility(){
        switch(user.getType()){
            case "Client":
                accept.setVisibility(View.INVISIBLE);
                decline.setVisibility(View.INVISIBLE);
                break;
            case "Landlord":
                accept.setVisibility(View.VISIBLE);
                decline.setVisibility(View.VISIBLE);
                break;
            case "PropertyManager":
                accept.setVisibility(View.VISIBLE);
                decline.setVisibility(View.VISIBLE);
                break;
        }
    }
}