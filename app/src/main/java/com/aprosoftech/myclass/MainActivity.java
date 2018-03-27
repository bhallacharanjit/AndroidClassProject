package com.aprosoftech.myclass;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    String myName;
    TextView tv_myName;
    Spinner sp_names;
    Button btn_1;
    ListView lv_names;

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
        lv_names = (ListView) findViewById(R.id.lv_names);

        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_1.setOnClickListener(this);


        HashMap hashMap = new HashMap();
        hashMap.put("student","sdfkashgjjsafgl;dfkgl");
        hashMap.put("water","sdjhgsdfhjbsdfjhlbksfg;'h");
        hashMap.put("alphabet","klsadghj;adfhjkgadfklgnkladfn");


//        String hh = hashMap.get("key1").toString();


        HashMap<Integer,String> hashMap1 = new HashMap<>();
        hashMap1.put(1,"test");

        String hh1 = hashMap1.get(1);




        //FOR SPINNER
        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_spinner_item,names);
        sp_names.setAdapter(arrayAdapter);



        //FOR LIST
//        ArrayAdapter arrayAdapter1 = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,names);
//        lv_names.setAdapter(arrayAdapter1);
//        lv_names.setOnItemClickListener(this);






        Log.d("Main2Activity","onCreate");
        tv_myName = findViewById(R.id.tv_name);
        myName = "Charanjit Singh";


        callGETService();

//        tv_myName.setText(myName);
    }



    //FOR CALLING GET SERVICE
    public void callGETService() {
        String url = "https://api.backendless.com/65C6BAD1-C8A1-91BF-FFDF-0803DE39EE00/0B0003D1-E1FB-A85B-FFC9-634D746D3100/data/MLAs?props=Name%2CCity&sortBy=Name%20desc";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("RESPONSE",response);

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    ArrayList arrayList = new ArrayList();
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        arrayList.add(jsonObject.getString("Name"));


                    }


                    ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,arrayList);
                    lv_names.setAdapter(arrayAdapter);




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
        RequestQueue request = Volley.newRequestQueue(MainActivity.this);
        request.add(stringRequest);
    }








    @Override
    public void onClick(View view) {
        Button btn = (Button) view;
        if (btn.getId() == R.id.btn_1) {
//            int selected_position = sp_names.getSelectedItemPosition();
//            Toast.makeText(MainActivity.this,names[selected_position],Toast.LENGTH_LONG).show();

            SharedPreferences sharedPreferences =
                    MainActivity.this.getSharedPreferences
                            ("CLASSPREF",MODE_PRIVATE);
            SharedPreferences.Editor editor= sharedPreferences.edit();

            editor.remove("USER");
            editor.apply();

            MainActivity.this.finish();




        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(MainActivity.this,"Clicked item at "+l, Toast.LENGTH_SHORT).show();
    }
}
