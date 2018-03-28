package com.aprosoftech.myclass;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    GridView gv_dashboard;
    String[] simpleArray = new String[]{"Add Neta", "Edit Neta", "View Neta", "More Apps"};
    Integer[] imagesArray = new Integer[]{R.mipmap.add_neta,
            R.mipmap.edit,
            R.mipmap.view,
            R.mipmap.more_apps};
    String[] colorsArray = new String[]{"#2196F3", "#9E9E9E", "#4CAF50", "#9C27B0"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        gv_dashboard = (GridView) findViewById(R.id.gv_dashboard);

//        ArrayAdapter arrayAdapter = new ArrayAdapter(GridViewActivity.this,android.R.layout.simple_list_item_1,simpleArray);
//        gv_dashboard.setAdapter(arrayAdapter);

        checkForPermission();

        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();

        for (int i = 0; i < simpleArray.length; i++) {
            HashMap<String, Object> tempHashmap = new HashMap<>();

            try {
                tempHashmap.put("name", simpleArray[i]);
                tempHashmap.put("image", imagesArray[i]);
                tempHashmap.put("color", colorsArray[i]);

                Log.d("TEMP HASHMAP", tempHashmap.toString());
                arrayList.add(tempHashmap);

            } catch (Exception ae) {
                Log.e("Exception raised", ae.getMessage());
            } finally {

            }

        }


        GridAdapter adapter = new GridAdapter(DashboardActivity.this, arrayList);
        gv_dashboard.setAdapter(adapter);
        gv_dashboard.setOnItemClickListener(this);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == 123) {
            Log.d("Length",""+permissions.length);
            for (int i=0;i<permissions.length;i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Permissions",permissions[i]+"is granted");
                } else {
                    Log.d("Permissions",permissions[i]+"is denied");
                }
            }
        }


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        try {
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse("http://www.google.com"));
//            startActivity(intent);
//        } catch (Exception ae) {
//            Toast.makeText(GridViewActivity.this,"Some error occured",Toast.LENGTH_LONG).show();
//        }

        switch (i) {
            case 0:
            {
                Intent intent = new Intent(DashboardActivity.this,AddNeta.class);
                startActivityForResult(intent,1002);
            } break;
            case 1:
            {
                Intent intent = new Intent(DashboardActivity.this,ShowNeta.class);
                startActivityForResult(intent,1003);
            }
        }




    }



    public void checkForPermission() {
        if (ContextCompat.
                checkSelfPermission(DashboardActivity.this,
                        android.Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            //Permission already granted
            Toast.makeText(DashboardActivity.this,"Permission Already Granted",Toast.LENGTH_LONG).show();

        } else {
            String[] permissions = new String[]{android.Manifest.permission.CALL_PHONE, android.Manifest.permission.READ_SMS};
            ActivityCompat.requestPermissions
                    (DashboardActivity.this,permissions,123);
        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent intent = new Intent(DashboardActivity.this,AddNeta.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(DashboardActivity.this,ShowNeta.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
