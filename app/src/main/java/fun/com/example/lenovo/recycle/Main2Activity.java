package fun.com.example.lenovo.recycle;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import fun.com.example.lenovo.compare.*;

public class Main2Activity extends AppCompatActivity {
    ImageView ivImage;
    TextView tVTitle;
    TextView tvPrice,tvdisplay,tvMemory,tvOs,tvAudio,tvCamera;
    Button compare, btnrate;
    public Button addcart;
    RatingBar rating;
    TextView desc;
    // Button rateit;
    RecyclerView rvItem,details;
    final String TAG = "Main2Activity";
    //Product[] stringArray = new Product[10];
    final ArrayList<String> list = new ArrayList<String>();








   // List stringArrayList = null;
    //final List<String> product=new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ivImage = (ImageView) findViewById(R.id.imageView);
        tVTitle = (TextView) findViewById(R.id.textView);
        tvPrice = (TextView) findViewById(R.id.textView2);
        rating = (RatingBar) findViewById(R.id.ratingBar);
        tvdisplay = (TextView) findViewById(R.id.tvDisplay);
        tvMemory = (TextView) findViewById(R.id.tvMemory);
        tvOs = (TextView) findViewById(R.id.tvos);
        tvAudio= (TextView) findViewById(R.id.tvaudio);
       tvCamera = (TextView) findViewById(R.id.tvCamera);
        btnrate = (Button) findViewById(R.id.rateit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences sharedpref=getSharedPreferences("login", Context.MODE_PRIVATE);

        String username=sharedpref.getString("name", "invalid");
        final String cid=sharedpref.getString("Cust_id", "invalid");


        // rateit=(Button)findViewById(R.id.rat);
        // Random r = new Random();
        // int cid = r.nextInt(80 - 65) + 65;
        list.add("");
       // rvItem = (RecyclerView) findViewById(R.id.similar);
       // rvItem.setHasFixedSize(true);

       // rvItem.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));



        if (getIntent().getSerializableExtra("product") != null) {
            Product product = (Product) getIntent().getSerializableExtra("product");
            String fullUrl = "https://gcsubhash20.000webhostapp.com/customer/" + product.image_url;

            Toast.makeText(this, product.name + product.pid, Toast.LENGTH_LONG).show();
            Picasso.with(this)
                    .load(fullUrl)
                    .placeholder(R.drawable.logo)
                    .error(android.R.drawable.stat_notify_error)
                    .into(ivImage);
            tVTitle.setText(product.name);
            tvPrice.setText("Rs." + product.price);
           // desc.setText(product.Brand);
            tvdisplay.setText(product.Display);
            tvMemory.setText(product.Memory);
                    tvOs.setText(product.Platform);
                            tvAudio.setText(product.Audio);
            tvCamera.setText(product.Camera);

            // float Pprice=product.price;


        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

         similaritem();


        final Product product = (Product) getIntent().getSerializableExtra("product");
        final int pid = product.pid;

        final int qty = 1;
        // final int cid = 100;

        compare = (Button) findViewById(R.id.compare);
        addcart = (Button) findViewById(R.id.okay);


        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //final String URL = "https://gcsubhash20.000webhostapp.com/customer/addcart.php";
                HashMap params = new HashMap();
                params.put("pid", String.valueOf(pid));
                params.put("customer_id", cid);
                params.put("qty", String.valueOf(qty));


                PostResponseAsyncTask task = new PostResponseAsyncTask(Main2Activity.this, params,
                        new AsyncResponse() {
                            @Override
                            public void processFinish(String s) {
                                Log.d(TAG, s);
                                if (s.contains("sucess")) {

                                    Toast.makeText(getApplicationContext(), "Added to cart", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                task.execute("https://gcsubhash20.000webhostapp.com/customer/addcart.php");
            }
        });


        compare.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                ViewDialog1 alert = new ViewDialog1();
                alert.showDialog(Main2Activity.this, "Error de conexión al servidor");


                /*Intent myIntent = new Intent(Main2Activity.this, compareActivity.class);


                myIntent.putExtra("product", product);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Main2Activity.this.startActivity(myIntent);
                */

            }
        });


        btnrate.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                final String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());


                final String rate = String.valueOf(rating.getRating());

                HashMap params = new HashMap();


                params.put("pid", String.valueOf(pid));
                params.put("customer_id", cid);
                params.put("date", String.valueOf(date));
                params.put("rate", String.valueOf(rate));

                PostResponseAsyncTask task = new PostResponseAsyncTask(Main2Activity.this, params,
                        new AsyncResponse() {
                            @Override
                            public void processFinish(String s) {
                                Log.d(TAG, s);
                                if (s.contains("Sucess")) {

                                    Toast.makeText(getApplicationContext(), "Sucessfully rated", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                task.execute("https://gcsubhash20.000webhostapp.com/customer/addrating.php");
            }
        });

/*
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
               final String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
               final  int cid=100;

                final String rate=String.valueOf(ratingBar.getRating());




                String URL = "http://10.0.3.2/customer/addrating.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("success")) {
                            Toast.makeText(getApplicationContext(), "Sucessfully rated", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                       // Toast.makeText(getApplicationContext(),"Sucessfully r"+rate,Toast.LENGTH_SHORT).show();

                        Map<String, String> params = new HashMap<>();
                        params.put("pid", String.valueOf(pid));
                        params.put("customer_id", String.valueOf(cid));
                        params.put("date", date);
                        params.put("rate", rate);

                        return params;
                    }
                };

                Singleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


            }
        });
        */


    }



    public void similaritem() {
        Product product = (Product) getIntent().getSerializableExtra("product");
        final String sim = product.Category;
        final  String id= String.valueOf(product.pid);
        rvItem = (RecyclerView) findViewById(R.id.similar);
        rvItem.setHasFixedSize(true);

        rvItem.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        String url = "https://gcsubhash20.000webhostapp.com/customer/similar.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        ArrayList<Product> productList = new JsonConverter<Product>()
                                .toArrayList(response, Product.class);

                        ProductAdapter adapter = new ProductAdapter(getApplicationContext(), productList);

                        rvItem.setAdapter(adapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null) {
                            Log.d(TAG, error.toString());
                            Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("pcat", sim);
                params.put("pid", id);

                return params;
            }


        };

        Singleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);





    }

    public class ViewDialog1 {



        public void showDialog(Activity activity, String msg) {
            final Dialog dialog = new Dialog(activity,R.style.Dialog);
            //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setTitle("compare with");


            dialog.setCancelable(true);
            dialog.setContentView(R.layout.spinner);
           // String[] numbers = {"none","1","2","3","4","5","6","7","8","9","10","11","12","13","14"};
           // String[] name = new String[10];
           // ArrayList[] arrayList = new ArrayList[]();
           // List<String> list;
          final Spinner sp = (Spinner) dialog.findViewById(R.id.spinner1);

            getdata();



            sp.setAdapter(new ArrayAdapter<String>(Main2Activity.this, android.R.layout.simple_dropdown_item_1line,list));
          // final String Text = sp.getSelectedItem().toString();

           // sp.setAdapter(new ArrayAdapter<>(Main2Activity.this, android.R.layout.simple_dropdown_item_1line,stringArray));

            Button dialogButton = (Button) dialog.findViewById(R.id.okay);
            dialogButton.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                    Product product = (Product) getIntent().getSerializableExtra("product");
                    final String Text = sp.getSelectedItem().toString();
                    //final String Text = sp.getSelectedItem().toString();
                    //Toast.makeText(getApplicationContext(),Text,Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(Main2Activity.this, compareActivity.class);


                    myIntent.putExtra("Pname", Text);
                    myIntent.putExtra("product",product);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Main2Activity.this.startActivity(myIntent);
                    dialog.dismiss();
                }
            });

        Button dialogButton1 = (Button) dialog.findViewById(R.id.cancel);
            dialogButton1.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                //Toast.makeText(getApplicationContext(),stringArray[1],Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
        });

            dialog.show();

    }


}

void getdata(){

    String url = "https://gcsubhash20.000webhostapp.com/customer/getName.php";
    StringRequest stringRequest = new StringRequest(Request.Method.GET,
            url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, response);
                    //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    showJSON(response);


                }

                private void showJSON(String response) {
                       // ArrayList<String> stringArrayList = new ArrayList<>();


                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray result = jsonObject.getJSONArray("result");
                       // stringArray = new String[result.length()];
                        for (int i = 0; i < result.length(); i++) {
                            JSONObject worker = result.getJSONObject(i);

                            String Name=worker.getString("name");

                          list.add(Name);

                            //stringArray[i] = worker.getString("prod_name");

                            //Toast.makeText(getApplicationContext(), price+qty, Toast.LENGTH_LONG).show();

                        }




                        //stringArray =   product.toArray(new Product[product.size()]);
                        //System.out.println(array[0] + " " + array[1] + " " + array[2]);


                        //stringArray =  stringArrayList.toArray(new String[stringArrayList.size()]);





                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error != null) {
                        Log.d(TAG, error.toString());
                        Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_LONG).show();
                    }
                }
            }
    );

    Singleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


    }


}









