package com.teaksol.empass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import daatamodel.Employee;
import helper.EmployeeDbAdapter;

public class ListActivity extends AppCompatActivity {
    EmployeeDbAdapter adapter;
    Employee employee;
    TextView name;
    TextView userName;
    EditText post;
    EditText mobileNumber;
    Button save;
    Button delete;
    @Override
    protected void onCreate(Bundle savedInstanceData) {
        super.onCreate(savedInstanceData);
        setContentView(R.layout.activity_list);
        Intent caller = getIntent();
        int empId = caller.getIntExtra("empId", 0);
        employee = new Employee();
        adapter = new EmployeeDbAdapter(this);
        name = findViewById(R.id.name);
        userName = findViewById(R.id.userName);
        post = findViewById(R.id.postEdit);
        mobileNumber=findViewById(R.id.numberEdit);
        employee=adapter.getEmployeeData(empId);
        name.setText(employee.getEmpName());
        userName.setText(employee.getUserName());
        post.setText(employee.getPost());
        mobileNumber.setText(employee.getMobNumber());
        save = findViewById(R.id.saveDatabtn);
        delete = findViewById(R.id.DeleteDatabtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String postnew = post.getText().toString();
                String mob = mobileNumber.getText().toString();
                adapter.updatdb(empId,postnew,mob);
                Intent intent = new Intent(ListActivity.this, employee_detail.class);
                intent.putExtra("userId",empId);
                startActivity(intent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.deleteEntry(empId);
                Intent intent = new Intent(ListActivity.this, LoginPage.class);
                startActivity(intent);
            }
        });




    }
}
