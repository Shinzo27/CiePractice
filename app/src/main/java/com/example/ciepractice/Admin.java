package com.example.ciepractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        DBHelper db = new DBHelper(this);
        ArrayList<HashMap<String, String>> userLeaves = db.GetData();
        ListView lv = (ListView) findViewById(R.id.admin_leaves);
        ListAdapter adapter = new SimpleAdapter(getApplicationContext(),userLeaves,R.layout.admin_lst,new String[]{"username,desc,fromdate,todate,time,status"},new int[]{R.id.adminUsername,R.id.adminDesc,R.id.adminFrom,R.id.adminTo,R.id.adminTime,R.id.spinnerStatus});
        lv.setAdapter(adapter);
    }
}