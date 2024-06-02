package com.example.rentron20;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LandlordView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_landlord_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText streetNumber=(EditText) findViewById(R.id.streetNumber);
        EditText streetName=(EditText) findViewById(R.id.streetName);
        EditText postalCode=(EditText) findViewById(R.id.postalCode);
        EditText aptNumber=(EditText)findViewById(R.id.aptNumber);
        Button delete=(Button)findViewById(R.id.deleteLandlord);
        Button logoff=(Button)findViewById(R.id.logofflandlord);
        TextView txt=(TextView)findViewById(R.id.textView3);
        Button setAddress=(Button)findViewById(R.id.setAddress);
        TextView errorAddress=(TextView) findViewById(R.id.erroryear);
        logoff.setVisibility(View.INVISIBLE);
        txt.setVisibility(View.INVISIBLE);
        delete.setVisibility(View.INVISIBLE);
        if((((Landlord)MainActivity.user).getAddress()==null)){
            setAddress.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    if(setAddress(streetName,streetNumber,postalCode,aptNumber)){
                        streetNumber.setVisibility(View.GONE);
                        streetName.setVisibility(View.GONE);
                        aptNumber.setVisibility(View.GONE);
                        postalCode.setVisibility(View.GONE);
                        setAddress.setVisibility(View.GONE);
                        errorAddress.setVisibility(View.GONE);
                        logoff.setVisibility(View.VISIBLE);
                        txt.setVisibility(View.VISIBLE);
                        delete.setVisibility(View.VISIBLE);
                        }
                    else{
                        errorAddress.setText("Something is missing from your address");
                    }
                }
            });
        }else{
            streetNumber.setVisibility(View.GONE);
            streetName.setVisibility(View.GONE);
            aptNumber.setVisibility(View.GONE);
            postalCode.setVisibility(View.GONE);
            setAddress.setVisibility(View.GONE);
            errorAddress.setVisibility(View.GONE);
            logoff.setVisibility(View.VISIBLE);
            txt.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
        }
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccount();
            }
        });
        logoff.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                LogOff();
            }
        });
    }
    public boolean validate(EditText name,EditText number,EditText pcode){
        boolean a=!(name.getEditableText().toString().isEmpty());
        boolean b=!(number.getEditableText().toString().isEmpty());
        boolean c=!(pcode.getEditableText().toString().isEmpty());
        return (a & b & c);
    }
    public boolean setAddress(EditText name, EditText number, EditText pcode, EditText apt){
        try{
            if(validate(name,number,pcode)){
                Address add=new Address(Integer.parseInt(number.getEditableText().toString()),name.getEditableText().toString(),new PostalCode(pcode.getEditableText().toString()),apt.getEditableText().toString());
                ((Landlord)MainActivity.user).setAddress(add);
                return true;
            }
            else{
                return false;
            }
        }catch(Exception e){
            return false;
        }
    }
    public int toInteger(EditText year){
        try{
            return Integer.parseInt(year.getEditableText().toString());
        }catch(Exception e){
            return 0;
        }
    }
    public void LogOff(){
        Intent i=new Intent(LandlordView.this,MainActivity.class);
        startActivity(i);
    }
    public void deleteAccount(){
        MainActivity.remove(MainActivity.user);
        LogOff();
    }
}