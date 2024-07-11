package com.example.rentron40;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class ClientFinder extends AppCompatActivity {
    UserHelper userHelper;
    PropertyHelper propertyHelper;
    ListView clientList;
    TextView name,birthyear;
    Button add,previous,active,inactive,all;
    RequestModel user;
    public static RequestHelper requestHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client_finder);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        userHelper=new UserHelper(this);
        propertyHelper=new PropertyHelper(this);
        requestHelper=new RequestHelper(this);
        init();
        setEventListeners();
        updateDisplayedClients();
    }
    private void init(){
        clientList=findViewById(R.id.clientFinderClientList);
        name=findViewById(R.id.clientFinderName);
        birthyear=findViewById(R.id.clientFinderBirthYear);
        add=findViewById(R.id.clientFinderButtonAdd);
        previous=findViewById(R.id.clientFinderButtonPrevious);
        active=findViewById(R.id.clientFinderActive);
        inactive=findViewById(R.id.clientFinderInactive);
        all=findViewById(R.id.clientFinderAll);

    }
    private void setEventListeners(){
        clientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                user=(RequestModel) clientList.getItemAtPosition(position);
                name.setText(userHelper.getName(user.getSenderId()));
                birthyear.setText(String.valueOf(userHelper.getBirthYear(user.getSenderId())));
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user!=null) {
                    propertyHelper.updateClient(LandlordActivity.property.getId(), user.getSenderId());
                    requestHelper.updateActive(user.getPropertyId(),user.getSenderId());
                    requestHelper.deleteUserByProperty(LandlordActivity.property.getId(),user.getRecipientId());
                    startActivity(new Intent(getApplicationContext(), LandlordActivity.class));
                }
                else{
                    Toast.makeText(getApplicationContext(), "PLEASE SELECT CLIENT", Toast.LENGTH_SHORT).show();
                }
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user=null;
                startActivity(new Intent(getApplicationContext(),PropertyMenu.class));
            }
        });
        active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDisplayedClients(1);
            }
        });
        inactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDisplayedClients(0);
            }
        });
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDisplayedClients();
            }
        });
    }
    private void updateDisplayedClients(int active){
        List<RequestModel> requests=requestHelper.findRequests(-1,MainActivity.user.getId(),-1,-1,-1,active);
        updateDisplayedClients(requests);
    }

    private void updateDisplayedClients(){
        List<RequestModel> request=requestHelper.findRequests(-1,-1,
                LandlordActivity.property.getId(),MainActivity.user.getId(),-1,-1);
        updateDisplayedClients(request);
    }
    private void updateDisplayedClients(List<RequestModel> request){
        ArrayAdapter<RequestModel> adapter=new ArrayAdapter<RequestModel>(getApplicationContext(),
                android.R.layout.simple_list_item_1,request);
        clientList.setAdapter(adapter);
    }
}