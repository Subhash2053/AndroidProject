package fun.com.example.lenovo.cart;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import fun.com.example.lenovo.recycle.R;

public class Confirm2Activity extends AppCompatActivity {
    TextView payid;
    Button order;
    EditText no,add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm2);
        payid=(TextView)findViewById(R.id.payid);
        order=(Button)findViewById(R.id.order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        no=(EditText)findViewById(R.id.number);
        add=(EditText)findViewById(R.id.address);

        SharedPreferences sharedpref=getSharedPreferences("login", Context.MODE_PRIVATE);

        String username=sharedpref.getString("name", "invalid");
        final String cid=sharedpref.getString("Cust_id", "invalid");

        final String id= (String) getIntent().getSerializableExtra("PaymentDetails");
        final String am= (String) getIntent().getSerializableExtra("PaymentAmount");
        final String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        payid.setText(""+id);


        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String num=no.getText().toString();
                final String addr=add.getText().toString();
                String order_url = "https://gcsubhash20.000webhostapp.com/customer/order.php";

                StringRequest request = new StringRequest(Request.Method.POST, order_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),"Saved Successfully",Toast.LENGTH_SHORT).show();
                        finish();
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
                        params.put("Amount",am);
                        params.put("Date", date);
                        params.put("id", id);
                        params.put("address", addr);
                        params.put("number", num);
                        params.put("cust_id",cid);

                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);



            }


        }
        );

    }
        }





