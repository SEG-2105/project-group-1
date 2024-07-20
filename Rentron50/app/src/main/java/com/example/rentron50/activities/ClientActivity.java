package com.example.rentron50.activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rentron50.R;
import com.example.rentron50.classes.PropertyHelper;
import com.example.rentron50.classes.PropertyModel;
import com.example.rentron50.classes.RequestHelper;
import com.example.rentron50.classes.UserHelper;

import java.util.List;

public class ClientActivity extends AppCompatActivity {
    Button logout,filter,requestMenu,myProp;
    ListView propertyList;
    UserHelper userHelper;
    PropertyHelper propertyHelper;
    public static PropertyModel propertyFilter;
    public static PropertyModel property;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        propertyHelper=new PropertyHelper(this);
        init();
        setEventListeners();
        updateDisplayedClients();
    }
    private void init(){
        logout=findViewById(R.id.logoutButtonClient);
        filter=findViewById(R.id.filterButtonClient);
        requestMenu=findViewById(R.id.requestsButtonClient);
        myProp=findViewById(R.id.myPropertiesButtonClient);
        propertyList=findViewById(R.id.propertyListClient);
    }
    private void setEventListeners(){
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.logout();
                Toast.makeText(getApplicationContext(),"User Logged Out",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PropertyFilterClientActivity.class));
            }
        });
        requestMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RequestMenu.class));
            }
        });
        myProp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),MyPropertyActivity.class));
            }
        });
        propertyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PropertyRequestActivity.property=(PropertyModel) propertyList.getItemAtPosition(position);
                startActivity(new Intent(getApplicationContext(),PropertyRequestActivity.class));
            }
        });
    }
    private void updateDisplayedClients(){
        List<PropertyModel> properties=propertyHelper.findPropertyForClients(propertyFilter);
        updateDisplayedClients(properties);
    }
    private void updateDisplayedClients(List<PropertyModel> properties){
        ArrayAdapter<PropertyModel> adapter=new ArrayAdapter<PropertyModel>(getApplicationContext(),
                android.R.layout.simple_list_item_1,properties);
        propertyList.setAdapter(adapter);
    }
}