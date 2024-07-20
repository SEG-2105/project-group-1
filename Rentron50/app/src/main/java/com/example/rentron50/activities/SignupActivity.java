package com.example.rentron50.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rentron50.R;
import com.example.rentron50.classes.Address;
import com.example.rentron50.classes.PostalCode;
import com.example.rentron50.classes.UserHelper;
import com.example.rentron50.classes.UserModel;

public class SignupActivity extends AppCompatActivity {
    EditText firstName,lastName,password,birthYear,streetNumber, streetName, postalCode, aptNumber ,email;
    Button signup,back;
    Spinner userType;
    UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        userHelper=new UserHelper(this);
        init();
        setSpinners();
        setEventListeners();
    }
    private void init(){
        firstName=findViewById(R.id.firstNameEditSignup);
        lastName=findViewById(R.id.lastNameEditSignup);
        password=findViewById(R.id.passswordEditSignup);
        birthYear=findViewById(R.id.birthYearEditSignup);
        streetNumber=findViewById(R.id.streetNumberEditSignup);
        streetName=findViewById(R.id.streetNameEditSignup);
        postalCode=findViewById(R.id.postalCodeEditSignup);
        aptNumber=findViewById(R.id.emailAddressEditSignup);
        email=findViewById(R.id.emailAddressEditSignup);

        signup=findViewById(R.id.signupButtonSignup);
        back=findViewById(R.id.backButtonSignup);

        userType=findViewById(R.id.userTypeSpinnerSignup);
    }
    private void setEventListeners(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        userType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                signup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch((String)userType.getItemAtPosition(position)){
                            case "Client":
                                UserModel user = new UserModel((String)userType.getItemAtPosition(position),firstName.getEditableText().toString(),lastName.getEditableText().toString(),
                                        email.getEditableText().toString(),password.getEditableText().toString(),Integer.parseInt(birthYear.getEditableText().toString()));
                                userHelper.addUser(user);
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                break;
                            case "Landlord":
                                Address address=new Address(Integer.parseInt(streetNumber.getEditableText().toString())
                                        ,streetName.getEditableText().toString(),new PostalCode(postalCode.getEditableText().toString()),aptNumber.getEditableText().toString());
                                user = new UserModel((String)userType.getItemAtPosition(position),firstName.getEditableText().toString(),lastName.getEditableText().toString(),
                                        email.getEditableText().toString(),password.getEditableText().toString(),address.toString());
                                userHelper.addUser(user);
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                break;
                            case "PropertyManager":
                                user = new UserModel((String)userType.getItemAtPosition(position),firstName.getEditableText().toString(),lastName.getEditableText().toString(),
                                        email.getEditableText().toString(),password.getEditableText().toString());
                                userHelper.addUser(user);
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                break;
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void setSpinners(){
        ArrayAdapter<CharSequence> userTypeAdapter=ArrayAdapter.createFromResource(
              this,R.array.userType, android.R.layout.simple_spinner_item
        );
        userType.setAdapter(userTypeAdapter);
    }

}