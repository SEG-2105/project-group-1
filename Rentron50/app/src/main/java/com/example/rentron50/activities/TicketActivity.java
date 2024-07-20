package com.example.rentron50.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
import com.example.rentron50.classes.TicketHelper;
import com.example.rentron50.classes.TicketModel;
import com.example.rentron50.classes.UserHelper;
import com.example.rentron50.classes.UserModel;

import java.util.List;

public class TicketActivity extends AppCompatActivity {
    Button back,resolve,accept,reject;
    TextView date,address,message,type,urgency,status;
    ListView logList;
    public static int ticketId;
    TicketModel ticket;
    TicketHelper ticketHelper;
    UserHelper userHelper;
    PropertyHelper propertyHelper;
    LogHelper logHelper;
    UserModel user;

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
        init();
        setEventListeners();
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
                if(ticket.getStatus()!=2){
                    resolve.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                resolve.setVisibility(View.INVISIBLE);
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