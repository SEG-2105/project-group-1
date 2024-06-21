package com.example.rentron35;

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

public class LandlordActivity extends AppCompatActivity {

    Button registerProperty,logout;
    ListView propertyList;
    PropertyHelper propertyHelper;
    public static PropertyModel property;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_landlord);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        propertyHelper=new PropertyHelper(this);
        init();
        updateDisplayedProperty();
        setEventListeners();
    }
    private void init(){
        registerProperty=findViewById(R.id.buttonRegisterLandlordProperty);
        propertyList=findViewById(R.id.propertyList);
        tv=findViewById(R.id.textView);
        logout=findViewById(R.id.landlordLogOut);
        tv.setText(MainActivity.user.toString());
    }
    private void setEventListeners(){
        registerProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PropertyResigrationActivity.class));
                updateDisplayedProperty();
            }
        });
        propertyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                property=(PropertyModel)propertyList.getItemAtPosition(position);
                startActivity(new Intent(getApplicationContext(),PropertyMenu.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.user=null;
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        updateDisplayedProperty();
    }
    private void updateDisplayedProperty(){
        List<PropertyModel> properties=propertyHelper.getOwnedProperties(MainActivity.user.getId());
        updateDisplayedProperty(properties);
    }
    private void updateDisplayedProperty(List<PropertyModel> properties){
        ArrayAdapter<PropertyModel> adapter=new ArrayAdapter<PropertyModel>(getApplicationContext(),
                android.R.layout.simple_list_item_1,properties);
        propertyList.setAdapter(adapter);
    }

}