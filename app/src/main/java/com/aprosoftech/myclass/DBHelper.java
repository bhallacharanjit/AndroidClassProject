package com.aprosoftech.myclass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by CSB on 24/04/18.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME =      "NetasDB.db";
    public static final String NETA_TABLE_NAME =    "Netas";
    public static final String COL_NETA_ID =        "Netaid";
    public static final String COL_NETA_NAME =      "NetaName";
    public static final String COL_NETA_PARTY =     "NetaParty";
    public static final String COL_NETA_CITY =      "NetaCity";
    public static final Integer DATABASE_VERSION =  1;





    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //create table Netas (id integer primary key, NetaName text,
        // NetaParty text, NetaCity text)


        String command = "CREATE TABLE "+NETA_TABLE_NAME
                +" ("+COL_NETA_ID+" integer primary key, "
                +COL_NETA_NAME+" text, "
                +COL_NETA_CITY+" text,"
                +COL_NETA_PARTY+" text";

        Log.d("CREATE TABLE COMMAND",command);

        sqLiteDatabase.execSQL(command);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }



    //getting all values from Database
    public JSONArray getAllDBValues() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String selectQuery = "select * from "+NETA_TABLE_NAME;
        Cursor selectCursor = sqLiteDatabase.rawQuery(selectQuery,null);
        JSONArray jsonArray = new JSONArray();
        while (selectCursor.moveToNext()) {
            String neta_name = selectCursor.getString(selectCursor.getColumnIndex(COL_NETA_NAME));
            String neta_city = selectCursor.getString(selectCursor.getColumnIndex(COL_NETA_CITY));
            String neta_party = selectCursor.getString(selectCursor.getColumnIndex(COL_NETA_PARTY));

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name",neta_name);
                jsonObject.put("city",neta_city);
                jsonObject.put("party",neta_party);
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArray;
    }


    //inserting data into table
    public void insertData(JSONObject jsonObject) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put(COL_NETA_NAME, jsonObject.getString("name"));
            contentValues.put(COL_NETA_CITY, jsonObject.getString("city"));
            contentValues.put(COL_NETA_PARTY, jsonObject.getString("party"));

            long status = sqLiteDatabase.insert(NETA_TABLE_NAME,null,contentValues);
            if (status > 0) {
                Log.d("INSERT STATUS","SUCCESS with id "+status);
            } else {
                Log.d("INSERT STATUS","ERROR");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




}
