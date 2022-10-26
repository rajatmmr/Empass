package com.teaksol.empass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

import daatamodel.Employee;
import helper.EmployeeDbAdapter;

public class searchAct extends AppCompatActivity {
    RecyclerView recyclerView;
    EmployeeDbAdapter adapter;
    String employe[];
    ArrayList employee;
    int cmpid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        employee = adapter.searchCompany(cmpid);
        adapter = new EmployeeDbAdapter(this);
        recyclerView=findViewById(R.id.recycleView);
        adapter.searchCompany(cmpid);
        Intent caller = getIntent();
        int userId = caller.getIntExtra("cmpId", 0);
    }
}