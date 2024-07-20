package com.example.rentron50.activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rentron50.R;
import com.example.rentron50.classes.UserHelper;
import com.example.rentron50.classes.UserModel;

public class MainActivity extends AppCompatActivity {
    Button login, signup, aboutUs;
    EditText email, password;
    UserHelper userHelper;
    public static String eMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        userHelper = new UserHelper(this);
        init();
        setEventListeners();
    }
    protected void onResume(){
        super.onResume();
        MainActivity.eMail=null;
    }
    private void init() {
        login = findViewById(R.id.loginButton);
        signup = findViewById(R.id.signupButton);
        aboutUs = findViewById(R.id.aboutUsButton);
        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
    }

    private void setEventListeners() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login()) {
                    eMail=email.getEditableText().toString();
                    UserModel user=userHelper.getUserModel(eMail);
                    switch (user.getType()) {
                        case "Client":
                            eMail=user.getEmailAddress();
                            startActivity(new Intent(getApplicationContext(),ClientActivity.class));
                            break;
                        case "Landlord":
                            eMail=user.getEmailAddress();
                            startActivity(new Intent(getApplicationContext(), LandlordActivity.class));
                            break;
                        case "PropertyManager":
                            eMail=user.getEmailAddress();
                            startActivity(new Intent(getApplicationContext(), PropertyManagerActivity.class));
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.loginFailure), Toast.LENGTH_LONG).show();
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
            }
        });
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    private boolean login() {
        try{
            return userHelper.login(email.getEditableText().toString(), password.getEditableText().toString());
        }catch (Exception e){
            return false;
        }
    }
    public static void logout(){
        eMail=null;
    }
}