package fun.com.example.lenovo.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import fun.com.example.lenovo.recycle.R;

/**
 * Created by SumanThp on 7/20/2017.
 */

public class Notification extends AppCompatActivity {
    Button button;
    String FCM_PREF = "fcm.pref";
    String FCM_TOKEN = "fcm.token";
    String app_server_url = "https://gcsubhash20.000webhostapp.com/customer/getToken.php";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
        //final String token = FirebaseInstanceId.getInstance().getToken();
        button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(FCM_PREF,MODE_PRIVATE);
                //final String token = sharedPreferences.getString(FCM_TOKEN,"");
                final String token = FirebaseInstanceId.getInstance().getToken();
                StringRequest request = new StringRequest(Request.Method.POST, app_server_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),token,Toast.LENGTH_LONG).show();

                    }
                }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("from_token", token);
                        SharedPreferences sharedpref=getSharedPreferences("login", Context.MODE_PRIVATE);

                        String username=sharedpref.getString("name", "invalid");
                        final String cid=sharedpref.getString("Cust_id", "invalid");

                        params.put("uid", cid);

                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);
            }
        });
    }
}
