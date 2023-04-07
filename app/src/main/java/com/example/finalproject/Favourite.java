package com.example.finalproject;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

import Helper.HeroAdapter;
import model.Hero;

public class Favourite extends NavDrawer {

    RecyclerView recyclerView;
    TextView welcomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        welcomeTextView = findViewById(R.id.welcome);
        recyclerView = findViewById(R.id.favHeroRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Hero> heroes = fetchDataFromPreference();
        recyclerView.setAdapter(new HeroAdapter(getApplicationContext(), heroes));

        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String name = sh.getString("name", " ");
        welcomeTextView.setText("Hi " + name + " , you have " + heroes.size() + " favourite characters");

    }

    private ArrayList<Hero> fetchDataFromPreference (){
        ArrayList<Hero> listOfHero = new ArrayList<Hero>();
        Gson gson = new Gson();

        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);


        Map<String, String> allSavedData = (Map<String, String>) sh.getAll();

        for(Map.Entry<String, String> entry : allSavedData.entrySet()){
            if (entry.getKey().equals("name")) continue;

            Hero hero = gson.fromJson(entry.getValue(), Hero.class);
            listOfHero.add(hero);
        }

        return listOfHero;
    }
}