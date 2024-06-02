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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static User user;
    public static ArrayList<User> users=new ArrayList<User>();

    public static int checkEmail(String email) {
        int i=0;
        for(User u:users){
            if(u.getEmailAddress().equals(email)){
                return i;
            }
            else{
                i++;
            }
        }
        return -1;
    }

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
        Button admin=(Button)findViewById(R.id.adminbutton);
        if(validateAdmin()){
            add(new Admin("admin","admin"));
        }
        admin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i=new Intent(MainActivity.this,loginAcitivity.class);
                startActivity(i);
            }
        });
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
                        if(validate(first_name,last_name,email_address,password)){
                            switch (position) {
                                case 0: {
                                    tv.setText((String) spinner.getItemAtPosition(position));
                                    user = new Client(first_name.getEditableText().toString(), last_name.getEditableText().toString(), 0, email_address.getEditableText().toString(), password.getEditableText().toString());
                                    Intent i = new Intent(MainActivity.this, ClientActivity.class);
                                    add(user);
                                    startActivity(i);
                                    tv.setText(user.toString());
                                    break;
                                }
                                case 1: {
                                    tv.setText((String) spinner.getItemAtPosition(position));
                                    user = new Landlord(first_name.getEditableText().toString(), last_name.getEditableText().toString(), 0, email_address.getEditableText().toString(), password.getEditableText().toString());
                                    add(user);
                                    Intent j = new Intent(MainActivity.this, LandlordView.class);
                                    startActivity(j);
                                    break;
                                }
                                case 2: {
                                    tv.setText((String) spinner.getItemAtPosition(position));
                                    user = new PropertyManager(first_name.getEditableText().toString(), last_name.getEditableText().toString(), email_address.getEditableText().toString(), password.getEditableText().toString());
                                    add(user);
                                    Intent k = new Intent(MainActivity.this, PropertyManagerView.class);
                                    startActivity(k);
                                    break;
                                }
                            }
                        }else{
                            tv.setText("One or more of your inputs are wrong");
                        }

                    }
                });
                Button login=(Button)findViewById(R.id.login);
                login.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        try{
                            startActivity(new Intent(MainActivity.this,loginAcitivity.class));
                        }catch(Exception e){

                        }

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
    public boolean validate(EditText firstName,EditText lastname,EditText email,EditText password){
        boolean a=firstName.getEditableText().toString().length()<40;
        boolean b= lastname.getEditableText().toString().length()<40;
        boolean c=email.getEditableText().toString().contains("@") & email.getEditableText().toString().contains(".") ;
        boolean d=password.getEditableText().toString().length()<40;
        boolean e=checkEmail(email.getEditableText().toString())==(-1);
        return (a & b & c & d & e);
    }

    public static User getUser() {
        return user;
    }
    public static void resetUser(){
        user=null;
    }
    public static void add(User p){
        try{
            users.add(p);
        }catch(Exception e){

        }
    }
    public static void remove(User p){
        try{
            users.remove(p);
        }catch(Exception e){

        }

    }
    public boolean validateAdmin(){
        for(User u:users){
            if(u.getEmailAddress().equals(new Admin("admin","admin").getEmailAddress())){
                return false;
            }
        }
        return true;
    }
    public static String usersToString(){
       String temp="";
       for(User a:users){
           temp=temp+a.toString()+" "+a.getClass()+"\n";
       }
       return temp;
    }
}