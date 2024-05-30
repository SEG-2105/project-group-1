package com.example.rentron20;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class ClientActivity extends AppCompatActivity {
    MainActivity main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        User user=MainActivity.getUser();
        Button deleteClient=findViewById(R.id.deleteClient);
        TextView tv=(TextView)findViewById(R.id.welcometextview);
        tv.setText("We welcome you ,"+user.getFirstName());
        Button logoff=(Button)findViewById(R.id.logoutclient);
        EditText year=(EditText) findViewById(R.id.setBirthYearC);
        TextView error=(TextView)findViewById(R.id.errorinBirth);
        Button submit=(Button)findViewById(R.id.submitAge);
        logoff.setVisibility(View.INVISIBLE);
        tv.setVisibility(View.INVISIBLE);
        deleteClient.setVisibility(View.INVISIBLE);
        if(MainActivity.user.getBirthYear()==0){
            submit.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    if(setBirthYear(year)){
                        year.setVisibility(View.GONE);
                        error.setVisibility(View.GONE);
                        submit.setVisibility(View.GONE);
                        logoff.setVisibility(View.VISIBLE);
                        tv.setVisibility(View.VISIBLE);
                        deleteClient.setVisibility(View.VISIBLE);
                    }
                    else{
                        error.setText("Birth year must be between 1915 and 2023");
                    }
                }
            });
        }else{
            year.setVisibility(View.GONE);
            error.setVisibility(View.GONE);
            submit.setVisibility(View.GONE);
            logoff.setVisibility(View.VISIBLE);
            tv.setVisibility(View.VISIBLE);
            deleteClient.setVisibility(View.VISIBLE);
        }
        deleteClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccount();
            }
        });
        logoff.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                MainActivity.resetUser();
                startActivity(new Intent(ClientActivity.this,MainActivity.class));
            }
        });

    }

    public boolean setBirthYear(EditText year){
        try{
            return MainActivity.user.setBirthYear(toInteger(year));
        }catch(Exception e){
            return false;
        }
    }
    public int toInteger(EditText year){
        try{
            return Integer.parseInt(year.getEditableText().toString());
        }catch(Exception e){
            return 0;
        }
    }
    public void deleteAccount(){
        MainActivity.remove(MainActivity.getUser());
        startActivity(new Intent(ClientActivity.this,MainActivity.class));
    }
}