package com.example.rentron20;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class loginAcitivity extends AppCompatActivity {
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
        EditText email=(EditText)findViewById(R.id.editemail);
        EditText password=(EditText)findViewById(R.id.editpassword);
        Button login=(Button)findViewById(R.id.loginlog);
        Spinner spinner=(Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this,R.array.user_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Button login=(Button)findViewById(R.id.loginlog);
                login.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        switch(position){
                            case 0:
                                login(email,password);
                                startActivity(new Intent(loginAcitivity.this,ClientActivity.class));
                                break;
                            case 1:
                                login(email,password);
                                startActivity(new Intent(loginAcitivity.this, LandlordView.class));
                            case 2:
                                login(email,password);
                                startActivity(new Intent(loginAcitivity.this,PropertyManagerView.class));
                        }
                    }

                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public boolean login(EditText email,EditText password){
        TextView tv=findViewById(R.id.errorbox);
        try{
            User a=(MainActivity.users.get(MainActivity.checkEmail(email.getEditableText().toString())));
            boolean p=(a.getPassword()).equals(password.getEditableText().toString());
            tv.setText(String.valueOf(p));
            return p;

        }catch(Exception e){
            tv.setText("Your password or email address are incorrect");
            return false;
        }

    }
}