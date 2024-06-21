package com.example.rentron35;

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

public class CreateClientActivity extends AppCompatActivity {
    EditText firstName;
    EditText lastName;
    EditText password;
    EditText birthYear;
    EditText email;
    Button submit;
    UserHelper userHelper;
    CharSequence emailError="Email is not valid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_client);
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
        firstName=findViewById(R.id.editclientfirstname);
        lastName=findViewById(R.id.editclientlastname);
        password=findViewById(R.id.editclientpassword);
        email=findViewById(R.id.editclientemail);
        birthYear=findViewById(R.id.editclientyob);
        submit=findViewById(R.id.createClientSubmit);
    }
    private void setEventListeners(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate(email.getEditableText().toString())){
                    UserModel user=new UserModel("Client",firstName.getText().toString(),lastName.getText().toString(),email.getText().toString(),password.getText().toString(),Integer.parseInt(birthYear.getText().toString()));
                    if(userHelper.addUser(user)){
                        startActivity(new Intent(getApplicationContext(),ClientActivity.class));
                    }
                    else{
                        Toast.makeText(getApplicationContext(),R.string.errorEmailorPassword,Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),getString(R.string.errorEmailorPassword),Toast.LENGTH_LONG).show();
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