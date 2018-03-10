package com.aprosoftech.myclass;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ModifyNeta extends AppCompatActivity implements View.OnClickListener {

    EditText et_name, et_party, et_city;
    Button btn_save,btn_local_save,btn_delete;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_neta);

        et_name = (EditText) findViewById(R.id.et_name);
        et_city = (EditText) findViewById(R.id.et_city);
        et_party = (EditText) findViewById(R.id.et_party);


        String selectedNeta = getIntent().getExtras().getString("SELECTED_NETA");
        try {
            jsonObject = new JSONObject(selectedNeta);
            et_name.setText(jsonObject.getString("Name"));
            et_city.setText(jsonObject.getString("City"));
            et_party.setText(jsonObject.getString("Party"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btn_save = (Button) findViewById(R.id.btn_save);
        btn_local_save = (Button) findViewById(R.id.btn_local_save);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_save.setOnClickListener(this);
        btn_local_save.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
//objectId

        Button btn_clicked = (Button) view;
        if (btn_clicked.getId() == R.id.btn_save) {

            String objId = "";
            try {
                objId = jsonObject.getString("objectId");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            String name = et_name.getText().toString();
            String party = et_party.getText().toString();
            String city = et_city.getText().toString();


            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Name", name);
                jsonObject.put("City", city);
                jsonObject.put("Party", party);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            String url = "https://api.backendless.com/65C6BAD1-C8A1-91BF-FFDF-0803DE39EE00/0B0003D1-E1FB-A85B-FFC9-634D746D3100/data/MLAs/" + objId;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(ModifyNeta.this, "Saved!", Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ModifyNeta.this, "Not Saved!", Toast.LENGTH_LONG).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(ModifyNeta.this);
            requestQueue.add(jsonObjectRequest);

        }
        else if (btn_clicked.getId() == R.id.btn_local_save) {
            SharedPreferences.Editor editor = getSharedPreferences("MyClassPrefs",MODE_PRIVATE).edit();
            editor.putString("Neta_Name",et_name.getText().toString());
            editor.apply();
        } else if (btn_clicked.getId() == R.id.btn_delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ModifyNeta.this);
            builder.setTitle("Confirmation");
            builder.setMessage("Are you sure you want to delete?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i == DialogInterface.BUTTON_POSITIVE) {
                        callDeleteService();
                    }
                }
            });
            builder.setNegativeButton("NO",null);
            builder.show();
        }


    }




    public void callDeleteService() {
        String objId = "";
        try {
            objId = jsonObject.getString("objectId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "https://api.backendless.com/65C6BAD1-C8A1-91BF-FFDF-0803DE39EE00/0B0003D1-E1FB-A85B-FFC9-634D746D3100/data/MLAs/"+objId;
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(ModifyNeta.this,"Deleted Record!",Toast.LENGTH_LONG).show();
                ModifyNeta.this.finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ModifyNeta.this,"Error in deleting Record!",Toast.LENGTH_LONG).show();
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(ModifyNeta.this);
        requestQueue.add(stringRequest);
    }
}
