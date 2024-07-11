package com.example.rentron40;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class ClientPropertyManager extends AppCompatActivity {
    Button previous;
    ListView propertyList;
    PropertyHelper propertyHelper;
    UserHelper userHelper;
    public static PropertyModel property;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client_property_manager);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        propertyHelper=new PropertyHelper(this);
        userHelper=new UserHelper(this);
        init();
        setEventListeners();
        updateDisplayedProperty();
    }

    private void setEventListeners() {
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ClientActivity.class));
            }
        });
        propertyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                property=(PropertyModel)  propertyList.getItemAtPosition(position);
                startActivity(new Intent(getApplicationContext(), ClientTicketMenu.class));
            }
        });
    }

    private void updateDisplayedProperty(){
        List<PropertyModel> properties=propertyHelper.getProperties(MainActivity.user.getId());
        updateDisplayedProperty(properties);
    }
    private void updateDisplayedProperty(List<PropertyModel> properties){
        ArrayAdapter<PropertyModel> adapter=new ArrayAdapter<PropertyModel>(getApplicationContext(),
                android.R.layout.simple_list_item_1,properties);
        propertyList.setAdapter(adapter);
    }
    public void init(){
        previous=findViewById(R.id.clientPropertyManagerPrevious);
        propertyList=findViewById(R.id.clientPropertyManagerPropertyList);
    }
}