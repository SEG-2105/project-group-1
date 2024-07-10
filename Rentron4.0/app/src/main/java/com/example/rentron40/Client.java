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

public class Client extends User {
    private int birthYear;

    public Client(String firstName, String lastName, int birthYear, String emailAddress, String password) {
        super(firstName, lastName, emailAddress, password);
        setBirthYear(birthYear);
    }

    public Client() {
        super();
    }

    public boolean setBirthYear(int birthYear) {
        if (birthYear > 1914 && birthYear < 2024) {
            this.birthYear = birthYear;
            return true;
        } else {
            this.birthYear = 0;
            return false;
        }
    }

    public int getBirthYear() {
        return birthYear;
    }

    public static boolean setBirthYear(int birthYear, Client c) {
        return c.setBirthYear(birthYear);
    }

    public static class ClientFinder extends AppCompatActivity {
        UserHelper userHelper;
        PropertyHelper propertyHelper;
        ListView clientList;
        TextView name,birthyear;
        Button add,evict;
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
                    propertyHelper.updateClient(LandlordActivity.property.getId(),user.getId());
                    startActivity(new Intent(getApplicationContext(),LandlordActivity.class));
                }
            });
            evict.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    propertyHelper.removeClient(LandlordActivity.property.getId());
                    Toast.makeText(getApplicationContext(),"Client has been evicted",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),LandlordActivity.class));
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
}
