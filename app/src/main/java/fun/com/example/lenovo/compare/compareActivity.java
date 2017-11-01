package fun.com.example.lenovo.compare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import fun.com.example.lenovo.recycle.Product;


import fun.com.example.lenovo.recycle.R;

public class compareActivity extends AppCompatActivity {
   // TextView mConditionTextView;
   // DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
   // DatabaseReference mConditionRef = mRootRef.child("book");

   final String TAG = this.getClass().getName();
    TextView tVTitle,tvPrice,tVTitle1,tvPrice1,tvBrand,tvBrand1,tvOS,tvOS1,tvdisplay,tvdisplay1,tvCamera,tvCamera1,tvAudio,TvAudio1,tvMemory,tvMemory1;
    ImageView ivImage,ivImage1;
    //RecyclerView rvItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        tVTitle=(TextView)findViewById(R.id.tvName);
        tvPrice=(TextView)findViewById(R.id.tvPrice);
        ivImage=(ImageView)findViewById(R.id.recommendImage);
        tVTitle1=(TextView)findViewById(R.id.tvName1);
        tvPrice1=(TextView)findViewById(R.id.tvPrice1);
        ivImage1=(ImageView)findViewById(R.id.ivImageUrl1);
        tvBrand=(TextView)findViewById(R.id.tvBrand);
        tvBrand1=(TextView)findViewById(R.id.tvBrand1);
                tvOS=(TextView)findViewById(R.id.tvOS);
                        tvOS1=(TextView)findViewById(R.id.tvOS1);
        tvdisplay=(TextView)findViewById(R.id.tvScreen);
        tvdisplay1=(TextView)findViewById(R.id.tvScreen1);
        tvCamera=(TextView)findViewById(R.id.tvCamera);
        tvCamera1=(TextView)findViewById(R.id.tvCamera1);
                tvAudio=(TextView)findViewById(R.id.tvAudio);
        TvAudio1=(TextView)findViewById(R.id.tvAudio1);
        tvMemory=(TextView)findViewById(R.id.tvMemory);
        tvMemory1=(TextView)findViewById(R.id.tvMemory1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
            tvBrand.setText(product.Brand);
                    tvOS.setText(product.Platform);
            tvdisplay.setText(product.Display);
            tvCamera.setText(product.Camera);
            tvAudio.setText(product.Audio);
            tvMemory.setText(product.Memory);
           // desc.setText(product.Description);

            // float Pprice=product.price;
        }
        cmpproduct();




    }

    public void cmpproduct() {

        final String pname=(String) getIntent().getSerializableExtra("Pname");
       // final String pname="LG L60";
       // Toast.makeText(getApplicationContext(),"hello"+pname,Toast.LENGTH_SHORT).show();
        String url = "https://gcsubhash20.000webhostapp.com/customer/compare.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                showjson(response);
            }

            private void showjson(String response) {
                String name;

                String price;
                String imgurl;
                String tvBrand;
                String tvOS;
                String tvdisplay;
                String tvCamera;
                String Audio,Memory;


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray("results");

                        JSONObject worker = result.getJSONObject(0);
                         name = worker.getString("name");
                        price = worker.getString("price");
                        imgurl= worker.getString("image_url");
                    tvBrand= worker.getString("Brand");
                    tvOS= worker.getString("Platform");
                    tvdisplay= worker.getString("Display");
                    tvCamera= worker.getString("Camera");

                    Audio= worker.getString("Audio");
                   Memory=worker.getString("Memory");


                        //Toast.makeText(getApplicationContext(), price+qty, Toast.LENGTH_LONG).show();


                    tVTitle1.setText(name);
                    tvPrice1.setText("Rs. "+price);
                    String fullUrl = "https://gcsubhash20.000webhostapp.com/customer/" + imgurl;

                    Picasso.with(getApplicationContext())
                            .load(fullUrl)
                            .placeholder(R.drawable.logo)
                            .error(android.R.drawable.stat_notify_error)
                            .into(ivImage1);
                    tvBrand1.setText(tvBrand);
                    tvOS1.setText(tvOS);
                    tvdisplay1.setText(tvdisplay);
                    tvCamera1.setText( tvCamera);
                    tvMemory1.setText(Memory );

                    TvAudio1.setText(Audio);
                    //Toast.makeText(getApplicationContext(), name+price, Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                params.put("name", pname);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


}




}



