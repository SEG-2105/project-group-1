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
        TextView erroryear=(TextView) findViewById(R.id.erroryear);
        if(MainActivity.user.getBirthYear()==0){
            setYear.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    if(setBirthYear(year)){
                        setYear.setVisibility(View.GONE);
                        year.setVisibility(View.GONE);
                        erroryear.setVisibility(View.GONE);
                        }
                    else{
                        erroryear.setText("Birth year must be between 1915 and 2023");
                    }
                }
            });
        }else{
            setYear.setVisibility(View.GONE);
            year.setVisibility(View.GONE);
            erroryear.setVisibility(View.GONE);
        }


        logoff.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                LogOff();
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
    public void LogOff(){
        Intent i=new Intent(LandlordView.this,MainActivity.class);
        startActivity(i);
    }
}