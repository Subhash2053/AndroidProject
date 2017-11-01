package fun.com.example.lenovo.recycle;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;
import java.util.Map;

import fun.com.example.lenovo.home.HomePage;

public class RegisterActivity extends AppCompatActivity {
    TextView login;
    private EditText fname,lname,username,Email,Password,address,phone;
    private Button btnSignUp;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        login=(TextView)findViewById(R.id.btn_link_login);
        fname=(EditText)findViewById(R.id.fname);
                lname=(EditText)findViewById(R.id.lname);
                        username=(EditText)findViewById(R.id.user_name);
                                Email=(EditText)findViewById(R.id.email);
                                        Password=(EditText)findViewById(R.id.password);
                                                address=(EditText)findViewById(R.id.address);
                                                        phone=(EditText)findViewById(R.id.phone_n);
        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        btnSignUp=(Button) findViewById(R.id.btn_signup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Adding you ...");
                if (!progressDialog.isShowing())
                    progressDialog.show();

                submitForm();
            }
        });
       login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
            }
        });

    }

    private void submitForm() {
        HashMap data =new HashMap();
        data.put("fname",fname.getText().toString());
        data.put("lname",lname.getText().toString());
        data.put("username",username.getText().toString());
        data.put("phone",phone.getText().toString());
        data.put("password",Password.getText().toString());
        data.put("email",Email.getText().toString());
        data.put("address",address.getText().toString());


        PostResponseAsyncTask task = new PostResponseAsyncTask(RegisterActivity.this, data,
                new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d("RegisterActivity", s);
                        if(s.contains("success")){
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Hi " + username.getText().toString() +", You are successfully Added!" , Toast.LENGTH_LONG).show();

                            Intent in = new Intent(RegisterActivity.this,LoginActivity.class);

                            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(in);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Register Unsucessfull" , Toast.LENGTH_LONG).show();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }
                });

        task.execute("https://gcsubhash20.000webhostapp.com/customer/register.php");


    }
}
