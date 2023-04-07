package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Helper.Global;
import Helper.HeroAdapter;
import model.Hero;
import model.Item;

public class Detail extends NavDrawer {
    public Hero hero;
    TextView name;
    TextView description;
    ImageView image;
    TextView detailAppearance;
    Button savaAsFavBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //View
        name = findViewById(R.id.detail_name);
        description = findViewById(R.id.description);
        image = findViewById(R.id.detail_image);
        detailAppearance = findViewById(R.id.detail_appearance);
        savaAsFavBtn = findViewById(R.id.save_as_fav_btn);

        Intent intent = getIntent();
        String characterId = intent.getStringExtra("characterId");
        if(characterId == null) characterId = "1009609";
        System.out.println(characterId);

        fetchDataByIdAndUploadUI(characterId);
    }

    // if character data is not available offline, fetch character data based on id,
    private void fetchDataByIdAndUploadUI (String id) {
        Gson gson = new Gson();

        //Check data if it is available offline
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        String heroDataAsJSON = sharedPreferences.getString(id, "");
        if(!heroDataAsJSON.isEmpty()) {
            hero = gson.fromJson(heroDataAsJSON, Hero.class);
            updateUi(hero);
            name.append("\nFavourite");
            return;
        }


        RequestQueue rq = Volley.newRequestQueue(this);
        String url = Global.baseUrl + "/characters/" + id + Global.secureParams;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject fullJSONData = response.getJSONObject("data");
                    JSONArray listOfHeroJson = fullJSONData.getJSONArray("results");

                    ArrayList<Hero> heroArrayList = gson.fromJson(listOfHeroJson.toString(), new TypeToken<List<Hero>>(){}.getType()); //convert JSON to ArrayList<Hero>

                    hero = heroArrayList.get(0);
                    updateUi(hero);


                } catch (JSONException e) {
                    Toast.makeText(Detail.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Detail.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        rq.add(jsonObjectRequest);

    }

    private void updateUi(Hero hero) {
        name.setText(hero.name);
        description.setText(hero.description);
        String imageURL = hero.thumbnail.path + "." + hero.thumbnail.extension;
        imageURL = imageURL.replace("http", "https");
        Picasso.get().load(imageURL).into(image);

        String appearanceInComics = "Appear in those comics: \n";
        String appearanceInEvents = "Appear in those events: \n";

        for (Item i : hero.comics.items){
            appearanceInComics = appearanceInComics + i.name + "\n";
        }

        for (Item i : hero.events.items){
            appearanceInEvents = appearanceInEvents + i.name + "\n";
        }

        detailAppearance.setText(appearanceInEvents + "\n" +appearanceInComics);
    }



    //Save data offline
    public void saveAsFavourite(View view){
        Gson gson = new Gson();
        String heroDataAsJSON = gson.toJson(hero);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString(hero.id, heroDataAsJSON);
        myEdit.commit();

        System.out.println(sharedPreferences.getString(hero.id, ""));

        //force reload
        finish();
        startActivity(getIntent());
    }

    public void unSave(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.remove(hero.id);
        myEdit.commit();

        //force reload
        finish();
        startActivity(getIntent());
    }




}