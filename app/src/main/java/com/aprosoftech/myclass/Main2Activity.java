package com.aprosoftech.myclass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    EditText et_username, et_password;
    Button btn_login,btn_sign_up;
    CheckBox cb_terms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_sign_up = (Button) findViewById(R.id.btn_sign_up);
        cb_terms = (CheckBox) findViewById(R.id.cb_terms);


        Log.d("Main2Ativity","onCreate");
        btn_login.setOnClickListener(this);
        btn_sign_up.setOnClickListener(this);


        SharedPreferences sharedPreferences =
                Main2Activity.this.getSharedPreferences
                        ("CLASSPREF",MODE_PRIVATE);

        if (sharedPreferences.contains("USER")) {
            Intent intent = new Intent(Main2Activity.this,MainActivity.class);
            String jsonString  = sharedPreferences.getString("USER","");
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                intent.putExtra("USERNAME",jsonObject.getString("email"));
                startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else {

        }




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


            if (cb_terms.isChecked()) {

            } else {
                Toast.makeText(Main2Activity.this,"Please agree to terms and conditions",Toast.LENGTH_LONG).show();
                return;
            }


            String url = "https://api.backendless.com/65C6BAD1-C8A1-91BF-FFDF-0803DE39EE00/0B0003D1-E1FB-A85B-FFC9-634D746D3100/data/user?where=email%3D'"+username+"'%20and%20password%3D'"+password+"'";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        if (jsonArray.length() > 0) {
                            //LOGIN SUCCESSFUL
                            SharedPreferences sharedPreferences =
                                    Main2Activity.this.getSharedPreferences
                                            ("CLASSPREF",MODE_PRIVATE);
                            SharedPreferences.Editor editor= sharedPreferences.edit();


                            editor.putString("USER",jsonArray.getJSONObject(0).toString());
                            editor.apply();


                            Intent intent = new Intent(Main2Activity.this,MainActivity.class);
                            intent.putExtra("USERNAME",et_username.getText().toString());
                            startActivity(intent);

                        }else {
                            //NOT A SUCCESSFUL LOGIN
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(Main2Activity.this);
            requestQueue.add(stringRequest);
//            if (username.equalsIgnoreCase("b") && password.equalsIgnoreCase("1")) {
//                Toast.makeText(Main2Activity.this,"Welcome User",Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(Main2Activity.this,"Wrong Uname and Password",Toast.LENGTH_SHORT).show();
//            }
        } else if (btn.getId() == R.id.btn_sign_up) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
//            builder.setTitle("Sign Up!");
//            builder.setMessage("We are not taking new signups at this moment we will be back shortly!");
//            builder.setNegativeButton("OK",null);
//            builder.show();





        }
    }









}
