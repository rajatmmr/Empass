package com.teaksol.empass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import helper.EmployeeDbAdapter;

public class Signup extends AppCompatActivity {
    EmployeeDbAdapter adapter;
    SharedPreferences sp;
    Button back;
    Button register;
    Switch admin;
    EditText userName;
    EditText empName;
    EditText post;
    EditText mobileNumber;
    EditText password;
    EditText passwordConform;
    EditText admPass;
    Spinner comp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        back = findViewById(R.id.backbtn);
        admin = findViewById(R.id.adminbtn);
        userName = findViewById(R.id.userIdNew);
        empName = findViewById(R.id.nameNew);
        post = findViewById(R.id.postNew);
        mobileNumber = findViewById(R.id.numberNew);
        password = findViewById(R.id.passwordNew);
        passwordConform = findViewById(R.id.repasswordNew);
        admPass = findViewById(R.id.adminPassword);
        comp = findViewById(R.id.company);
        register = findViewById(R.id.sendDatabtn);
        sp= PreferenceManager.getDefaultSharedPreferences(this);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Teaksol");
        categories.add("Wipro");
        categories.add("TCS");
        categories.add("Mindtree");
        categories.add("Sabre");
        categories.add("Google");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        comp.setAdapter(dataAdapter);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (admin.isChecked())
                {
                    admPass.setVisibility(View.VISIBLE);
                }
                else
                {
                    admPass.setVisibility(View.INVISIBLE);
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.getText().toString().equals(passwordConform.getText().toString()))
                {
                    if(admin.isChecked())
                    {
                        if(admPass.getText().toString().equals("Mamamia"))
                        {
                            adapter.signup(convert(userName),convert(password),convert(empName),convert(mobileNumber),convert(post),true,compid(comp));
                        }
                        else
                        {
                            admPass.setText("");
                            Toast toast =   Toast.makeText(getApplicationContext(),"Invalid Admin Password",Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                    else
                    {
                        adapter.signup(convert(userName),convert(password),convert(empName),convert(mobileNumber),convert(post),false,compid(comp));
                    }
                }
                else
                {
                    password.setText("");
                    passwordConform.setText("");
                    Toast toast = Toast.makeText(getApplicationContext(),"Passwords do not Match",Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this ,LoginPage.class);
                startActivity(intent);
            }
        });

    }
    public String convert(EditText text)
    {
        Toast toast = Toast.makeText(getApplicationContext(),text.getText().toString(),Toast.LENGTH_LONG);
        toast.show();
        return text.getText().toString();
    }
    public int compid(Spinner compa)
    {
        int compnum=compa.getSelectedItemPosition();
        return compnum;
    }
}