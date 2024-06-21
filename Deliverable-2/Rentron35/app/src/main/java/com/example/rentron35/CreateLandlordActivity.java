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

public class CreateLandlordActivity extends AppCompatActivity {
    EditText firstName;
    EditText lastName;
    EditText password;
    EditText email;
    EditText streetNumber;
    EditText streetName;
    EditText postalCode;
    EditText aptNumber;
    Button submit;
    UserHelper userHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_landlord);
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
        firstName=findViewById(R.id.editLandlordfirstname);
        lastName=findViewById(R.id.editLandlordlastname);
        password=findViewById(R.id.editLandlordpassword);
        email=findViewById(R.id.editLandlordemail);
        streetName=findViewById(R.id.editLandlordstreetName);
        streetNumber=findViewById(R.id.editLandlordstreetNumber);
        postalCode=findViewById(R.id.editLandlordpostalcode);
        aptNumber=findViewById(R.id.editLandlordaptnumber);
        submit=findViewById(R.id.createlandlordsubmit);
    }
    private void setEventListeners(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate(email.getEditableText().toString())) {
                    UserModel user=new UserModel("Landlord",
                            firstName.getText().toString(),
                            lastName.getText().toString(),
                            email.getText().toString(),
                            password.getText().toString(),
                            "Address");
                    if(userHelper.addUser(user)){
                        MainActivity.user=user;
                        startActivity(new Intent(getApplicationContext(),LandlordActivity.class));
                    }
                    else{
                        Toast.makeText(getApplicationContext(),R.string.errorEmailorPassword,Toast.LENGTH_LONG).show();
                    }
                }
                else{
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