package com.example.rentron50.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rentron50.R;
import com.example.rentron50.classes.UserHelper;
import com.example.rentron50.classes.UserModel;

public class PropertyManagerActivity extends AppCompatActivity {
    Button logout, myProp, requests;
    UserHelper userHelper;
    UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_property_manager);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        userHelper=new UserHelper(this);
        user=userHelper.getUserModel(MainActivity.eMail);
        init();
        setEventListeners();
    }
    protected void onStart(){
        super.onStart();
        user=userHelper.getUserModel(MainActivity.eMail);
    }
    protected void onResume(){
        super.onResume();
        user=userHelper.getUserModel(MainActivity.eMail);
    }
    private void init(){
        logout=findViewById(R.id.logoutButtonPropertyManager);
        myProp=findViewById(R.id.myPropertiesButtonPropertyManager);
        requests=findViewById(R.id.requestsButtonPropertyManager);
    }
    private void setEventListeners(){
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.logout();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        myProp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MyPropertyActivity.class));
            }
        });
        requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestMenu.mode="Normal";
                startActivity(new Intent(getApplicationContext(),RequestMenu.class));
            }
        });
    }
}