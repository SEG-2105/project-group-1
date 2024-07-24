package com.example.rentron50.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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
import com.example.rentron50.classes.RequestModel;
import com.example.rentron50.classes.TicketHelper;
import com.example.rentron50.classes.UserHelper;
import com.example.rentron50.classes.UserModel;

import java.util.List;

public class PropertyManagerFinderActivity extends AppCompatActivity {
    ListView pmList;
    TextView name,email,rating;
    EditText comission;
    Button back,submit;
    RequestHelper requestHelper;
    TicketHelper ticketHelper;
    PropertyHelper propertyHelper;
    UserHelper userHelper;
    UserModel user;
    PropertyModel property;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_property_manager_finder);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        requestHelper=new RequestHelper(this);
        propertyHelper=new PropertyHelper(this);
        userHelper=new UserHelper(this);
        ticketHelper=new TicketHelper(this);
        user=userHelper.getUserModel(MainActivity.eMail);
        property=propertyHelper.getPropertyModel(MyPropertyActivity.propertyId);
        init();
        setEventListeners();
        updateDisplayedClients();
    }

    private void setEventListeners() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PropertyMenuActivity.class));
            }
        });
        pmList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserModel pm=(UserModel)pmList.getItemAtPosition(position);
                name.setText(pm.getName());
                email.setText(pm.getEmailAddress());
                rating.setText(String.valueOf(ticketHelper.getPropertyManagerAverageRating(pm.getId())));
                submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RequestMenu.mode="PropertyManagerFinder";
                            try{RequestModel request=new RequestModel(property.getId(),user.getId()
                                    ,pm.getId(),1,Integer.parseInt(comission.getEditableText().toString()));
                            requestHelper.addRequest(request);
                            startActivity(new Intent(getApplicationContext(),PropertyMenuActivity.class));}
                            catch(Exception e){
                                Toast.makeText(getApplicationContext(),"Please enter comission amount",Toast.LENGTH_LONG).show();
                            }
                        }
                });

            }
        });

    }

    private void init(){
        pmList=findViewById(R.id.propertyManagerListPropertyManagerFinder);
        name=findViewById(R.id.nameTextPropertyManagerFinder);
        email=findViewById(R.id.emailTextPropertyManagerFinder);
        comission=findViewById(R.id.commissionEditPropertyManagerFinder);
        back=findViewById(R.id.backButtonPropertyManagerFinder);
        submit=findViewById(R.id.submitButtonPropertyManagerFinder);
        rating=findViewById(R.id.ratingTextPropertyManagerFinder);
    }
    private void updateDisplayedClients(){
        List<UserModel> pms=userHelper.getPms();
        updateDisplayedClients(pms);
    }
    private void updateDisplayedClients(List<UserModel> pms){
        ArrayAdapter<UserModel> adapter=new ArrayAdapter<UserModel>(getApplicationContext(),
                android.R.layout.simple_list_item_1,pms);
        pmList.setAdapter(adapter);
    }
}