package com.example.ciepractice;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText txtUsername,txtPassword;
    TextView login;
    Button btnLogin;
    SharedPreferences sharedPreferences;
    DBHelper db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        login = findViewById(R.id.textView);
        btnLogin = findViewById(R.id.btnLogin);
        db = new DBHelper(this);
        sharedPreferences = getSharedPreferences("myPref",MODE_PRIVATE);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                if(username.equals("admin") && password.equals("12345")){
                    Toast.makeText(MainActivity.this, "Hello Admin", Toast.LENGTH_SHORT).show();
                    Intent admin = new Intent(getApplicationContext(),Admin.class);
                    startActivity(admin);
                }
                else{
                    Boolean check = db.checkDetail(username,password);
                    if(check == true){
                        Toast.makeText(MainActivity.this, "User Already Found", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username",username.toString());
                        editor.apply();
                        Intent login = new Intent(getApplicationContext(),Dashboard.class);
                        startActivity(login);
                    }
                    else{
                        Boolean insert = db.insert(username,password);
                        if(insert == true){
                            Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("username",username.toString());
                            editor.apply();
                            Intent register = new Intent(getApplicationContext(),Dashboard.class);
                            startActivity(register);
                        }
                    }
                }
            }
        });
    }
}