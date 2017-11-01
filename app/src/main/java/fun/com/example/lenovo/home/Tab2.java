package fun.com.example.lenovo.home;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fun.com.example.lenovo.profile.ScrollingActivity;
import fun.com.example.lenovo.profile.about;
import fun.com.example.lenovo.profile.edit;
import fun.com.example.lenovo.profile.order1;
import fun.com.example.lenovo.profile.upload1;
import fun.com.example.lenovo.recycle.LoginActivity;
import fun.com.example.lenovo.recycle.R;
import fun.com.example.lenovo.recycle.Singleton;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by suman on 5/27/2017.
 */

public class Tab2 extends Fragment {
    private static final int IMG_REQUEST =1 ;
    Bitmap bitmap;
    ImageView i1,i2,i3,i4,DP;
    TextView t1,t2,t3;
    String TAG= String.valueOf(getActivity());
    private String uploadDpUrl = "https://gcsubhash20.000webhostapp.com/customer/uploadDP.php";
    CardView cv;
    FloatingActionButton i5;
   // private String getDpUrl = "https://gcsubhash20.000webhostapp.com/customer/profile/getDp.php";
    //String image_url = "http://10.0.2.2/upload/uploads";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview =inflater.inflate(R.layout.activity_scrolling,container,false);


        cv = (CardView)  rootview.findViewById(R.id.card_view2);
        i1=(ImageView) rootview.findViewById(R.id.about);
        i2=(ImageView) rootview.findViewById(R.id.order);
        i3=(ImageView) rootview.findViewById(R.id.wishlist);
        i4=(ImageView) rootview.findViewById(R.id.notification);
        t1=(TextView) rootview.findViewById(R.id.t1);
        t2=(TextView) rootview.findViewById(R.id.t2);
        t3=(TextView) rootview.findViewById(R.id.t3);
        i5=   (FloatingActionButton) rootview.findViewById(R.id.fab);
        DP=(ImageView)rootview.findViewById(R.id.circleImageView);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(getActivity(),about.class  );
                startActivity(intent);
            }
        });

        i5.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),IMG_REQUEST);
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(getActivity(),order1.class  );
                startActivity(intent);
            }
        });
        SharedPreferences sharedpref=this.getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        String cid=sharedpref.getString("Cust_id", "invalid");
        HashMap data =new HashMap();
        data.put("uid",cid);

        PostResponseAsyncTask task = new PostResponseAsyncTask(this.getActivity(), data,
                new AsyncResponse() {


                    @Override
                    public void processFinish(String s) {

                        Log.d(TAG, s);
                        String fullUrl = "https://gcsubhash20.000webhostapp.com/customer/profile/" + s;
                        Picasso.with(getActivity())
                                .load(fullUrl)
                                .placeholder(R.drawable.logo)
                                .error(android.R.drawable.stat_notify_error)
                                .into(DP);


                    }
                });

        task.execute("https://gcsubhash20.000webhostapp.com/customer/getDp.php");


        return rootview;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data!=null){

            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(),path);
                DP.setImageBitmap(bitmap);
                storeImage2Server();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void storeImage2Server() {
        SharedPreferences sharedpref=this.getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        final String cid=sharedpref.getString("Cust_id", "invalid");
        final String username=sharedpref.getString("name", "invalid");
        StringRequest request = new StringRequest(Request.Method.POST, uploadDpUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String Response = jsonObject.getString("result");
                    Toast.makeText(getActivity(),Response,Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("image", imageToString(bitmap));
                params.put("cid", cid);
                params.put("name", username);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] imgbytes = baos.toByteArray();
        return Base64.encodeToString(imgbytes,Base64.DEFAULT);

    }


}







