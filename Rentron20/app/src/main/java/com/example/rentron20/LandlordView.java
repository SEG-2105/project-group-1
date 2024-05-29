package com.example.rentron20;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LandlordView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_landlord_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button logoff=(Button)findViewById(R.id.logofflandlord);
        TextView txt=(TextView)findViewById(R.id.textView3);
        EditText year=(EditText)findViewById(R.id.editBirthYear);
        Button setYear=(Button)findViewById(R.id.setBirthYear);
        setYear.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                setBirthYear(Integer.parseInt(year.getEditableText().toString()));
                setYear.setVisibility(View.GONE);
                year.setVisibility(View.GONE);
            }
        });
        logoff.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                LogOff();

            }
        });
    }
    public void setBirthYear(int year){
        MainActivity.user.setBirthYear(year);
    }
    public void LogOff(){
        Intent i=new Intent(LandlordView.this,MainActivity.class);
        startActivity(i);
    }
}