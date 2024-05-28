package com.example.rentron20;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Spinner;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

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
        EditText first_name=(EditText)findViewById(R.id.firstname);
        EditText last_name=(EditText)findViewById(R.id.lastname);
        EditText email_address=(EditText)findViewById(R.id.email);
        EditText password=(EditText)findViewById(R.id.password);


        Spinner spinner=(Spinner)findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this,R.array.user_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Button bt3=(Button)findViewById(R.id.button3);
        TextView tv=(TextView)findViewById(R.id.textView);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bt3.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        switch(position){
                            case 0:
                            {tv.setText((String)spinner.getItemAtPosition(position));
                            new Client(first_name.getEditableText().toString(),last_name.getEditableText().toString(),0,email_address.getEditableText().toString(),password.getEditableText().toString());
                            Intent i=new Intent(MainActivity.this,ClientActivity.class);
                            startActivity(i);
                            break;}
                            case 1:
                            {tv.setText((String)spinner.getItemAtPosition(position));
                            new Landlord(first_name.getEditableText().toString(),last_name.getEditableText().toString(),0,email_address.getEditableText().toString(),password.getEditableText().toString());
                            Intent j=new Intent(MainActivity.this,LandlordView.class);
                            startActivity(j);
                            break;}
                            case 2:
                            {tv.setText((String)spinner.getItemAtPosition(position));
                            new PropertyManager(first_name.getEditableText().toString(),last_name.getEditableText().toString(),email_address.getEditableText().toString(),password.getEditableText().toString());
                            Intent k=new Intent(MainActivity.this,PropertyManagerView.class);
                            startActivity(k);
                            break;}

                        }

                    }
                });
                Button login=(Button)findViewById(R.id.login);
                login.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                       startActivity(new Intent(MainActivity.this,loginAcitivity.class));
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    public boolean isEmpty(){
        return true;
    }
}