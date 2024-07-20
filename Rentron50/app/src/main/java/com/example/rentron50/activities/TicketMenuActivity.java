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
import com.example.rentron50.classes.RequestModel;
import com.example.rentron50.classes.Ticket;
import com.example.rentron50.classes.TicketHelper;
import com.example.rentron50.classes.TicketModel;
import com.example.rentron50.classes.UserHelper;
import com.example.rentron50.classes.UserModel;

import java.util.List;

public class TicketMenuActivity extends AppCompatActivity {
    ListView ticketList;
    Button back,make,active,closed;
    TicketHelper ticketHelper;
    UserHelper userHelper;
    PropertyModel property;
    PropertyHelper propertyHelper;
    UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ticket_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ticketHelper=new TicketHelper(this);
        userHelper=new UserHelper(this);
        propertyHelper=new PropertyHelper(this);
        user=userHelper.getUserModel(MainActivity.eMail);
        property=propertyHelper.getPropertyModel(MyPropertyActivity.propertyId);
        init();
        setEventListeners();
        updateDisplayedTickets();
        setViews();
    }
    protected void onResume(){
        super.onResume();
        updateDisplayedTickets();
    }
    private void init(){
        ticketList=findViewById(R.id.ticketListTicketMenu);
        back=findViewById(R.id.backButtonTicketMenu);
        make=findViewById(R.id.makeButtonTicketMenu);
        active=findViewById(R.id.activeButtonTicketMenu);
        closed=findViewById(R.id.closedButtonTicketMenu);
    }
    private void setEventListeners(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PropertyMenuActivity.class));
            }
        });
        make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TicketMakerActivity.class));
            }
        });
        ticketList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TicketActivity.ticketId=((TicketModel)ticketList.getItemAtPosition(position)).getId();

                startActivity(new Intent(getApplicationContext(),TicketActivity.class));
                make.setText(String.valueOf(TicketActivity.ticketId));
            }
        });
        active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<TicketModel> tickets=ticketHelper.getActiveTickets(property.getId());
                updateDisplayedTickets(tickets);
            }
        });
        closed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<TicketModel> tickets=ticketHelper.getClosedTickets(property.getId());
                updateDisplayedTickets(tickets);
            }
        });
    }
    private void updateDisplayedTickets(){
        List<TicketModel> tickets=ticketHelper.getTicketByProperty(property.getId());
        updateDisplayedTickets(tickets);
    }
    private void updateDisplayedTickets(List<TicketModel> properties){
        ArrayAdapter<TicketModel> adapter=new ArrayAdapter<TicketModel>(getApplicationContext(),
                android.R.layout.simple_list_item_1,properties);
        ticketList.setAdapter(adapter);
    }
    private void setViews(){
        switch (user.getType()){
            case "Client":
                make.setVisibility(View.VISIBLE);
                break;
            case "Landlord":
                make.setVisibility(View.INVISIBLE);
                break;
            case "PropertyManager":
                make.setVisibility(View.INVISIBLE);
                break;
        }
    }
}