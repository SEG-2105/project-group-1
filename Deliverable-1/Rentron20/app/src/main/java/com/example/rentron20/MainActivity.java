package com.example.rentron20;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
    EditText first_name;
    EditText last_name;
    EditText email_address;
    EditText password;
    Button admin;
    Spinner spinner;
    Button bt3;
    TextView tv;
    UserHelper userHelper;

    public void init(){
        first_name=(EditText)findViewById(R.id.firstname);
        last_name=(EditText)findViewById(R.id.lastname);
        email_address=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        admin=(Button)findViewById(R.id.adminbutton);
        spinner=(Spinner)findViewById(R.id.spinner3);
        bt3=(Button)findViewById(R.id.button3);
        tv=(TextView)findViewById(R.id.textView);
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this,R.array.user_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if(validateAdmin()){
            add(new Admin("admin","admin"));
        }
    }
    public void createClient(int position){
        tv.setText((String) spinner.getItemAtPosition(position));
        user = new Client(first_name.getEditableText().toString(), last_name.getEditableText().toString(), 0, email_address.getEditableText().toString(), password.getEditableText().toString());
        Intent i = new Intent(MainActivity.this, ClientActivity.class);
        add(user);
        startActivity(i);
        tv.setText(user.toString());
    }
    public void createLandlord(int position){
        tv.setText((String) spinner.getItemAtPosition(position));
        user = new Landlord(first_name.getEditableText().toString(), last_name.getEditableText().toString(), email_address.getEditableText().toString(), password.getEditableText().toString());
        add(user);
        Intent j = new Intent(MainActivity.this, LandlordView.class);
        startActivity(j);
    }
    public void createPropertyManager(int position){
        tv.setText((String) spinner.getItemAtPosition(position));
        user = new PropertyManager(first_name.getEditableText().toString(), last_name.getEditableText().toString(), email_address.getEditableText().toString(), password.getEditableText().toString());
        add(user);
        addToUserBase(first_name.getEditableText().toString(), last_name.getEditableText().toString(), email_address.getEditableText().toString());
        Intent k = new Intent(MainActivity.this, PropertyManagerView.class);
        startActivity(k);
    }
    public void addToUserBase(String firstName, String lastName,String emailAddress) {
        UserModel userModel = new UserModel("PropertyManager",firstName, lastName, emailAddress);
        userHelper=new UserHelper(this);
        userHelper.addUser(userModel);
    }
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
        init();
        //Admin button
        admin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i=new Intent(MainActivity.this,AdminActivity.class);
                startActivity(i);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bt3.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        if(validate(first_name,last_name,email_address,password)){
                            switch (position) {
                                case 0: {
                                    createClient(position);
                                    break;
                                }
                                case 1: {
                                    createLandlord(position);
                                    break;
                                }
                                case 2: {
                                    createPropertyManager(position);
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
        boolean a=(firstName.getEditableText().toString().length()<40) & !(firstName.getEditableText().toString().isEmpty());
        boolean b= lastname.getEditableText().toString().length()<40 & !(lastname.getEditableText().toString().isEmpty());
        boolean c=email.getEditableText().toString().contains("@") & email.getEditableText().toString().contains(".") ;
        boolean d=password.getEditableText().toString().length()<40 & !(password.getEditableText().toString().isEmpty());
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