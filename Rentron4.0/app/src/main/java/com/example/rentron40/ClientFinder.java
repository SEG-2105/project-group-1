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
    Button add,previous;
    UserModel user;

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
    }
    private void setEventListeners(){
        clientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                user=(UserModel) clientList.getItemAtPosition(position);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user!=null) {
                    propertyHelper.updateClient(LandlordActivity.property.getId(), user.getId());
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
    }

    private void updateDisplayedClients(){
        List<UserModel> properties=userHelper.getClients();
        updateDisplayedClients(properties);
    }
    private void updateDisplayedClients(List<UserModel> users){
        ArrayAdapter<UserModel> adapter=new ArrayAdapter<UserModel>(getApplicationContext(),
                android.R.layout.simple_list_item_1,users);
        clientList.setAdapter(adapter);
    }
}