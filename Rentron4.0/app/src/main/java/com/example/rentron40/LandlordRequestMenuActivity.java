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

public class LandlordRequestMenuActivity extends AppCompatActivity {
    ListView requestList;
    Button previous;
    RequestHelper requestHelper;
    public static RequestModel request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_landlord_request_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        setEventListeners();
        updateDisplayedRequests();
    }
    private void init(){
        requestList=findViewById(R.id.landlordRequestMenuList);
        previous=findViewById(R.id.landlordRequestMenuPrevious);
    }
    private void setEventListeners(){
        requestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                request=(RequestModel)requestList.getItemAtPosition(position);
                startActivity(new Intent(getApplicationContext(),RequestActivity.class));
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LandlordActivity.class));
            }
        });
    }
    private void updateDisplayedRequests(){
        List<RequestModel> requests=requestHelper.findRequests(-1,-1,-1,MainActivity.user.getId(),-1,-1);
        updateDisplayedRequests(requests);
    }
    private void updateDisplayedRequests(List<RequestModel> requests){
        ArrayAdapter<RequestModel> adapter=new ArrayAdapter<RequestModel>(getApplicationContext(),
                android.R.layout.simple_list_item_1,requests);
        requestList.setAdapter(adapter);
    }
}