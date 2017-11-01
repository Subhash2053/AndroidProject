package fun.com.example.lenovo.profile;

        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.android.volley.AuthFailureError;
        import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;
        import com.squareup.picasso.Picasso;

        import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Map;

        import fun.com.example.lenovo.recycle.R;

public class about extends AppCompatActivity {





    TextView name,phone,email,address;
    ImageView displayPicture;

    public String insert_url="https://gcsubhash20.000webhostapp.com/customer/userData.php";


private String image_url="http://10.0.2.2/connect/image";
    //String uploadDpUrl="http://gcsubash20.000webhost.com/customer/uploadDP.php";
    //String getDpUrl="http://gcsubash20.000webhost.com/customer/getDP.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setHomeButtonEnabled(true);
        name=(TextView)findViewById(R.id.userName);
       phone=(TextView)findViewById(R.id.contactText);
       email=(TextView)findViewById(R.id.emailText);
       address=(TextView)findViewById(R.id.addressText);
        displayPicture=(ImageView)findViewById(R.id.displayPicture) ;





        getCartInformation();

       /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(about.this,edit.class );
                startActivity(intent);

            }
        });
        */
    }








    public void getCartInformation() {
        SharedPreferences sharedpref=getSharedPreferences("login", Context.MODE_PRIVATE);

        String username=sharedpref.getString("name", "invalid");
        final String cid=sharedpref.getString("Cust_id", "invalid");






        StringRequest request = new StringRequest(Request.Method.POST, insert_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                showjson(response);
            }

            private void showjson(String response) {
                String Username;
                String Image;
                String Email;
                String phone_no;
                String Address;


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray("result");

                    JSONObject worker = result.getJSONObject(0);
                    Username=worker.getString("username");

                                Image=worker.getString("Image");

                                Email=worker.getString("Email");
                                phone_no=worker.getString("phone_no");
                                Address=worker.getString("Address");


                            name.setText(Username);
                    email.setText(Email);
                    phone.setText(phone_no);
                    address.setText(Address);
                    String fullUrl = "https://gcsubhash20.000webhostapp.com/customer/profile/" +Image;

                    Picasso.with(getBaseContext())
                            .load(fullUrl)
                            .placeholder(R.drawable.logo)
                            .error(android.R.drawable.stat_notify_error)
                            .into(displayPicture);


                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }

                , new Response.ErrorListener(){

            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("uid", cid);

                return params;
            }
        }
                ;



        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }
}
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.infoEdit) {
            return true;
        }
        if (id == R.id.history) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}*/
