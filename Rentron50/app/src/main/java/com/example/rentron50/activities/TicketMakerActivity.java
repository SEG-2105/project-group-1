package com.example.rentron50.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rentron50.R;
import com.example.rentron50.classes.LogHelper;
import com.example.rentron50.classes.PropertyHelper;
import com.example.rentron50.classes.PropertyModel;
import com.example.rentron50.classes.TicketHelper;
import com.example.rentron50.classes.TicketModel;

public class TicketMakerActivity extends AppCompatActivity {
    Button submit, back;
    EditText message;
    Spinner type,urgency;
    TicketHelper ticketHelper;
    PropertyHelper propertyHelper;
    LogHelper logHelper;
    String typeTemp;
    int urgencyTemp;
    PropertyModel property;
    private final String[] urgencyOptions={"0","1","2","3","4","5"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ticket_maker);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ticketHelper=new TicketHelper(this);
        logHelper=new LogHelper(this);
        init();
        setEventListeners();
        setSpinners();
    }
    private void init(){
        submit=findViewById(R.id.submitButtonTicketMaker);
        back=findViewById(R.id.backButtonTicketMaker);
        message=findViewById(R.id.messageEditMultilineTicketMaker);
        type=findViewById(R.id.typeSpinnerTicketMaker);
        urgency=findViewById(R.id.urgencySpinnerTicketMaker);
    }
    private void setEventListeners(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),TicketMenuActivity.class));
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TicketModel ticketModel=new TicketModel(MyPropertyActivity.propertyId
                        ,typeTemp,urgencyTemp,message.getEditableText().toString());
                ticketHelper.addTicket(ticketModel);
                int ticketId=ticketHelper.getMaxId(MyPropertyActivity.propertyId);
                logHelper.ticketInLimbo(ticketId);
                startActivity(new Intent(getApplicationContext(),TicketMenuActivity.class));
            }
        });
        urgency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                urgencyTemp=Integer.parseInt((String)urgency.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeTemp=(String)type.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void setSpinners(){
        ArrayAdapter<CharSequence> ticketTypeAdapter=ArrayAdapter.createFromResource(
                getApplicationContext(),R.array.ticketType, android.R.layout.simple_spinner_item
        );
        ArrayAdapter<CharSequence> ticketUrgencyAdapter=new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item,
                urgencyOptions);
        type.setAdapter(ticketTypeAdapter);
        urgency.setAdapter(ticketUrgencyAdapter);
    }
}