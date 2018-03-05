package com.aprosoftech.myclass;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String myName;
    TextView tv_myName;
    Spinner sp_names;
    Button btn_1;

    String[] names = new String[]{"Name 1", "Name 2", "Name 3", "My Name"};

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Main2Ativity","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Main2Ativity","onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Main2Ativity","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Main2Ativity","onStop");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Main2Ativity","onDestroy");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toast.makeText(MainActivity.this,getIntent().getExtras().getString("USERNAME"),Toast.LENGTH_LONG).show();

        sp_names = (Spinner) findViewById(R.id.sp_names);

        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_1.setOnClickListener(this);


        HashMap hashMap = new HashMap();
        hashMap.put("student","sdfkashgjjsafgl;dfkgl");
        hashMap.put("water","sdjhgsdfhjbsdfjhlbksfg;'h");
        hashMap.put("alphabet","klsadghj;adfhjkgadfklgnkladfn");


        String hh = hashMap.get("key1").toString();


        HashMap<Integer,String> hashMap1 = new HashMap<>();
        hashMap1.put(1,"test");

        String hh1 = hashMap1.get(1);




        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_spinner_item,names);
        sp_names.setAdapter(arrayAdapter);

        Log.d("Main2Activity","onCreate");
        tv_myName = findViewById(R.id.tv_name);
        myName = "Charanjit Singh";

//        tv_myName.setText(myName);
    }

    @Override
    public void onClick(View view) {
        Button btn = (Button) view;
        if (btn.getId() == R.id.btn_1) {
            int selected_position = sp_names.getSelectedItemPosition();
            Toast.makeText(MainActivity.this,names[selected_position],Toast.LENGTH_LONG).show();
        }
    }
}
