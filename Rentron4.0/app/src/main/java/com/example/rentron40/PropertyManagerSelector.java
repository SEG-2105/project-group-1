package com.example.rentron40;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class PropertyManagerSelector extends AppCompatActivity {
    ListView propertyManagerList;
    EditText comission;
    TextView name, email;
    Button btn,remove;
    UserHelper userHelper;
    UserModel user;
    PropertyHelper propertyHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_property_manager_selector);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        userHelper=new UserHelper(this);
        propertyHelper=new PropertyHelper(this);
        init();
        setEventListeners();
        updateDisplayedUsers();
    }
    private void init(){
        propertyManagerList=findViewById(R.id.pmSelectorPmList);
        comission=findViewById(R.id.pmSelectorCommissionEdit);
        name=findViewById(R.id.pmSelectorNameTv);
        email=findViewById(R.id.pmSelectorEmailTv);
        btn=findViewById(R.id.pmSelectorPmRequest);
    }
    private void setEventListeners(){
        propertyManagerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                user=(UserModel)propertyManagerList.getItemAtPosition(position);
                name.setText(user.getName());
                email.setText(user.getEmailAddress());
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        propertyHelper.updatePropertyManager(LandlordActivity.property.getId(),user.getId());
                        startActivity(new Intent(getApplicationContext(),LandlordActivity.class));
                    }
                });
            }
        });


    }

    private void updateDisplayedUsers(){
        List<UserModel> users=userHelper.getPms();
        updateDisplayedUsers(users);
    }
    private void updateDisplayedUsers(List<UserModel> users){
        ArrayAdapter<UserModel> adapter=new ArrayAdapter<UserModel>(getApplicationContext(),
                android.R.layout.simple_list_item_1,users);
        propertyManagerList.setAdapter(adapter);
    }

}