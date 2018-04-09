package com.aprosoftech.myclass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by CSB on 12/03/18.
 */

public class NetaAdapter extends BaseAdapter {

    Context context;
    JSONArray netaArray,netaCompleteArray;
    LayoutInflater layoutInflater = null;

    public NetaAdapter(Context context , JSONArray jsonArray) {
        this.context = context;
        this.netaArray = this.netaCompleteArray = jsonArray;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }


    public void filterData(String filterString) {
        if (filterString.length() == 0) {
            this.netaArray = this.netaCompleteArray;
            this.notifyDataSetChanged();
            return;
        }

        this.netaArray = new JSONArray();

       for (int i=0;i<this.netaCompleteArray.length();i++) {
           try {
               JSONObject jsonObject = this.netaCompleteArray.getJSONObject(i);
               String netaName = jsonObject.getString("Name");

               if (netaName.toLowerCase().contains(filterString.toLowerCase()))
               {
                   this.netaArray.put(jsonObject);
               }
           } catch (JSONException e) {
               e.printStackTrace();
           }


       }

       this.notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        return this.netaArray.length();
    }

    @Override
    public Object getItem(int i) {
        try {
            return this.netaArray.get(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View myXmlView = view;
        if (myXmlView == null)
            myXmlView = layoutInflater.inflate(R.layout.custom_list,null);

        TextView tv_name = (TextView) myXmlView.findViewById(R.id.tv_name);
        TextView tv_city = (TextView) myXmlView.findViewById(R.id.tv_city);
        TextView tv_party = (TextView) myXmlView.findViewById(R.id.tv_party);

        try {
            JSONObject jsonObject = this.netaArray.getJSONObject(i);
            tv_name.setText(jsonObject.getString("Name"));
            tv_city.setText(jsonObject.getString("City"));
            tv_party.setText(jsonObject.getString("Party"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return myXmlView;
    }
}
