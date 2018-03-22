package com.aprosoftech.myclass;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class GridViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


    GridView gv_dashboard;
    String[] simpleArray = new String[]{"Add Neta", "Edit Neta", "View Neta", "More Apps"};
    Integer[] imagesArray = new Integer[]{R.mipmap.add_neta,
            R.mipmap.edit,
            R.mipmap.view,
            R.mipmap.more_apps};
    String[] colorsArray = new String[]{"#2196F3", "#9E9E9E", "#4CAF50", "#9C27B0"};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_action_bar_items, menu);
        MenuItem item = menu.findItem(R.id.add);
        item.setVisible(false);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            Intent intent = new Intent(GridViewActivity.this, AddNeta.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.view) {
            Calendar calendar = Calendar.getInstance();
            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH);
            int mDay  = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(GridViewActivity.this,GridViewActivity.this,mYear,mMonth,mDay);
            datePickerDialog.show();
        } else if (item.getItemId() == R.id.delete) {
            Calendar calendar = Calendar.getInstance();
            int mHour = calendar.get(Calendar.HOUR_OF_DAY);
            int mMinutes = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(GridViewActivity.this,GridViewActivity.this,mHour,mMinutes,false);
            timePickerDialog.show();
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(GridViewActivity.this);
        alert.setTitle("Do you really want to quit this awesome app?");
        alert.setMessage("Pressing OK would take this awesome app to background and you will not be able to access features");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                GridViewActivity.this.finish();
            }
        });
        alert.setNegativeButton("NO! I want to stay", null);
        alert.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);


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


        GridAdapter adapter = new GridAdapter(GridViewActivity.this, arrayList);
        gv_dashboard.setAdapter(adapter);
        gv_dashboard.setOnItemClickListener(this);


    }


    public void checkForPermission() {
        if (ContextCompat.
                checkSelfPermission(GridViewActivity.this,
                        Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            //Permission already granted
            Toast.makeText(GridViewActivity.this,"Permission Already Granted",Toast.LENGTH_LONG).show();

        } else {
            String[] permissions = new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.READ_SMS};
            ActivityCompat.requestPermissions
                    (GridViewActivity.this,permissions,123);
        }
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
                Intent intent = new Intent(GridViewActivity.this,AddNeta.class);
                startActivityForResult(intent,1002);
            } break;
            case 1:
            {
                Intent intent = new Intent(GridViewActivity.this,ShowNeta.class);
                startActivityForResult(intent,1003);
            }
        }




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1002) {
            if (resultCode == RESULT_OK) {

                Toast.makeText(GridViewActivity.this,"Neta Added with name: "+data.getExtras().getString("Name"),Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(GridViewActivity.this,"Neta Adding Cancelled",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Toast.makeText(this, i2+"-"+(i1+1)+"-"+i, Toast.LENGTH_SHORT).show();





    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        String time = "";
        String am_pm = "";
        if (i<12) {
            time = ""+i;
            am_pm = "AM";
        } else {
            time = ""+(i-12);
            am_pm = "PM";
        }
        Toast.makeText(this,time+":"+i1+" "+am_pm,Toast.LENGTH_LONG).show();
    }
}
