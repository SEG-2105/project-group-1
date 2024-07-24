package com.example.rentron50.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rentron50.R;
import com.example.rentron50.classes.LogHelper;
import com.example.rentron50.classes.LogModel;
import com.example.rentron50.classes.PropertyHelper;
import com.example.rentron50.classes.PropertyModel;
import com.example.rentron50.classes.TicketHelper;
import com.example.rentron50.classes.TicketModel;
import com.example.rentron50.classes.UserHelper;
import com.example.rentron50.classes.UserModel;

import java.util.List;

public class TicketActivity extends AppCompatActivity {
    Button back,resolve,accept,reject,rate;
    TextView date,address,message,type,urgency,status;
    ListView logList;
    public static int ticketId;
    TicketModel ticket;
    TicketHelper ticketHelper;
    UserHelper userHelper;
    PropertyHelper propertyHelper;
    Spinner rating;
    int rat;
    PropertyModel property;
    LogHelper logHelper;
    UserModel user;
    final String[] ratings={"Ratings","0","1","2","3","4","5"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ticket);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ticketHelper=new TicketHelper(this);
        propertyHelper=new PropertyHelper(this);
        userHelper=new UserHelper(this);
        logHelper=new LogHelper(this);
        ticket=ticketHelper.getTicketById(ticketId);
        user=userHelper.getUserModel(MainActivity.eMail);
        property=propertyHelper.getPropertyModel(MyPropertyActivity.propertyId);
        init();
        setEventListeners();
        setSpinners();
        setTextViews();
        setVisibility();
        updateDisplayedLogs();
    }
    private void init(){
        back=findViewById(R.id.backButtonTicket);
        resolve=findViewById(R.id.resolveButtonTicket);
        date=findViewById(R.id.dateTextTicket);
        address=findViewById(R.id.addressTextTicket);
        message=findViewById(R.id.messageTextTicket);
        type=findViewById(R.id.typeTextTicket);
        urgency=findViewById(R.id.urgencyTextTicket);
        status=findViewById(R.id.statusTextTicket);
        logList=findViewById(R.id.logListTicket);
        accept=findViewById(R.id.acceptButtonTicket);
        reject=findViewById(R.id.rejectButtonTicket);
        rating=findViewById(R.id.ratingSpinnerTicket);
        rate=findViewById(R.id.ratingButtonTicket);
    }
    private void setEventListeners(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),TicketMenuActivity.class));
            }
        });
        resolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ticketHelper.resolveTicket(ticketId);
                ticket=ticketHelper.getTicketModel(ticketId);
                logHelper.resolveTicket(ticketId);
                setTextViews();
                updateDisplayedLogs();
                setVisibility();
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ticketHelper.acceptTicket(ticketId);
                ticket=ticketHelper.getTicketModel(ticketId);
                logHelper.acceptTicket(ticketId);
                updateDisplayedLogs();
                setTextViews();
                setVisibility();
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ticketHelper.RejectTicket(ticketId);
                ticket=ticketHelper.getTicketModel(ticketId);
                logHelper.rejectTicket(ticketId);
                setTextViews();
                updateDisplayedLogs();
                setVisibility();
            }
        });
        rating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int rat=position-1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ticketHelper.rateTicket(ticket.getId(),ticket.getPmID(),rat);
            }
        });
    }
    private void setSpinners(){
        ArrayAdapter<CharSequence> ratingAdapter=new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1,ratings
        );
        rating.setAdapter(ratingAdapter);
    }
    private void setTextViews(){
        date.setText(ticket.getDate());
        address.setText(propertyHelper.getPropertyModel(ticket.getPropertyId()).getAddress());
        message.setText(ticket.getMessage());
        type.setText(ticket.getType());
        urgency.setText(String.valueOf(ticket.getUrgency()));
        status.setText(String.valueOf((ticket.getStatus()==0)?"RESOLVED":(ticket.getStatus()==1)?"Rejected":(ticket.getStatus()==2)?"In Progress":"Limbo"));
    }
    private void setVisibility(){
        switch(user.getType()){
            case "Client":
                resolve.setVisibility(View.VISIBLE);
                accept.setVisibility(View.INVISIBLE);
                reject.setVisibility(View.INVISIBLE);
                rate.setVisibility(View.INVISIBLE);
                rating.setVisibility(View.INVISIBLE);
                if(ticket.getStatus()!=2){
                    resolve.setVisibility(View.INVISIBLE);
                    rating.setVisibility(View.VISIBLE);
                    rate.setVisibility(View.VISIBLE);
                }
                break;
            default:
                resolve.setVisibility(View.INVISIBLE);
                rate.setVisibility(View.INVISIBLE);
                rating.setVisibility(View.INVISIBLE);
                if(ticket.getStatus()!=-1){
                    accept.setVisibility(View.INVISIBLE);
                    reject.setVisibility(View.INVISIBLE);
                }
                break;
        }

    }
    private void updateDisplayedLogs(){
        try{
            List<LogModel> logs=logHelper.getLogByTicket(ticketId);
            updateDisplayedTickets(logs);
        }catch (Exception e){

        }

    }
    private void updateDisplayedTickets(List<LogModel> logs){
        ArrayAdapter<LogModel> adapter=new ArrayAdapter<LogModel>(getApplicationContext(),
                android.R.layout.simple_list_item_1,logs);
        logList.setAdapter(adapter);
    }

}