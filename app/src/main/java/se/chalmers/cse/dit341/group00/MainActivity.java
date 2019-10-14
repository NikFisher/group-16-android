package se.chalmers.cse.dit341.group00;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import se.chalmers.cse.dit341.group00.model.Player;

public class MainActivity extends AppCompatActivity {

    // Field for parameter name
    public static final String HTTP_PARAM = "httpResponse";
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView myPlayerImage = (ImageView) findViewById(R.id.playerImageView);
        myPlayerImage.setImageResource(R.drawable.janitor);
        setPlayerInfo();

        //button to go to battlefield
        Button bossButton = (Button) findViewById(R.id.boss_btn);
        bossButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBattlefield();
            }
        });
    }
        public void openBattlefield(){
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
        }



    public void setPlayerInfo (){
        final TextView myPlayerName = findViewById(R.id.playerNameTextView);
        final TextView myPlayerHealth = findViewById(R.id.playerHealthTextView);
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


                        Player[] players = gson.fromJson(dataArray, Player[].class);

                        myPlayerName.setText(players[0].name);
                        myPlayerDefense.setText(Integer.toString(players[0].defense));
                        myPlayerHealth.setText(Integer.toString(players[0].health));
                        myPlayerDamage.setText(Integer.toString(players[0].damage));
                        myPlayerCurrency.setText(Integer.toString(players[0].currency));
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

    public void launchTaskActivity (View view){

        TextView myPlayerCurrency = findViewById(R.id.playerCurrencyTextView);
        Intent intent = new Intent(this, TaskActivity.class);
        intent.putExtra(HTTP_PARAM, myPlayerCurrency.getText().toString());
        startActivity(intent);


    }
}
