package com.aprosoftech.myclass;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String myName;
    TextView tv_myName;

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


        Log.d("Main2Activity","onCreate");
        tv_myName = findViewById(R.id.tv_name);
        myName = "Charanjit Singh";

//        tv_myName.setText(myName);
    }
}
