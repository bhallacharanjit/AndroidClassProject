package com.aprosoftech.myclass;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;

public class TrashActivity extends AppCompatActivity {

    RadioButton rb_male,rb_female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash);


        rb_male = (RadioButton) findViewById(R.id.rb_male);
        rb_female = (RadioButton) findViewById(R.id.rb_female);


        if (rb_male.isChecked()) {

        }
    }
}
