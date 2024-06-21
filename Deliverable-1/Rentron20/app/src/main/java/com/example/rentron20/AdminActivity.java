package com.example.rentron20;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.util.List;

public class AdminActivity extends AppCompatActivity {
    Button logoff;
    private ListView users_listView;
    private UserHelper userhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        userhelper=new UserHelper(this);
        init();
        updateDisplayedUsers();
        setEventListeners();
    }
    public void addUser(UserModel userModel){
        userhelper.addUser(userModel);
        updateDisplayedUsers();
    }
    private void updateDisplayedUsers() {
        List<UserModel> users=userhelper.getAllUsers();
        updateDisplayedUsers(users);
    }
    private void updateDisplayedUsers(List<UserModel> users) {
        ArrayAdapter<UserModel> adapter =new ArrayAdapter<>(users_listView.getContext(),
                android.R.layout.simple_list_item_1,users);
        users_listView.setAdapter(adapter);
    }

    protected void init(){
        logoff=findViewById(R.id.logoffAdmin);
        users_listView=findViewById(R.id.userlist);
    }
    protected void setEventListeners(){
        logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this,MainActivity.class));
            }
        });
    }
}