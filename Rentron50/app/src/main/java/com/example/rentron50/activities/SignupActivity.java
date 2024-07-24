package com.example.rentron50.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
    EditText firstName,lastName,password,birthYear,email,
            streetNumber, streetName, postalCode, aptNumber,city,country;
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
        aptNumber=findViewById(R.id.aptNumberEditSignup);
        email=findViewById(R.id.emailAddressEditSignup);
        country=findViewById(R.id.countryEditSignup);
        city=findViewById(R.id.cityEditSignup);


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
                setVisibility(position);
                signup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(validate(position)){
                                switch((String)userType.getItemAtPosition(position)){
                                    case "Client":
                                        UserModel user = new UserModel((String)userType.getItemAtPosition(position),firstName.getEditableText().toString(),lastName.getEditableText().toString(),
                                                email.getEditableText().toString(),password.getEditableText().toString(),Integer.parseInt(birthYear.getEditableText().toString()));
                                        userHelper.addUser(user);
                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                        break;
                                    case "Landlord":
                                        Address address=new Address(Integer.parseInt(streetNumber.getEditableText().toString())
                                                ,streetName.getEditableText().toString(),new PostalCode(postalCode.getEditableText().toString())
                                                ,aptNumber.getEditableText().toString(),city.getEditableText().toString(),country.getEditableText().toString());
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
                        else{
                            Toast.makeText(SignupActivity.this, "Make sure all fields are filled correctly", Toast.LENGTH_SHORT).show();
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
    private boolean validate(int type){
        boolean ba;
        boolean a=firstName.getEditableText().toString().isEmpty() || lastName.getEditableText().toString().isEmpty() || password.getEditableText().toString().isEmpty() || email.getEditableText().toString().isEmpty();
        if(!birthYear.getEditableText().toString().isEmpty()){
            ba=Integer.parseInt(birthYear.getEditableText().toString())>(2024-18)|| Integer.parseInt(birthYear.getEditableText().toString())<(2024-125);
        }
        else{
            ba=false;
        }

        boolean b=birthYear.getEditableText().toString().isEmpty();
        boolean c=streetName.getEditableText().toString().isEmpty() || streetNumber.getEditableText().toString().isEmpty()
                || postalCode.getEditableText().toString().isEmpty() || city.getEditableText().toString().isEmpty()
                || country.getEditableText().toString().isEmpty();
        switch (type) {
            case 0:
                return !(a || b || ba);
            case 1:
                return !(a || c);
            case 2:
                return !a;
        }
        return false;
    }
    private void setVisibility(int type){
        switch(type){
            case 0:
                birthYear.setVisibility(View.VISIBLE);
                streetName.setVisibility(View.INVISIBLE);
                streetNumber.setVisibility(View.INVISIBLE);
                postalCode.setVisibility(View.INVISIBLE);
                aptNumber.setVisibility(View.INVISIBLE);
                country.setVisibility(View.INVISIBLE);
                city.setVisibility(View.INVISIBLE);
                break;
            case 1:
                birthYear.setVisibility(View.INVISIBLE);
                streetName.setVisibility(View.VISIBLE);
                streetNumber.setVisibility(View.VISIBLE);
                postalCode.setVisibility(View.VISIBLE);
                aptNumber.setVisibility(View.VISIBLE);
                country.setVisibility(View.VISIBLE);
                city.setVisibility(View.VISIBLE);
                break;
            case 2:
                birthYear.setVisibility(View.INVISIBLE);
                streetName.setVisibility(View.INVISIBLE);
                streetNumber.setVisibility(View.INVISIBLE);
                postalCode.setVisibility(View.INVISIBLE);
                aptNumber.setVisibility(View.INVISIBLE);
                country.setVisibility(View.INVISIBLE);
                city.setVisibility(View.INVISIBLE);
        }
    }


}