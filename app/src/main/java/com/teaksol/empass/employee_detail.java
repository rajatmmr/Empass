package com.teaksol.empass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import daatamodel.Employee;
import helper.EmployeeDbAdapter;

public class employee_detail extends AppCompatActivity {
    EmployeeDbAdapter adapter;
    Employee employee;
    ImageView qrcode;
    TextView name;
    TextView number;
    TextView post;
    TextView company;
    Boolean admin;
    FloatingActionButton sch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detail);
        employee = new Employee();
        adapter = new EmployeeDbAdapter(this);
        qrcode = findViewById(R.id.Qrplacer);
        name = findViewById(R.id.nameview);
        number=findViewById(R.id.numberView);
        post = findViewById(R.id.postView);
        company=findViewById(R.id.companyView);
        sch=findViewById(R.id.sear);
        Intent caller = getIntent();
        // get the rating passed by the first activity
        int userId = caller.getIntExtra("userId", 0);
        employee=adapter.getEmployeeData(userId);
        String stringName,stringNumber,stringPost,stringCompany;
        stringName = employee.getEmpName();
        stringNumber=employee.getMobNumber();
        stringPost=employee.getPost();
        stringCompany=companys(employee.getCmpId());
        admin=employee.isAdmin();
        name.setText(stringName);
        number.setText(stringNumber);
        post.setText(stringPost);
        company.setText(stringCompany);
        if(admin){
            sch.setVisibility(View.VISIBLE);
        }


    }
    private String companys(int cmp){
        String [] compname = {
                "Teaksol",
                "Wipro",
                "TCS",
                "Mindtree",
                "Sabre",
                "Google"
        };
        return compname[cmp];
    }
}