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

import java.util.List;

public class SearchPropertyActivity extends AppCompatActivity {
    Button filter, previous;
    ListView propertyList;
    PropertyHelper propertyHelper;
    UserHelper userHelper;
    public static PropertyModel propertyFilter=new PropertyModel();
    public static PropertyModel property;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_property);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        propertyHelper=new PropertyHelper(this);
        userHelper=new UserHelper(this);
        propertyFilter.setOccupied(0);
        init();
        setEventListeners();
        updateDisplayedProperty();
    }

    private void setEventListeners() {
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ClientPropertyFilterActivity.class));
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ClientActivity.class));
            }
        });
        propertyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                property=(PropertyModel) propertyList.getItemAtPosition(position);
                startActivity(new Intent(getApplicationContext(),ClientPropertyMenu.class));
            }
        });

    }
    private void updateDisplayedProperty(){
        List<PropertyModel> properties=propertyHelper.findPropertyForClients(propertyFilter);
        updateDisplayedProperty(properties);
    }
    private void updateDisplayedProperty(List<PropertyModel> properties){
        ArrayAdapter<PropertyModel> adapter=new ArrayAdapter<PropertyModel>(getApplicationContext(),
                android.R.layout.simple_list_item_1,properties);
        propertyList.setAdapter(adapter);
    }

    private void init(){
        filter=findViewById(R.id.filterSearchButton);
        previous=findViewById(R.id.previousSearchButton);
        propertyList=findViewById(R.id.propertyListSearch);
    }
}