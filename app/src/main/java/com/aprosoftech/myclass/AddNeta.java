package com.aprosoftech.myclass;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AddNeta extends AppCompatActivity implements View.OnClickListener {


    EditText et_name, et_party, et_city;
    Button btn_save;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_neta);


        et_name = (EditText) findViewById(R.id.et_name);
        et_city = (EditText) findViewById(R.id.et_city);
        et_party = (EditText) findViewById(R.id.et_party);

        btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String name = et_name.getText().toString();
        String party = et_party.getText().toString();
        String city = et_city.getText().toString();


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Name",name);
            jsonObject.put("City",city);
            jsonObject.put("Party",party);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "https://api.backendless.com/65C6BAD1-C8A1-91BF-FFDF-0803DE39EE00/0B0003D1-E1FB-A85B-FFC9-634D746D3100/data/MLAs";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response",response.toString());
                progressDialog.dismiss();
                Toast.makeText(AddNeta.this,"Data Saved",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error","Err"+error.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(AddNeta.this,"Error is there!",Toast.LENGTH_LONG).show();
            }
        });

        progressDialog = new ProgressDialog(AddNeta.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Loading !");
        progressDialog.setMessage("Sending data to server");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(AddNeta.this);
        requestQueue.add(jsonObjectRequest);




    }
}
