package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Setting extends NavDrawer {

    Button resetBtn;
    TextView nameEditText;
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        resetBtn = findViewById(R.id.resetBtn);
        nameEditText = findViewById(R.id.nameEditText);
        sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);


    }

    public void reset(View view){
        SharedPreferences.Editor myEdit = sh.edit();
        myEdit.clear();
        myEdit.commit();
    }

    public void setName(View view){
        String newName = nameEditText.getText().toString();
        SharedPreferences.Editor myEdit = sh.edit();
        myEdit.putString("name", newName);
        myEdit.commit();
    }
}