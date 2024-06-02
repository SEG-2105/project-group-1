package com.example.rentron20;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PropertyManagerView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_property_manager_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button delete=(Button)findViewById(R.id.deletePM);
        Button logoff=(Button)findViewById(R.id.logoffPropMan);
        TextView txt=(TextView)findViewById(R.id.textView4);
        logoff.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                LogOff();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccount();
            }
        });
    }
    public void LogOff(){
        Intent i=new Intent(PropertyManagerView.this,MainActivity.class);
        startActivity(i);
    }
    public void deleteAccount(){
        MainActivity.remove(MainActivity.user);
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
}