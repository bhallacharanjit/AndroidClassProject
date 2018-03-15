package com.aprosoftech.myclass;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class GridViewActivity extends AppCompatActivity {


    GridView gv_dashboard;
    String[] simpleArray = new String[]{"S1","S2","S3","S4","S5"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);


        gv_dashboard = (GridView) findViewById(R.id.gv_dashboard);


        ArrayAdapter arrayAdapter = new ArrayAdapter(GridViewActivity.this,android.R.layout.simple_list_item_1,simpleArray);
        gv_dashboard.setAdapter(arrayAdapter);
    }
}
