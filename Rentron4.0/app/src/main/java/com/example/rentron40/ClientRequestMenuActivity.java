package com.example.rentron40;

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

import java.util.List;

public class ClientRequestMenuActivity extends AppCompatActivity {
    ListView requestList;
    Button previous,active,inactive,all;
    RequestHelper requestHelper;

    public static RequestModel request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client_request_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        requestHelper=new RequestHelper(this);
        init();
        setEventListeners();
        updateDisplayedRequests();
    }

    private void init() {
        requestList = findViewById(R.id.clientRequestMenuList);
        previous = findViewById(R.id.clientRequestMenuPrevious);
        active=findViewById(R.id.clientRequestMenuActive);
        inactive=findViewById(R.id.clientRequestMenuInactive);
        all=findViewById(R.id.clientRequestMenuAll);
    }

    private void setEventListeners() {
        requestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                request = (RequestModel) requestList.getItemAtPosition(position);
                startActivity(new Intent(getApplicationContext(), ClientRequestActivity.class));
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ClientActivity.class));
            }
        });
        active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDisplayedRequests(1);
            }
        });
        inactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDisplayedRequests(0);
            }
        });
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDisplayedRequests();
            }
        });
    }
    private void updateDisplayedRequests(int active){
        List<RequestModel> requests=requestHelper.findRequests(-1,MainActivity.user.getId(),-1,-1,-1,active);
        updateDisplayedRequests(requests);
    }
    private void updateDisplayedRequests(){
        List<RequestModel> requests=requestHelper.findRequests(-1,MainActivity.user.getId(),-1,-1,-1,-1);
        updateDisplayedRequests(requests);
    }
    private void updateDisplayedRequests(List<RequestModel> requests){
        ArrayAdapter<RequestModel> adapter=new ArrayAdapter<RequestModel>(getApplicationContext(),
                android.R.layout.simple_list_item_1,requests);
        requestList.setAdapter(adapter);
    }
}