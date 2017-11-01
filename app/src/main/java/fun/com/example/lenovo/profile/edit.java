package fun.com.example.lenovo.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import fun.com.example.lenovo.recycle.R;

public class edit extends AppCompatActivity {
public Button save,cancel;
    String update_about="http://10.0.2.2/customer/update_about.php";
    EditText first_name,last_name,phone_no,address,email;
    public Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        save=(Button)findViewById(R.id.save);
        cancel=(Button)findViewById(R.id.cancel);
        first_name=(EditText)findViewById(R.id.first_name);
        last_name=(EditText)findViewById(R.id.last_name);
        phone_no=(EditText)findViewById(R.id.phone_no);
        address=(EditText)findViewById(R.id.address);
        email=(EditText)findViewById(R.id.email);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest request = new StringRequest(Request.Method.POST, update_about, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String Response = jsonObject.getString("result");
                            Toast.makeText(getApplicationContext(),Response,Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(ctx,"Saved Successfully",Toast.LENGTH_SHORT).show();
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
                        params.put("First_name", first_name.getText().toString());
                        params.put("Last_name", last_name.getText().toString());
                        params.put("Email", email.getText().toString());
                        params.put("Address", address.getText().toString());
                        params.put("phone_no", phone_no.getText().toString());
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(ctx);
                requestQueue.add(request);

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(edit.this,ScrollingActivity.class  );
                startActivity(intent);
            }
        });
    }
}
