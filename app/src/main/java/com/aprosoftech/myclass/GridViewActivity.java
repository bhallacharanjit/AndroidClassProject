package com.aprosoftech.myclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.HashMap;

public class GridViewActivity extends AppCompatActivity {


    GridView gv_dashboard;
    String[] simpleArray = new String[]{"Add Neta","Edit Neta","View Neta","More Apps"};
    Integer[] imagesArray= new Integer[]{R.mipmap.add_neta,R.mipmap.edit,R.mipmap.view,R.mipmap.more_apps};
    String [] colorsArray = new String[]{"#2196F3","#9E9E9E","#4CAF50","#9C27B0"};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_action_bar_items,menu);
        MenuItem item = menu.findItem(R.id.add);
        item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            Intent intent = new Intent(GridViewActivity.this,AddNeta.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);


        gv_dashboard = (GridView) findViewById(R.id.gv_dashboard);

//        ArrayAdapter arrayAdapter = new ArrayAdapter(GridViewActivity.this,android.R.layout.simple_list_item_1,simpleArray);
//        gv_dashboard.setAdapter(arrayAdapter);


        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();

        for (int i=0;i<simpleArray.length;i++) {
            HashMap<String, Object> tempHashmap = new HashMap<>();
            tempHashmap.put("name",simpleArray[i]);
            tempHashmap.put("image",imagesArray[i]);
            tempHashmap.put("color",colorsArray[i]);


            arrayList.add(tempHashmap);
        }




        GridAdapter adapter = new GridAdapter(GridViewActivity.this,arrayList);
        gv_dashboard.setAdapter(adapter);


    }
}
