package fun.com.example.lenovo.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fun.com.example.lenovo.recycle.Product;
import fun.com.example.lenovo.recycle.ProductAdapter;
import fun.com.example.lenovo.recycle.R;
import fun.com.example.lenovo.recycle.Singleton;

public class order1 extends AppCompatActivity {
    RecyclerView rvItem;
    RecyclerView.LayoutManager layManager;
    RecyclerView.Adapter mAdapter;
    RecyclerView.ItemAnimator itemAnimator ;
    //String insert_cart_url = "http://192.168.0.106/getCart.php";
    String get_order_url = "https://gcsubhash20.000webhostapp.com/customer/getOrdered.php";
    ArrayList<String> Name = new ArrayList<>();

    ArrayList<String> Price = new ArrayList<>();
    ArrayList<Integer> Quan = new ArrayList<>();
    ArrayList<String> Image = new ArrayList<>();
    ArrayList<Integer>productID = new ArrayList<>();
    ArrayList<String> Shipping_address = new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getCartInformation();

    }



    public void getCartInformation() {
        SharedPreferences sharedpref=getSharedPreferences("login", Context.MODE_PRIVATE);
        final String cid=sharedpref.getString("Cust_id", "invalid");
        rvItem = (RecyclerView) findViewById(R.id.recycler);
        rvItem.setHasFixedSize(true);

        rvItem.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));



        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                get_order_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("order1", response);
                        // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        ArrayList<Product> productList = new JsonConverter<Product>()
                                .toArrayList(response, Product.class);

                        OrderAdapter adapter = new OrderAdapter(getApplicationContext(), productList);

                        rvItem.setAdapter(adapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null) {
                            Log.d("order1", error.toString());
                            Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("cid", cid);


                return params;
            }


        };

        Singleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);




    }
}

