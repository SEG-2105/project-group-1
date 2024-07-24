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
import com.example.rentron50.classes.PropertyHelper;
import com.example.rentron50.classes.PropertyModel;
import com.example.rentron50.classes.User;
import com.example.rentron50.classes.UserHelper;
import com.example.rentron50.classes.UserModel;

import java.util.List;

public class MyPropertyActivity extends AppCompatActivity {
    Button back;
    ListView myPropertyList;
    PropertyHelper propertyHelper;
    UserHelper userHelper;
    public static int propertyId=1;
    UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_property);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        propertyHelper=new PropertyHelper(this);
        userHelper=new UserHelper(this);
        user=userHelper.getUserModel(MainActivity.eMail);
        init();
        updateDisplayedProperties();
        setEventListeners();
    }
    private void init(){
        back=findViewById(R.id.backButtonMyProperty);
        myPropertyList=findViewById(R.id.myPropertyList);
    }
    private void setEventListeners(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(user.getType()){
                    case "Client":
                        startActivity(new Intent(getApplicationContext(),ClientActivity.class));
                        break;
                    case "Landlord":
                        startActivity(new Intent(getApplicationContext(),LandlordActivity.class));
                        break;
                    case "PropertyManager":
                        startActivity(new Intent(getApplicationContext(),PropertyManagerActivity.class));
                        break;
                }
            }
        });
        myPropertyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                propertyId=((PropertyModel)myPropertyList.getItemAtPosition(position)).getId();
                startActivity(new Intent(getApplicationContext(),PropertyMenuActivity.class));
            }
        });

}
    private void updateDisplayedProperties(){
        List<PropertyModel> properties;
        switch(user.getType()){
            case "Client":
                properties=propertyHelper.getProperties(user.getId());
                updateDisplayedProperties(properties);
                break;
            case "Landlord":
                properties=propertyHelper.getOwnedProperties(user.getId());
                updateDisplayedProperties(properties);
                break;
            case "PropertyManager":
                properties=propertyHelper.getPropertiesToManage(user.getId());
                updateDisplayedProperties(properties);
                break;
        }

    }
    private void updateDisplayedProperties(List<PropertyModel> properties){
        ArrayAdapter<PropertyModel> adapter=new ArrayAdapter<PropertyModel>(getApplicationContext(),
                android.R.layout.simple_list_item_1,properties);
        myPropertyList.setAdapter(adapter);
    }
}