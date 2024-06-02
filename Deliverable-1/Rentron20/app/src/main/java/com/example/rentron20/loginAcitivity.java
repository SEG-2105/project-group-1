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
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this,R.array.user_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                MainActivity.user=login(email,password);
                String a=MainActivity.user.getClass().toString();
                if(a.equals(new Client().getClass().toString())){
                    startActivity(new Intent(loginAcitivity.this,ClientActivity.class));
                }else if(a.equals(new Landlord().getClass().toString())){
                    startActivity(new Intent(loginAcitivity.this, LandlordView.class));
                }else if(a.equals(new PropertyManager().getClass().toString())){
                    startActivity(new Intent(loginAcitivity.this,PropertyManagerView.class));
                        }
                else if(a.equals(new Admin().getClass().toString())){
                    startActivity(new Intent(loginAcitivity.this,AdminActivity.class));
                }
                    }
                });
        Button back=(Button)findViewById(R.id.backlogin);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginAcitivity.this,MainActivity.class));
            }
        });
    }
    public User login(EditText email,EditText password){
        TextView tv=(TextView)findViewById(R.id.errorbox);
        try{
            User a=(MainActivity.users.get(MainActivity.checkEmail(email.getEditableText().toString())));
            boolean p=(a.getPassword()).equals(password.getEditableText().toString());
            tv.setText(String.valueOf(p));
            if(p){return a;}
            tv.setText("Your password or email address are incrorrect");
            return new User();

        }catch(Exception e){
            tv.setText("Your password or email address are incorrect");
            return new User();
        }

    }
}