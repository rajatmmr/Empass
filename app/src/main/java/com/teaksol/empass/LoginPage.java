package com.teaksol.empass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import daatamodel.Employee;
import helper.EmployeeDbAdapter;

public class LoginPage extends AppCompatActivity {
    EditText userName;
    EditText password;
    Button signup;
    Button login;
    Button forgot;
    EmployeeDbAdapter adapter;
    Employee employee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        userName = findViewById(R.id.userId);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.registerbtn);
        login = findViewById(R.id.loginbtn);
        forgot = findViewById(R.id.forgetbtn);
        adapter = new EmployeeDbAdapter(this);
        employee = new Employee();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this ,Signup.class);
                startActivity(intent);
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=userName.getText().toString();
                if(name.isEmpty())
                {
                    Toast toast =   Toast.makeText(getApplicationContext(),"Empty Username",Toast.LENGTH_LONG);
                    toast.show();
                }
                else if (adapter.getEmpid(name)==-1)
                {
                    Toast toast =   Toast.makeText(getApplicationContext(),"Invalid Username",Toast.LENGTH_LONG);
                    toast.show();
                }
                else
                {
                    int userid= adapter.getEmpid(name);
                    employee = adapter.getEmployeeData(userid);
                    String number = employee.getMobNumber();
                    String pass = adapter.getPassword(userid);
                    String message ="Your password is:"+pass;
                    try {
                        SmsManager smsManager= SmsManager.getDefault();
                        smsManager.sendTextMessage(number,null,message,null,null);
                        Toast.makeText(getApplicationContext(),"Message Sent to Your Moblie Number",Toast.LENGTH_LONG).show();
                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Some fiedls is Empty",Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = userName.getText().toString();
                String pass = password.getText().toString();
                if (adapter.validateLogin(user,pass) == -1|| adapter.validateLogin(user,pass)==-2||user.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Invalid UserName or Password",Toast.LENGTH_LONG).show();
                }
                else
                {
                    int userid = adapter.getEmpid(user);
                    Intent intent = new Intent(LoginPage.this, employee_detail.class);
                    intent.putExtra("userId",userid);
                    startActivity(intent);
                }
            }
        });

    }
}