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
        TextView tv=(TextView)findViewById(R.id.welcometextview);
        tv.setText("We welcome you ,"+user.getFirstName());
        Button logoff=(Button)findViewById(R.id.logoutclient);
        logoff.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                MainActivity.resetUser();
                startActivity(new Intent(ClientActivity.this,MainActivity.class));
            }
        });
    }
    public void deleteAccount(){
        MainActivity.remove(MainActivity.getUser());
    }
}