package com.aprosoftech.myclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    EditText et_username, et_password;
    Button btn_login,btn_sign_up;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_sign_up = (Button) findViewById(R.id.btn_sign_up);


        Log.d("Main2Ativity","onCreate");
        btn_login.setOnClickListener(this);
        btn_sign_up.setOnClickListener(this);


    }


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
    public void onClick(View view) {
        Button btn = (Button) view;
        if (btn.getId() == R.id.btn_login) {
            String username = et_username.getText().toString();
            String password = et_password.getText().toString();


            if (username.equalsIgnoreCase("b") && password.equalsIgnoreCase("1")) {
                Toast.makeText(Main2Activity.this,"Welcome User",Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(Main2Activity.this,"Wrong Uname and Password",Toast.LENGTH_SHORT).show();
            }
        } else if (btn.getId() == R.id.btn_sign_up) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
//            builder.setTitle("Sign Up!");
//            builder.setMessage("We are not taking new signups at this moment we will be back shortly!");
//            builder.setNegativeButton("OK",null);
//            builder.show();


            Intent intent = new Intent(Main2Activity.this,MainActivity.class);
            intent.putExtra("USERNAME",et_username.getText().toString());
            startActivity(intent);



        }
    }









}
