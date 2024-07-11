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

public class ClientTicketMenu extends AppCompatActivity {
    TextView urgency,message, type, date,status;
    Button previous,add,approve;
    ListView ticketList;
    TicketHelper ticketHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client_ticket_menu);
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
    private void init(){
        urgency=findViewById(R.id.clientTicketMenuUrgency);
        message=findViewById(R.id.clientTicketMenuMessage);
        type=findViewById(R.id.clientTicketMenuType);
        date=findViewById(R.id.clientTicketMenuDate);
        previous=findViewById(R.id.clientTicketMenuPrevious);
        add=findViewById(R.id.clientTicketMenuAdd);
        status=findViewById(R.id.clientTicketMenuStatus);
        ticketList=findViewById(R.id.clientTicketMenuTicketList);
        approve=findViewById(R.id.clientTicketMenuApprove);
    }
    private void setEventListeners() {
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ClientActivity.class));
            }
        });
        ticketList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TicketModel ticket = (TicketModel) ticketList.getItemAtPosition(position);
                type.setText(ticket.getType());
                urgency.setText(String.valueOf(ticket.getUrgency()));
                message.setText(ticket.getMessage());
                date.setText(ticket.getDate());
                if (ticket.getStatus() == 1) {
                    status.setText("CLOSED");
                } else {
                    status.setText("OPEN");
                }
                approve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ticketHelper.updateTicketStatus(ticket.getId(),1);
                        updateDisplayedTickets();
                        status.setText("CLOSED");
                    }
                });
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ClientTicketMakerActivity.class));
            }
        });
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