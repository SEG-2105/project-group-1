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

public class TicketMenuActivity extends AppCompatActivity {
    ListView ticketList;
    Button previous;
    TicketHelper ticketHelper;
    TextView type, urgency,message, date,status;
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
        init();
        setEventListeners();
        updateDisplayedTickets();
    }
    public void setEventListeners(){
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PropertyMenu.class));
            }
        });
        ticketList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               TicketModel ticket= (TicketModel)ticketList.getItemAtPosition(position);
               type.setText(ticket.getType());
               urgency.setText(String.valueOf(ticket.getUrgency()));
               message.setText(ticket.getMessage());
               date.setText(ticket.getDate());
               if(ticket.getStatus()==1){
                   status.setText("CLOSED");
               }
               else{
                   status.setText("OPEN");
               }

            }
        });
    }
    public void init(){
        ticketList=findViewById(R.id.ticketMenuList);
        previous=findViewById(R.id.ticketMenuPrevious);
        type=findViewById(R.id.ticketMenuType);
        urgency=findViewById(R.id.ticketMenuUrgency);
        message=findViewById(R.id.ticketMenuMessage);
        date=findViewById(R.id.ticketMenuDate);
        status=findViewById(R.id.ticketMenuStatus);
    }
    private void updateDisplayedTickets(){
        List<TicketModel> tickets=ticketHelper.getAllTickets();
        updateDisplayedTickets(tickets);
    }
    private void updateDisplayedTickets(List<TicketModel> tickets){
        ArrayAdapter<TicketModel> adapter=new ArrayAdapter<TicketModel>(getApplicationContext(),
                android.R.layout.simple_list_item_1,tickets);
        ticketList.setAdapter(adapter);
    }
}