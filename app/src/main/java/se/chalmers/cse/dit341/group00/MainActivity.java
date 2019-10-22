package se.chalmers.cse.dit341.group00;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


import se.chalmers.cse.dit341.group00.model.Boss;
import se.chalmers.cse.dit341.group00.model.Item;

import se.chalmers.cse.dit341.group00.model.Player;
import se.chalmers.cse.dit341.group00.model.Shop;


public class MainActivity extends AppCompatActivity{

    // Field for parameter name

    public Boss [] bosses;
    public Player[] players;
    public Item[] items;
    public Shop[] shops;

    public static final String HTTP_PARAM = "httpResponse";
    private View view;
    private Spinner spinner;
    private static final String[] paths = {"Simon", "Tobias", "Nick"};

    public String boss1Description = "This garbage man leaves garbage everywhere . He is not very good at his job";
    public String boss1Difficulty = "Easy";
    public String boss1name = "Garbage Garbage Man";
    public int boss1Damage = 30;
    public int boss1Health = 200;

    public String boss2Description = "Giant mutant bacteria, This bacteria wants to infect veryone it comes in contact with ";
    public String boss2Difficulty = "Medium";
    public String boss2name = "Bacterius Maximus";
    public int boss2Damage = 50;
    public int boss2Health = 300;

    public String boss3Description = "This building is so dirty it spreads diseases to the people in the city, making them sick ";
    public String boss3Difficulty = "hard";
    public String boss3name = "dirty building";
    public int boss3Damage = 200;
    public int boss3Health = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0) {
                    setPlayerInfo(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


       /* ImageView myPlayerImage = (ImageView) findViewById(R.id.playerImageView);
        myPlayerImage.setImageResource(R.drawable.janitor);*/
        getPlayers();



        //button to go to battlefield
        Button bossButton = (Button) findViewById(R.id.boss_btn);

        bossButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openBattlefield();
            }
        });

        Button ressButton = (Button) findViewById(R.id.ressurect_btn);


        ressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ressurectPlayer();

            }
        });

        Button shopButton = (Button) findViewById(R.id.shop_button);
        shopButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openShop();
            }
        });
    }
    public void openBattlefield(){
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
    public void openShop(){
        Intent shopIntent = new Intent(this, ShopActivity.class);
        startActivity(shopIntent);
    }
    public void ressurectPlayer(){
        TextView ressText = findViewById(R.id.textView9);
        Button ressButton = (Button) findViewById(R.id.ressurect_btn);
        if (players[0].health == 100){
            ressText.setText("You're already at full health");
            ressButton.setEnabled(false);
        }
        else {
            patchPlayer(100, false);
            players[0].health = 100;
            players[0].dead = false;
            TextView myPlayerHealth = findViewById(R.id.playerHealthTextView);
            myPlayerHealth.setText(Integer.toString(players[0].health));

            ressButton.setEnabled(false);

            ressText.setText("You have been brought back to life");
        }
    }




    public void getPlayers (){


        String url = getString(R.string.server_url) + "/api/players";

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // GSON allows to parse a JSON string/JSONObject directly into a user-defined class
                        Gson gson = new Gson();

                        String dataArray = null;


                        try {
                            dataArray = response.getString("players");

                        } catch (JSONException e) {
                            Log.e(this.getClass().toString(), e.getMessage());
                        }


                        players = gson.fromJson(dataArray, Player[].class);


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);

                    }
                });

        // The request queue makes sure that HTTP requests are processed in the right order.
        queue.add(jsonObjectRequest);

    }

    public void setPlayerInfo(int index){
        TextView myPlayerName = findViewById(R.id.playerNameTextView);
        TextView myPlayerHealth = findViewById(R.id.playerHealthTextView);
        TextView myPlayerDamage = findViewById(R.id.playerDamageTextView);
        TextView myPlayerDefense = findViewById(R.id.playerDefenseTextView);
        TextView myPlayerCurrency = findViewById(R.id.playerCurrencyTextView);

        try {
            String url = getString(R.string.server_url) + "/api/players/" + players[index]._id;


        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // GSON allows to parse a JSON string/JSONObject directly into a user-defined class
                        Gson gson = new Gson();

                        String dataArray = null;
                        String playerName = null;
                        String playerHealth = null;
                        String playerDamage = null;
                        String playerDefense = null;
                        String playerCurrency = null;

                        try {
                            dataArray = null;
                            playerName = response.getString("name");
                            playerHealth = response.getString("health");
                            playerDamage = response.getString("damage");
                            playerDefense = response.getString("defense");
                            playerCurrency = response.getString("currency");

                        } catch (JSONException e) {
                            Log.e(this.getClass().toString(), e.getMessage());
                        }

                        myPlayerName.setText(playerName);
                        myPlayerHealth.setText(playerHealth);
                        myPlayerDamage.setText(playerDamage);
                        myPlayerDefense.setText(playerDefense);
                        myPlayerCurrency.setText(playerCurrency);

                        setPlayerImage(players[index].damage);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myPlayerName.setText("Error!" + error.toString());
                        myPlayerHealth.setText("Error!" + error.toString());
                        myPlayerDamage.setText("Error!" + error.toString());
                        myPlayerDefense.setText("Error!" + error.toString());
                        myPlayerCurrency.setText("Error!" + error.toString());
                    }
                });
        queue.add(jsonObjectRequest);
        }catch(Exception err){
            myPlayerName.setText("Server");
            myPlayerHealth.setText("Offline");
        }
    }

    public void setPlayerImage (int damage){

        ImageView img = findViewById(R.id.playerImageView);

        if(damage >= 500){
            img.setImageResource(R.drawable.janitor2);
        }else{
            img.setImageResource(R.drawable.janitor);
        }

    }

    public void launchTaskActivity (View view){

        Intent intent = new Intent(this, TaskActivity.class);
        startActivity(intent);

    }
    public void patchPlayer(int health, boolean dead) {
        String url = getString(R.string.server_url) + "/api/players/1";

        RequestQueue queue = Volley.newRequestQueue(this);

        try {
            JSONObject postParams = new JSONObject();
            postParams.put("health", Integer.toString(health));
            postParams.put("dead", Boolean.toString(dead));


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.PATCH, url, postParams, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // GSON allows to parse a JSON string/JSONObject directly into a user-defined class
                            Gson gson = new Gson();

                            String dataArray = null;

                            try {
                                dataArray = response.getString("items");

                            } catch (JSONException e) {
                                Log.e(this.getClass().toString(), e.getMessage());
                            }


                            Player[] players = gson.fromJson(dataArray, Player[].class);


                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }

                    }
                    );


            // The request queue makes sure that HTTP requests are processed in the right order.
            queue.add(jsonObjectRequest);
        } catch (JSONException err) {
            System.out.println(err);
        }


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_main);
        getPlayers();

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0) {
                    setPlayerInfo(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button bossButton = (Button) findViewById(R.id.boss_btn);

        bossButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openBattlefield();
            }
        });

        Button ressButton = (Button) findViewById(R.id.ressurect_btn);


        ressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ressurectPlayer();

            }
        });

        Button shopButton = (Button) findViewById(R.id.shop_button);
        shopButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openShop();
            }
        });



    }

    public void deleteBosses(){
        String url = getString(R.string.server_url) + "/api/bosses/";

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // GSON allows to parse a JSON string/JSONObject directly into a user-defined class
                        Gson gson = new Gson();

                        String dataArray = null;


                        try {
                            dataArray = response.getString("bosses");

                        } catch (JSONException e) {
                            Log.e(this.getClass().toString(), e.getMessage());
                        }


                        Boss[] bosses = gson.fromJson(dataArray, Boss[].class);


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        // The request queue makes sure that HTTP requests are processed in the right order.
        queue.add(jsonObjectRequest);

    }
    public void postBosses(){
        String url = getString(R.string.server_url) + "/api/bosses/";
        RequestQueue queue = Volley.newRequestQueue(this);

        try {
            JSONObject postParams = new JSONObject();
            postParams.put("id", "30");
            postParams.put("description", boss1Description);
            postParams.put("difficulty", boss1Difficulty);
            postParams.put("health", Integer.toString(boss1Health));
            postParams.put("damage",Integer.toString(boss1Damage));
            postParams.put("name", boss1name);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, postParams, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // GSON allows to parse a JSON string/JSONObject directly into a user-defined class
                            Gson gson = new Gson();
                            String dataArray = null;

                            try {
                                dataArray = response.getString("Boss");

                            } catch (JSONException e) {
                                Log.e(this.getClass().toString(), e.getMessage());
                            }

                            bosses = gson.fromJson(dataArray, Boss[].class);


                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }

                    }
                    );
            // The request queue makes sure that HTTP requests are processed in the right order.
            queue.add(jsonObjectRequest);
        } catch (JSONException err) {
            System.out.println(err);
        }

        try {
            JSONObject postParams = new JSONObject();
            postParams.put("id", "30");
            postParams.put("description", boss2Description);
            postParams.put("difficulty", boss2Difficulty);
            postParams.put("health", Integer.toString(boss2Health));
            postParams.put("damage",Integer.toString(boss2Damage));
            postParams.put("name", boss2name);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, postParams, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // GSON allows to parse a JSON string/JSONObject directly into a user-defined class
                            Gson gson = new Gson();
                            String dataArray = null;

                            try {
                                dataArray = response.getString("Boss");

                            } catch (JSONException e) {
                                Log.e(this.getClass().toString(), e.getMessage());
                            }

                            bosses = gson.fromJson(dataArray, Boss[].class);


                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }

                    }
                    );
            // The request queue makes sure that HTTP requests are processed in the right order.
            queue.add(jsonObjectRequest);
        } catch (JSONException err) {
            System.out.println(err);
        }

        try {
            JSONObject postParams = new JSONObject();
            postParams.put("id", "40");
            postParams.put("description", boss3Description);
            postParams.put("difficulty", boss3Difficulty);
            postParams.put("health", Integer.toString(boss3Health));
            postParams.put("damage",Integer.toString(boss3Damage));
            postParams.put("name", boss3name);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, postParams, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // GSON allows to parse a JSON string/JSONObject directly into a user-defined class
                            Gson gson = new Gson();
                            String dataArray = null;

                            try {
                                dataArray = response.getString("Boss");

                            } catch (JSONException e) {
                                Log.e(this.getClass().toString(), e.getMessage());
                            }

                            bosses = gson.fromJson(dataArray, Boss[].class);


                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }

                    }
                    );
            // The request queue makes sure that HTTP requests are processed in the right order.
            queue.add(jsonObjectRequest);
        } catch (JSONException err) {
            System.out.println(err);
        }
    }

    public void putPlayer(){
        TextView myPlayerName = findViewById(R.id.playerNameTextView);
        TextView myPlayerHealth = findViewById(R.id.playerHealthTextView);
        TextView myPlayerDamage = findViewById(R.id.playerDamageTextView);
        TextView myPlayerDefense = findViewById(R.id.playerDefenseTextView);
        TextView myPlayerCurrency = findViewById(R.id.playerCurrencyTextView);

        players[0].name= "Simon";
        players[0].damage = 10;
        players[0].defense = 10;
        players[0].health = 100;
        players[0].currency = 0;


        String url = getString(R.string.server_url) + "/api/players/1";

        RequestQueue queue = Volley.newRequestQueue(this);

        try {
            JSONObject postParams = new JSONObject();
            postParams.put("name", players[0].name);
            postParams.put("damage", players[0].damage);
            postParams.put("defense", players[0].defense);
            postParams.put("health", players[0].health);
            postParams.put("currency",players[0].currency);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.PUT, url, postParams, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // GSON allows to parse a JSON string/JSONObject directly into a user-defined class
                            Gson gson = new Gson();

                            String data = null;

                            try {
                                data = response.getString("players");

                            } catch (JSONException e) {
                                Log.e(this.getClass().toString(), e.getMessage());
                            }


                            myPlayerDefense.setText(Integer.toString(players[0].defense));
                            myPlayerHealth.setText(Integer.toString(players[0].health));
                            myPlayerDamage.setText(Integer.toString(players[0].damage));
                            myPlayerCurrency.setText(Integer.toString(players[0].currency));

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                            myPlayerHealth.setText("Error!" + error.toString());
                            myPlayerDamage.setText("Error!" + error.toString());
                            myPlayerDefense.setText("Error!" + error.toString());
                            myPlayerCurrency.setText("Error!" + error.toString());
                        }

                    }
                    );


            // The request queue makes sure that HTTP requests are processed in the right order.
            queue.add(jsonObjectRequest);
        } catch (JSONException err) {
            System.out.println(err);
        }
    }

    public void deleteItems(){
        String url = getString(R.string.server_url) + "/api/items/";

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // GSON allows to parse a JSON string/JSONObject directly into a user-defined class
                        Gson gson = new Gson();

                        String dataArray = null;


                        try {
                            dataArray = response.getString("items");

                        } catch (JSONException e) {
                            Log.e(this.getClass().toString(), e.getMessage());
                        }

                        items = gson.fromJson(dataArray, Item[].class);


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        // The request queue makes sure that HTTP requests are processed in the right order.
        queue.add(jsonObjectRequest);
    }

    public void putShop(){
        String url = getString(R.string.server_url) + "/api/shops/10";

        RequestQueue queue = Volley.newRequestQueue(this);

        try {
            JSONObject postParams = new JSONObject();
            postParams.put("name", "The Shop");
            postParams.put("items", null);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.PUT, url, postParams, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // GSON allows to parse a JSON string/JSONObject directly into a user-defined class
                            Gson gson = new Gson();

                            String data = null;

                            try {
                                data = response.getString("players");

                            } catch (JSONException e) {
                                Log.e(this.getClass().toString(), e.getMessage());
                            }


                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {


                        }

                    }
                    );


            // The request queue makes sure that HTTP requests are processed in the right order.
            queue.add(jsonObjectRequest);
        } catch (JSONException err) {
            System.out.println(err);
        }
    }
    public void postItemsToShop(String _id, String name, String description, String type, int attackValue, int defenseValue, int price){
        String url = getString(R.string.server_url) + "/api/shops/10/items";
        RequestQueue queue = Volley.newRequestQueue(this);

        try {
            JSONObject postParams = new JSONObject();
            postParams.put("_id", _id);
            postParams.put("name", name);
            postParams.put("description", description);
            postParams.put("type", type);
            postParams.put("attackValue", attackValue);
            postParams.put("defenseValue",defenseValue);
            postParams.put("price", price);



            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, postParams, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // GSON allows to parse a JSON string/JSONObject directly into a user-defined class
                            Gson gson = new Gson();
                            String dataArray = null;

                            try {
                                dataArray = response.getString("shops");

                            } catch (JSONException e) {
                                Log.e(this.getClass().toString(), e.getMessage());
                            }

                            shops = gson.fromJson(dataArray, Shop[].class);


                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }

                    }
                    );
            // The request queue makes sure that HTTP requests are processed in the right order.
            queue.add(jsonObjectRequest);
        } catch (JSONException err) {
            System.out.println(err);
        }
    }
    public void newGameBtnClicked(View view){
        postBosses();
        putPlayer();
        String item1Description = "This mop ain't much, but it gets the job done";
        String item2Description = "liquid generally used to decrease infectious agents on the hands";
        String item3Description = "This inexpensive shoes rely on a proprietary closed-cell resin " +
                "material called Croslite to produce a lightweight, slip-resistant," +
                "odor-resistant, non-marking sole.,";
        String item4Description = "helpful for cleaning in confined areas and for " +
                "the removal of hazardous dust and fumes.";
        String item5Description = "A hazmat suit, also known as decontamination suit, " +
                "is a piece of personal protective equipment that consists of an impermeable " +
                "whole-body garment worn as protection against hazardous materials.";

        postItemsToShop("11", "Standard Mop", item1Description,"attack",5,5, 20);
        postItemsToShop("12", "Hand sanitizer", item2Description,"defence",10,10, 40);
        postItemsToShop("13", "Crocs", item3Description,"defence",25,0, 60);
        postItemsToShop("14", "vacuum Cleaner", item4Description,"attack",0,15, 80);
        postItemsToShop("15", "Standard Mop", item5Description,"attack",50,0, 100);
    }

    /*
     var item5 = {
        name: 'hazmat suit',
        description: 'A hazmat suit, also known as decontamination suit, ' +
    'is a piece of personal protective equipment that consists of an impermeable ' +
     'whole-body garment worn as protection against hazardous materials.',
        type: 'defence',
        attackValue: 50,
        defenseValue: 0,
        price: 100
      } */

    public void resetBtnClicked(View view){
        deleteBosses();
        deleteItems();
        putShop();
    }
}
