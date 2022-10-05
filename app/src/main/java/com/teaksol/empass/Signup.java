package com.teaksol.empass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class Signup extends AppCompatActivity {
    Button back;
    Switch admin;
    TextInputLayout admPass;
    Spinner comp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        back = findViewById(R.id.backbtn);
        admin = findViewById(R.id.adminbtn);
        admPass = findViewById(R.id.adminPassword);
        comp = findViewById(R.id.company);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Item 1");
        categories.add("Item 2");
        categories.add("Item 3");
        categories.add("Item 4");
        categories.add("Item 5");
        categories.add("Item 6");

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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this ,LoginPage.class);
                startActivity(intent);
            }
        });
    }
}