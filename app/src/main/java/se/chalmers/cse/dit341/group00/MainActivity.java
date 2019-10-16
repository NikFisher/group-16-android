package se.chalmers.cse.dit341.group00;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import se.chalmers.cse.dit341.group00.model.Player;


public class MainActivity extends AppCompatActivity {

    // Field for parameter name
    public Player[] players;

    public static final String HTTP_PARAM = "httpResponse";
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* ImageView myPlayerImage = (ImageView) findViewById(R.id.playerImageView);
        myPlayerImage.setImageResource(R.drawable.janitor);*/
        setPlayerInfo();

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



    public void setPlayerInfo (){
        final TextView myPlayerName = findViewById(R.id.playerNameTextView);
        TextView myPlayerHealth = findViewById(R.id.playerHealthTextView);
        final TextView myPlayerDamage = findViewById(R.id.playerDamageTextView);
        final TextView myPlayerDefense = findViewById(R.id.playerDefenseTextView);
        final TextView myPlayerCurrency = findViewById(R.id.playerCurrencyTextView);

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

                        myPlayerName.setText(players[0].name);
                        myPlayerDefense.setText(Integer.toString(players[0].defense));
                        myPlayerHealth.setText(Integer.toString(players[0].health));
                        myPlayerDamage.setText(Integer.toString(players[0].damage));
                        myPlayerCurrency.setText(Integer.toString(players[0].currency));
                        setPlayerImage(players[0].damage);

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

        // The request queue makes sure that HTTP requests are processed in the right order.
        queue.add(jsonObjectRequest);

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
}
