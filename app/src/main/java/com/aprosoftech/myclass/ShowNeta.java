package com.aprosoftech.myclass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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

import java.util.ArrayList;

public class ShowNeta extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {


    ListView lv_Netas;
    JSONArray jsonArray;
    Button btn_val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_neta);


        lv_Netas = (ListView) findViewById(R.id.lv_netas);
        btn_val = (Button) findViewById(R.id.btn_val);
        btn_val.setOnClickListener(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        callGETService();
    }


    //FOR CALLING GET SERVICE
    public void callGETService() {
        String url = "https://api.backendless.com/65C6BAD1-C8A1-91BF-FFDF-0803DE39EE00/0B0003D1-E1FB-A85B-FFC9-634D746D3100/data/MLAs?sortBy=Name%20desc";
//        String url = "https://api.backendless.com/65C6BAD1-C8A1-91BF-FFDF-0803DE39EE00/0B0003D1-E1FB-A85B-FFC9-634D746D3100/data/MLAs?where=Name%3D'"+et_username.getText().toString+"'%20AND%20City%3D'"+et_password.getText().toString()+"'";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("RESPONSE",response);

                try {
                    jsonArray = new JSONArray(response);

                    ArrayList arrayList = new ArrayList();
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        arrayList.add(jsonObject.getString("Name"));
                    }


//                    ArrayAdapter arrayAdapter = new ArrayAdapter(ShowNeta.this,android.R.layout.simple_list_item_1,arrayList);
//                    lv_Netas.setAdapter(arrayAdapter);
//
                    

                    NetaAdapter netaAdapter = new NetaAdapter(ShowNeta.this, jsonArray);
                    lv_Netas.setAdapter(netaAdapter);


                    lv_Netas.setOnItemClickListener(ShowNeta.this);




                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR",error.getLocalizedMessage());
            }
        });
        RequestQueue request = Volley.newRequestQueue(ShowNeta.this);
        request.add(stringRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            Intent intent = new Intent(ShowNeta.this,ModifyNeta.class);
            intent.putExtra("SELECTED_NETA",jsonObject.toString());
            startActivity(intent);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyClassPrefs",MODE_PRIVATE);
        Toast.makeText(ShowNeta.this,sharedPreferences.getString("Neta_Name",""),Toast.LENGTH_LONG).show();

    }
}
