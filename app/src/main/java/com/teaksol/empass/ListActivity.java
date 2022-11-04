package com.teaksol.empass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import daatamodel.Employee;
import helper.EmployeeDbAdapter;

public class ListActivity extends AppCompatActivity {
    EmployeeDbAdapter adapter;
    ListView sp;
    ArrayAdapter<String> aa;
    @Override
    protected void onCreate(Bundle savedInstanceData) {
        super.onCreate(savedInstanceData);
        setContentView(R.layout.activity_list);
        Intent caller = getIntent();
        // get the rating passed by the first activity
        int cmpId = caller.getIntExtra("cmpId", 0);
        adapter = new EmployeeDbAdapter(this);
        ArrayList<Employee> emps = adapter.searchCompany(cmpId);
        sp=(ListView)findViewById(R.id.lv);
        String empNames[] = new String[emps.size()];
        int i = 0;
        for(Employee emp: emps) {
            empNames[i] = emp.getEmpName();
        }
        aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,empNames);
        sp.setAdapter(aa);
    }
}
