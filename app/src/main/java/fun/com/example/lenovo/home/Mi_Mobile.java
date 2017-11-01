package fun.com.example.lenovo.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kosalgeek.android.json.JsonConverter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fun.com.example.lenovo.recycle.*;

/**
 * Created by SumanThp on 7/7/2017.
 */

public class Mi_Mobile extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;   SearchView sv;
    RecyclerView.Adapter adapter;
    String getMi_url = "https://gcsubhash20.000webhostapp.com/customer/getMi.php";
    String getMiImage_url = "https://gcsubhash20.000webhostapp.com/customer/";
    Toolbar tb;
    Bundle bundle ;
    String brand;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_category);
        tb = (Toolbar) findViewById(R.id.tb);
        bundle= getIntent().getExtras();
        brand = bundle.getString("Brand");
        setSupportActionBar(tb);
        tb.setTitle(brand);
        tb.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_navigation_arrow_back));
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.category_recycle);
       recyclerView.setHasFixedSize(true);

       recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));



        StringRequest request = new StringRequest(Request.Method.POST, getMi_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<Product> productList = new JsonConverter<Product>()
                        .toArrayList(response, Product.class);

                ViewMobile adapter = new ViewMobile(getApplicationContext(), productList);

                recyclerView.setAdapter(adapter);


            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("BRAND", brand);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }
}
