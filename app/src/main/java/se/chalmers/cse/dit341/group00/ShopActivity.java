package se.chalmers.cse.dit341.group00;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;

import se.chalmers.cse.dit341.group00.model.Item;

public class ShopActivity extends AppCompatActivity {
    Item[] items;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.HTTP_PARAM);
        getItems();

        Button buyBtn1 = findViewById(R.id.buy_btn1);
        buyBtn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                buyItem(0);
            }
        });
        Button buyBtn2 = findViewById(R.id.buy_btn2);
        buyBtn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                buyItem(1);
            }
        });
        Button buyBtn3 = findViewById(R.id.buy_btn3);
        buyBtn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                buyItem(2);
            }
        });
        Button buyBtn4 = findViewById(R.id.buy_btn4);
        buyBtn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                buyItem(3);
            }
        });
        Button buyBtn5 = findViewById(R.id.buy_btn5);
        buyBtn5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                buyItem(4);
            }
        });



    }
    public void buyItem(int index){
        if (items.length > index){
            String url = getString(R.string.server_url) + "/api/shops/10/items/" + items[index]._id;
            ArrayList<TextView> itemNames = new ArrayList<>();
            TextView itemName1 = findViewById(R.id.itemName1);
            TextView itemName2 = findViewById(R.id.itemName2);
            TextView itemName3 = findViewById(R.id.itemName3);
            TextView itemName4 = findViewById(R.id.itemName4);
            TextView itemName5 = findViewById(R.id.itemName5);

            itemNames.add(itemName1);
            itemNames.add(itemName2);
            itemNames.add(itemName3);
            itemNames.add(itemName4);
            itemNames.add(itemName5);

            ArrayList<TextView> itemDefenses = new ArrayList<>();
            TextView itemDefense1 = findViewById(R.id.itemDefense1);
            TextView itemDefense2 = findViewById(R.id.itemDefense2);
            TextView itemDefense3 = findViewById(R.id.itemDefense3);
            TextView itemDefense4 = findViewById(R.id.itemDefense4);
            TextView itemDefense5 = findViewById(R.id.itemDefense5);

            itemDefenses.add(itemDefense1);
            itemDefenses.add(itemDefense2);
            itemDefenses.add(itemDefense3);
            itemDefenses.add(itemDefense4);
            itemDefenses.add(itemDefense5);

            ArrayList<TextView> itemAttacks = new ArrayList<>();
            TextView itemAttack1 = findViewById(R.id.itemAttack1);
            TextView itemAttack2 = findViewById(R.id.itemAttack2);
            TextView itemAttack3 = findViewById(R.id.itemAttack3);
            TextView itemAttack4 = findViewById(R.id.itemAttack4);
            TextView itemAttack5 = findViewById(R.id.itemAttack5);
            itemAttacks.add(itemAttack1);
            itemAttacks.add(itemAttack2);
            itemAttacks.add(itemAttack3);
            itemAttacks.add(itemAttack4);
            itemAttacks.add(itemAttack5);

            ArrayList<TextView> itemPrices = new ArrayList<>();
            TextView itemPrice1 = findViewById(R.id.itemPrice1);
            TextView itemPrice2 = findViewById(R.id.itemPrice2);
            TextView itemPrice3 = findViewById(R.id.itemPrice3);
            TextView itemPrice4 = findViewById(R.id.itemPrice4);
            TextView itemPrice5 = findViewById(R.id.itemPrice5);
            itemPrices.add(itemPrice1);
            itemPrices.add(itemPrice2);
            itemPrices.add(itemPrice3);
            itemPrices.add(itemPrice4);
            itemPrices.add(itemPrice5);
            itemNames.get(index).setText("out of Stock");
            itemAttacks.get(index).setText("n/a");
            itemDefenses.get(index).setText("n/a");
            itemPrices.get(index).setText("n/a");

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






                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
            // The request queue makes sure that HTTP requests are processed in the right order.
            queue.add(jsonObjectRequest);
        }

    }
    public void getItems(){
        String url = getString(R.string.server_url) + "/api/shops/10";


        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // GSON allows to parse a JSON string/JSONObject directly into a user-defined class
                        Gson gson = new Gson();

                        String dataArray = null;

                        ArrayList<TextView> itemNames = new ArrayList<>();
                        TextView itemName1 = findViewById(R.id.itemName1);
                        TextView itemName2 = findViewById(R.id.itemName2);
                        TextView itemName3 = findViewById(R.id.itemName3);
                        TextView itemName4 = findViewById(R.id.itemName4);
                        TextView itemName5 = findViewById(R.id.itemName5);

                        itemNames.add(itemName1);
                        itemNames.add(itemName2);
                        itemNames.add(itemName3);
                        itemNames.add(itemName4);
                        itemNames.add(itemName5);

                        ArrayList<TextView> itemDefenses = new ArrayList<>();
                        TextView itemDefense1 = findViewById(R.id.itemDefense1);
                        TextView itemDefense2 = findViewById(R.id.itemDefense2);
                        TextView itemDefense3 = findViewById(R.id.itemDefense3);
                        TextView itemDefense4 = findViewById(R.id.itemDefense4);
                        TextView itemDefense5 = findViewById(R.id.itemDefense5);

                        itemDefenses.add(itemDefense1);
                        itemDefenses.add(itemDefense2);
                        itemDefenses.add(itemDefense3);
                        itemDefenses.add(itemDefense4);
                        itemDefenses.add(itemDefense5);

                        ArrayList<TextView> itemAttacks = new ArrayList<>();
                        TextView itemAttack1 = findViewById(R.id.itemAttack1);
                        TextView itemAttack2 = findViewById(R.id.itemAttack2);
                        TextView itemAttack3 = findViewById(R.id.itemAttack3);
                        TextView itemAttack4 = findViewById(R.id.itemAttack4);
                        TextView itemAttack5 = findViewById(R.id.itemAttack5);
                        itemAttacks.add(itemAttack1);
                        itemAttacks.add(itemAttack2);
                        itemAttacks.add(itemAttack3);
                        itemAttacks.add(itemAttack4);
                        itemAttacks.add(itemAttack5);

                        ArrayList<TextView> itemPrices = new ArrayList<>();
                        TextView itemPrice1 = findViewById(R.id.itemPrice1);
                        TextView itemPrice2 = findViewById(R.id.itemPrice2);
                        TextView itemPrice3 = findViewById(R.id.itemPrice3);
                        TextView itemPrice4 = findViewById(R.id.itemPrice4);
                        TextView itemPrice5 = findViewById(R.id.itemPrice5);
                        itemPrices.add(itemPrice1);
                        itemPrices.add(itemPrice2);
                        itemPrices.add(itemPrice3);
                        itemPrices.add(itemPrice4);
                        itemPrices.add(itemPrice5);




                        try {
                            dataArray = response.getString("items");

                        } catch (JSONException e) {
                            Log.e(this.getClass().toString(), e.getMessage());
                        }


                        items = gson.fromJson(dataArray, Item[].class);

                        for (int i = 0; i < items.length; i++){
                            itemNames.get(i).setText(items[i].name);
                            itemDefenses.get(i).setText(String.valueOf(items[i].defenseValue));
                            itemAttacks.get(i).setText(String.valueOf(items[i].attackValue));
                            itemPrices.get(i).setText(String.valueOf(items[i].price));
                        }


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
}
