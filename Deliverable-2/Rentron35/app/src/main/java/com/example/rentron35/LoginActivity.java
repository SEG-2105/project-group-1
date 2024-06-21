package com.example.rentron35;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button login;
    UserHelper userHelper;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        userHelper=new UserHelper(this);
        init();
        setEventListeners();
    }
    private void init(){
        email=findViewById(R.id.editEmailLoginUser);
        password=findViewById(R.id.editPasswordLoginUser);
        login=findViewById(R.id.btnLogInUser);
        tv=findViewById(R.id.textView2);
    }
    private void setEventListeners(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(userHelper.getUserModel(email.getEditableText().toString()).toString());
                if(userHelper.login(email.getEditableText().toString(),password.getEditableText().toString())){
                    UserModel user=userHelper.getUserModel(email.getEditableText().toString());
                    Toast.makeText(getApplicationContext(), "text", Toast.LENGTH_LONG).show();
                    if(user.getType().equals("Client")){
                        MainActivity.user=user;
                        startActivity(new Intent(getApplicationContext(),ClientActivity.class));
                    }
                    if(user.getType().equals("Landlord")){
                        MainActivity.user=user;
                        startActivity(new Intent(getApplicationContext(),LandlordActivity.class));
                    }
                    if(user.getType().equals("Property Manager")){
                        MainActivity.user=user;
                        startActivity(new Intent(getApplicationContext(),PropertyManagerActivity.class));
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Something was wrong",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}