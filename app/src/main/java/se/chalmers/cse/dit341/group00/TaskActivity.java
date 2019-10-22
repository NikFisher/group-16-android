package se.chalmers.cse.dit341.group00;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

public class TaskActivity extends AppCompatActivity {

    Player[] players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        setPlayers();

    }

    public void setPlayers() {

        final TextView myPlayerCurrency = findViewById(R.id.playerCurrency);

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

                        myPlayerCurrency.setText(Integer.toString(players[0].currency));
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                       myPlayerCurrency.setText("Error!" + error.toString());
                    }
                });
        // The request queue makes sure that HTTP requests are processed in the right order.
        queue.add(jsonObjectRequest);
    }

    public void moreBtnClicked(View view) {
        final TextView myPlayerCurrency = findViewById(R.id.playerCurrency);
        players[0].currency += 1000;

        String url = getString(R.string.server_url) + "/api/players/1";
        RequestQueue queue = Volley.newRequestQueue(this);
        try{
            JSONObject postParams = new JSONObject();
            postParams.put("currency", players[0].currency);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.PATCH, url, postParams, new Response.Listener<JSONObject>(){
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


                        }
                    },new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
                    );
            // The request queue makes sure that HTTP requests are processed in the right order.
            queue.add(jsonObjectRequest);
        } catch (JSONException err){
            System.out.println(err);
        }

        myPlayerCurrency.setText(Integer.toString(players[0].currency));
    }

}


