package com.example.rentron50.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rentron50.R;
import com.example.rentron50.classes.Client;
import com.example.rentron50.classes.PropertyHelper;
import com.example.rentron50.classes.PropertyModel;
import com.example.rentron50.classes.RequestHelper;
import com.example.rentron50.classes.RequestModel;
import com.example.rentron50.classes.UserHelper;
import com.example.rentron50.classes.UserModel;

import java.util.List;

public class RequestMenu extends AppCompatActivity {
    ListView requestList;
    Button back,active,inactive;
    RequestHelper requestHelper;
    UserHelper userHelper;
    public static String mode;
    UserModel user;
    PropertyModel property;
    PropertyHelper propertyHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_request_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        requestHelper=new RequestHelper(this);
        userHelper=new UserHelper(this);
        propertyHelper=new PropertyHelper(this);
        user=userHelper.getUserModel(MainActivity.eMail);
        MainActivity.eMail=user.getEmailAddress();
        init();
        setEventListeners();
        updateDisplayedClients();
    }
    protected void onStart(){
        super.onStart();
        user=userHelper.getUserModel(MainActivity.eMail);
        MainActivity.eMail=user.getEmailAddress();
        updateDisplayedClients();
    }
    protected void onResume(){
        super.onResume();
        user=userHelper.getUserModel(MainActivity.eMail);
        MainActivity.eMail=user.getEmailAddress();
        updateDisplayedClients();
    }
    private void init(){
        back=findViewById(R.id.backButtonRequestMenu);
        requestList=findViewById(R.id.requestListRequestMenu);
        active=findViewById(R.id.activeButtonTicketMenu);
        inactive=findViewById(R.id.inactiveButtonTicketMenu);
    }
    private void setEventListeners(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(user.getType()){
                    case "Client":
                        startActivity(new Intent(getApplicationContext(), ClientActivity.class));
                        break;
                    case "Landlord":
                        if(RequestMenu.mode.equals("Normal")){
                            startActivity(new Intent(getApplicationContext(),PropertyManagerActivity.class));
                        }else{
                            startActivity(new Intent(getApplicationContext(),PropertyMenuActivity.class));
                        }
                        break;
                    case "PropertyManager":
                        if(RequestMenu.mode.equals("Normal")){
                            startActivity(new Intent(getApplicationContext(),PropertyManagerActivity.class));
                        }else{
                            startActivity(new Intent(getApplicationContext(),PropertyMenuActivity.class));
                        }
                        break;
                }
                startActivity(new Intent(getApplicationContext(),PropertyMenuActivity.class));
            }
        });
        requestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RequestActivity.request=(RequestModel) requestList.getItemAtPosition(position);
                startActivity(new Intent(getApplicationContext(),RequestActivity.class));
            }
        });
        active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(user.getType()){
                    case "Client":
                        updateDisplayedClients(requestHelper.getActiveRequestsBySender(user.getId(),0));
                        break;
                    case "Landlord":
                        switch(RequestMenu.mode){
                            case "ClientFinder":
                                updateDisplayedClients(requestHelper.getActiveClientPropertyRequests(property.getId()));
                                break;
                            case "PropertyManagerFinder":
                                updateDisplayedClients(requestHelper.getActiveRequestsBySender(user.getId(),1));
                                break;
                        }


                    case "PropertyManager":
                        switch(RequestMenu.mode){
                            case "ClientFinder":
                                updateDisplayedClients(requestHelper.getActiveClientPropertyRequests(property.getId()));
                                break;
                            case "Normal":
                                updateDisplayedClients(requestHelper.getActiveRequestsByRecipient(user.getId()));

                        }
                }

            }
        });
        inactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(user.getType()){
                    case "Client":
                        updateDisplayedClients(requestHelper.getInActiveRequestsBySender(user.getId(),0));
                        break;
                    case "Landlord":
                        switch (RequestMenu.mode){
                            case "ClientFinder":
                                updateDisplayedClients(requestHelper.getInActiveClientPropertyRequests(property.getId()));
                                break;
                            case "PropertyManagerFinder":
                                updateDisplayedClients(requestHelper.getInActiveRequestsBySender(user.getId(),1));
                        }

                        break;
                    case "PropertyManager":
                        switch(RequestMenu.mode){
                            case "ClientFinder":
                                updateDisplayedClients(requestHelper.getInActiveClientPropertyRequests(property.getId()));
                                break;
                            case "Normal":
                                updateDisplayedClients(requestHelper.getInActiveRequestsByRecipient(user.getId(),1));

                        }
                }
            }
        });
    }
    private void updateDisplayedClients(){
        switch(user.getType()){
            case "Client":
                List<RequestModel> requests=requestHelper.findRequestsBySenderId(user.getId());
                updateDisplayedClients(requests);
                break;
            case "Landlord":
                switch(mode){
                    case "ClientFinder":
                        property=propertyHelper.getPropertyModel(MyPropertyActivity.propertyId);
                        requests=requestHelper.getPropertyClientRequests(user.getId(),property.getId());
                        updateDisplayedClients(requests);
                        break;
                }
                break;
            case "PropertyManager":
                switch(mode){
                    case "ClientFinder":
                        property=propertyHelper.getPropertyModel(MyPropertyActivity.propertyId);
                        requests=requestHelper.getPropertyClientRequests(property.getId());
                        updateDisplayedClients(requests);
                        break;
                    case "Normal":
                        requests=requestHelper.getRequestsForPm(user.getId());
                        updateDisplayedClients(requests);
                        break;
                }
                break;

        }
    }
    private void updateDisplayedClients(List<RequestModel> requests){
        ArrayAdapter<RequestModel> adapter=new ArrayAdapter<RequestModel>(getApplicationContext(),
                android.R.layout.simple_list_item_1,requests);
        requestList.setAdapter(adapter);
    }
}