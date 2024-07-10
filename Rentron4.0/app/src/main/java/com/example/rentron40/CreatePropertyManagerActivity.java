package com.example.rentron40;

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

public class CreatePropertyManagerActivity extends AppCompatActivity {
    UserHelper userHelper;
    EditText firstname,lastname,email,password;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_property_manager);
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
        firstname=findViewById(R.id.editPropertyManagerfirstname);
        lastname=findViewById(R.id.editPropertyManagerlastname);
        email=findViewById(R.id.editPropertyManageremail);
        password=findViewById(R.id.editPropertyManagerpassword);
        signup=findViewById(R.id.btnPropertyManagersignup);
    }
    private void setEventListeners(){
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate(email.getEditableText().toString())){
                    UserModel user=new UserModel("PropertyManager",firstname.getText().toString(),lastname.getText().toString(),email.getText().toString(),password.getText().toString());
                    if(userHelper.addUser(user)){
                        startActivity(new Intent(getApplicationContext(),PropertyManagerActivity.class));
                    }
                    else{
                        Toast.makeText(getApplicationContext(),R.string.errorEmailorPassword,Toast.LENGTH_LONG).show();
                    }
                } else{
                    Toast.makeText(getApplicationContext(),R.string.errorEmailorPassword,Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    private boolean validate(String email){
        /*
        For now this is all the validation for the email that will be done. In later releases of the application it is
        probable that checking if the email actually is valid will be the best recourse
         */
        String at="@";
        String dot=".";
        return email.contains(at) && email.contains(dot);
    }


}