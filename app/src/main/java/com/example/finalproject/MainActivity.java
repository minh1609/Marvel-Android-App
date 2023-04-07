package com.example.finalproject;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Helper.Global;
import Helper.HeroAdapter;
import model.Hero;

public class MainActivity extends NavDrawer {

    Button fetchData;
    RecyclerView recyclerView;
    TextView seaarchBarTextView;
    ArrayList<Hero> listOfHero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listOfHero = new ArrayList<>();

        fetchData = findViewById(R.id.fetchData);
        recyclerView = findViewById(R.id.heroRecyclerView);
        seaarchBarTextView = findViewById(R.id.searchBarTextView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new HeroAdapter(getApplicationContext(), listOfHero));


        RequestQueue rq = Volley.newRequestQueue(this);


        fetchData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchQuery = seaarchBarTextView.getText().toString();
                String url = Global.baseUrl + "/characters" + Global.secureParams + "&limit=80&nameStartsWith=" + searchQuery;


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject fullJSONData = response.getJSONObject("data");
                            JSONArray listOfHeroJson = fullJSONData.getJSONArray("results");
                            Gson gson = new Gson();
                            listOfHero = gson.fromJson(listOfHeroJson.toString(), new TypeToken<List<Hero>>(){}.getType()); //convert JSON to ArrayList<Hero>

                            recyclerView.setAdapter(new HeroAdapter(getApplicationContext(), listOfHero)); // force re-render list after the data is fetched

                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                rq.add(jsonObjectRequest);

            }

        });
    }
}