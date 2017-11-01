package fun.com.example.lenovo.recycle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.kosalgeek.android.md5simply.MD5;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import fun.com.example.lenovo.home.HomePage;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{


    // UI references.
    private EditText nameView;
    private EditText PasswordView;
    TextView signup;
    private CheckBox chk;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private View loginFormView;
    final String TAG= this.getClass().getName();
    boolean checkFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
       nameView = (EditText) findViewById(R.id.name);
        PasswordView = (EditText) findViewById(R.id.password);
        chk=(CheckBox)findViewById(R.id.remember);
        signup=(TextView)findViewById(R.id.signup);
        TextView show=(TextView)findViewById(R.id.show);

        show.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedpref=getSharedPreferences("login",0);
                SharedPreferences.Editor editor=sharedpref.edit();
                editor.putString("name",nameView.getText().toString());
                // editor.putString("Password", String.valueOf(PasswordView.getText()));

                editor.apply();
            }
        });


        signup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginActivity.this,RegisterActivity.class);

                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
            }
        });


        Button SignIn = (Button) findViewById(R.id.signin);





        SignIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {




                attemptLogin();
            }
        });
        /*
        pref = getSharedPreferences("login.conf", Context.MODE_PRIVATE);
        editor=pref.edit();
        String username = pref.getString("username", "");
        String password = pref.getString("password", "");
        Log.d(TAG, pref.getString("password", ""));
        HashMap data = new HashMap();
        data.put("txtUsername", username);
        data.put("txtPassword", password);*/
/*
        String username=pref.getString("username", "");
        TextView st=(TextView)findViewById(R.id.show);
        st.setText(username);
        */

       // Toast.makeText(getApplicationContext(), , Toast.LENGTH_LONG).show();

    }

    private void attemptLogin() {
        HashMap data =new HashMap();
        data.put("txtUsername",nameView.getText().toString());
        data.put("txtPassword",PasswordView.getText().toString());

/*
        editor.putString("Username",nameView.getText().toString());
        editor.putString("Password", MD5.encrypt(PasswordView.getText().toString()));
        editor.apply();

        Log.d(TAG,pref.getString("Username",""));
        Log.d(TAG,pref.getString("Password",""));
        */

        PostResponseAsyncTask task = new PostResponseAsyncTask(LoginActivity.this, data,
                new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d(TAG, s);
                        if(s.contains("success")){
                            String id = s.replaceAll("[^0-9]", "");
                            SharedPreferences sharedpref=getSharedPreferences("login",0);
                            SharedPreferences.Editor editor=sharedpref.edit();
                            editor.putString("name",nameView.getText().toString());
                             editor.putString("Cust_id",id );

                            editor.apply();

                            final String token = FirebaseInstanceId.getInstance().getToken();
                            String app_server_url = "https://gcsubhash20.000webhostapp.com/customer/getToken.php";
                            StringRequest request = new StringRequest(Request.Method.POST, app_server_url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                   // Toast.makeText(getApplicationContext(),token,Toast.LENGTH_LONG).show();

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


                           // Toast.makeText(getApplicationContext(),s.substring(0, 1) , Toast.LENGTH_LONG).show();

                            Intent in = new Intent(LoginActivity.this,HomePage.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(in);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Username and password not match" , Toast.LENGTH_LONG).show();

                        }
                    }
                });

        task.execute("https://gcsubhash20.000webhostapp.com/customer/index.php");
    }


    }



