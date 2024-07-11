package com.example.rentron40;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ClientTicketMakerActivity extends AppCompatActivity {
    EditText urgency,message, type, date;
    Button previous,add;
    TicketHelper ticketHelper;
    PropertyHelper propertyHelper;
    UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client_ticket_maker);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ticketHelper=new TicketHelper(this);
        propertyHelper=new PropertyHelper(this);
        userHelper=new UserHelper(this);
        init();
        setEventListeners();
    }
    private void init(){
        urgency=findViewById(R.id.clientTicketMakerUrgency);
        message=findViewById(R.id.clientTicketMakerMessage);
        type=findViewById(R.id.clientTicketMakerType);
        date=findViewById(R.id.clientTicketMakerDate);
        previous=findViewById(R.id.clientTicketMakerPrevious);
        add=findViewById(R.id.clientTicketMakerAdd);
    }
    private void setEventListeners(){
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ClientTicketMenu.class));
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TicketModel ticket=new TicketModel(
                        propertyHelper.getPropertyModel(MainActivity.user.getId(),0).getId(),
                        type.getEditableText().toString(),
                        Integer.parseInt(urgency.getEditableText().toString()),
                        message.getEditableText().toString(),
                        date.getEditableText().toString()
                );
                ticketHelper.addTicket(ticket);
                startActivity(new Intent(getApplicationContext(),ClientTicketMenu.class));
            }
        });
    }
}