package fun.com.example.lenovo.profile;

/**
 * Created by srijan on 7/18/2017.
 */


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fun.com.example.lenovo.recycle.R;

public class upload1 extends AppCompatActivity {
    private final int IMG_REQUEST = 1;
    Bitmap bitmap;
    ImageView DP;
    private String uploadDpUrl = "http://10.0.2.2/upload/uploadDP.php";
    private String getDpUrl = "http://10.0.2.2/upload/getDp.php";
    String image_url = "http://10.0.2.2/upload/upolads/";
    //String uploadDpUrl = "http://gcsubhash20.000webhostapp.com/customer/uploadDp.php";
    //String getDpUrl = "http://gcsubhash20.000webhostapp.com/customer/getDp.php";
    //String image_url = "http://gcsubhash20.000webhostapp.com/customer/UserImages/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);

        DP = (ImageView) findViewById(R.id.circleImageView);

        viewDP();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),IMG_REQUEST);
            }
        });
    }

    private void viewDP() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,getDpUrl,null,
                new Response.Listener<JSONObject>(){
                    String disPicture;
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray jsonArray= response.getJSONArray("result");
                            for(int i=0; i<jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                disPicture = jsonObject.getString("url");
                                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();

                            }
                            Picasso.with(getApplicationContext()).load(image_url+disPicture).into(DP);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }

                , new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data!=null){

            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                DP.setImageBitmap(bitmap);
                storeImage2Server();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void storeImage2Server() {

        StringRequest request = new StringRequest(Request.Method.POST, uploadDpUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String Response = jsonObject.getString("result");
                    Toast.makeText(getApplicationContext(),Response,Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("IMAGE", imageToString(bitmap));
                params.put("NAME", "sumanthp");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] imgbytes = baos.toByteArray();
        return Base64.encodeToString(imgbytes,Base64.DEFAULT);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }
}
