package se.chalmers.cse.dit341.group00;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class SecondActivity extends AppCompatActivity {
    Player[] players;
    Boss[] bosses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        setPlayer();
        setBoss();

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.HTTP_PARAM);

            Button exitButton = (Button) findViewById(R.id.exit_btn);
            exitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        goToHome();
                }
            });

            Button attackButton = (Button) findViewById(R.id.attackBtn);
            attackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                            updateHealth(players[0],bosses[0]);
                        //updatePlayerHealth();
                }
            });
        }
        public void updateHealth(Player player, Boss boss) {
            //TextView to show HP
            TextView playerHealth = findViewById(R.id.textView12);
            TextView bossHealth = findViewById(R.id.textView14);


           //-----Update Player and Boss---------//
            player.health = player.health - boss.damage;
            boss.health = boss.health - player.damage;

            //-----Set values to Textview---------//
            bossHealth.setText(Integer.toString(boss.health));
            playerHealth.setText(Integer.toString(player.health));

            //------Update values---------//
            player.setHealth(player.health);
            boss.setHealth(boss.health);


        }

        public void goToHome(){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }

        public void setPlayer(){
            TextView playerHealth = findViewById(R.id.textView12);
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

                            playerHealth.setText(Integer.toString(players[0].health));


                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            playerHealth.setText("Error!" + error.toString());

                        }
                    });
            // The request queue makes sure that HTTP requests are processed in the right order.
            queue.add(jsonObjectRequest);
        }


       public void setBoss(){
            TextView bossHealth = findViewById(R.id.textView14);
            String url = getString(R.string.server_url) + "/api/bosses";

            RequestQueue queue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

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

                            bossHealth.setText(Integer.toString(bosses[0].health));


                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            bossHealth.setText("Error!" + error.toString());

                        }
                    });
            // The request queue makes sure that HTTP requests are processed in the right order.
            queue.add(jsonObjectRequest);
        }



}



