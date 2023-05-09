package com.example.ciepractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Dashboard extends AppCompatActivity {
    EditText txtDesc,txtFromdate,txtTodate,txtTime;
    Button btnTime, btnFromdate, btnTodate, btnApply;
    TextView head;
    ListView lstLeaves;
    DBHelper db;
    SharedPreferences sharedPreferences;
    ArrayList<String> listItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        txtDesc = findViewById(R.id.txtDesc);
        txtFromdate = findViewById(R.id.txtFromdate);
        txtTodate = findViewById(R.id.txtTodate);
        txtTime = findViewById(R.id.txtTime);
        btnTime = findViewById(R.id.btnTime);
        btnFromdate = findViewById(R.id.btnFromDate);
        btnTodate = findViewById(R.id.btnTodate);
        btnApply = findViewById(R.id.btnApply);
        lstLeaves = findViewById(R.id.lstLeaves);
        db = new DBHelper(this);
        sharedPreferences = getSharedPreferences("myPref",MODE_PRIVATE);
        String uname = sharedPreferences.getString("username",null);
        Toast.makeText(this, "Hello "+uname, Toast.LENGTH_SHORT).show();
        Display();
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = txtDesc.getText().toString();
                String from = txtFromdate.getText().toString();
                String to = txtTodate.getText().toString();
                String time = txtTime.getText().toString();
                Boolean apply = db.leave(uname,description,from,to,time);
                if(apply == true){
                    Toast.makeText(Dashboard.this, "Leave Applied", Toast.LENGTH_SHORT).show();
                    Display();
                }
                else{
                    Toast.makeText(Dashboard.this, "Leave Not Applied", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void Display(){
        sharedPreferences = getSharedPreferences("myPref",MODE_PRIVATE);
        String uname = sharedPreferences.getString("username",null);
        ArrayList<HashMap<String, String>> userList = db.GetUsers(uname);
        ListView lv = (ListView) findViewById(R.id.lstLeaves);
        ListAdapter adapter = new SimpleAdapter(getApplicationContext(), userList, R.layout.lst_leaves,new String[]{"desc","fromdate","todate","time","status"}, new int[]{R.id.lstDesc, R.id.lstFromdate, R.id.lstTodate, R.id.lstTime, R.id.lstStatus});
        lv.setAdapter(adapter);
    }
}