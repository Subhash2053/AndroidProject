package fun.com.example.lenovo.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kosalgeek.android.json.JsonConverter;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fun.com.example.lenovo.cart.cardActivity;
import fun.com.example.lenovo.profile.ScrollingActivity;
import fun.com.example.lenovo.recycle.*;

import static android.R.attr.fragment;

/**
 * Created by suman on 5/27/2017.
 */

public class HomePage extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FragmentManager fragManager = getFragmentManager();
    MaterialSearchView sv;
    String getProduct_url = "https://gcsubhash20.000webhostapp.com/customer/getSearchedProduct.php";
    String getProductImage_url = "http://10.0.2.2/customer/Images/Category/";
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> price = new ArrayList<>();
    ArrayList<String> image = new ArrayList<>();
    ArrayList<String> temp = new ArrayList<>();
    ArrayList<String> id = new ArrayList<>();
    Toolbar tb;
    String[] ok = {"ss","sss","ssss","sesese","sdfsd","sjhbjhjbhj","svvfgb","sdsfd"};
    String[] suggestion;
    private int[] DataSet;
    boolean searchState;
    HandleHistory handleHistory;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    StringBuilder sb = new StringBuilder();
    String tempId,oldValue;
    boolean value;
    @Override
    public void onBackPressed() {
        if(searchState) {
            sv.closeSearch();
        }
        else
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setNegativeButton("No", null).show();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        sharedPreferences = getSharedPreferences("MyPref",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        sv = (MaterialSearchView) findViewById(R.id.searchView);
        sv.setEllipsize(true);
        sv.setSubmitOnClick(true);
        sv.bringToFront();
        sv.setSuggestionIcon(getApplicationContext().getResources().getDrawable(android.R.drawable.ic_menu_search));
        populateSuggestion();
        showSearchView();
        SharedPreferences sharedpref=getSharedPreferences("login", Context.MODE_PRIVATE);

        String username=sharedpref.getString("name", "invalid");
        String cid=sharedpref.getString("Cust_id", "invalid");
        if (username=="invalid"){
            Intent in = new Intent(HomePage.this,LoginActivity.class);
            startActivity(in);
        }

        fragManager.beginTransaction().replace(R.id.fragment_container,new Home()).commit();

    bottomNavigationView = (BottomNavigationView) findViewById(R.id.bnv);
    bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()){
                case R.id.home :
                    fragManager.beginTransaction().replace(R.id.fragment_container,new Home()).commit();
                    break;
                case R.id.option2:
                    fragManager.beginTransaction().replace(R.id.fragment_container,new Tab2()).commit();
                    break;
                case R.id.option3:
                    fragManager.beginTransaction().replace(R.id.fragment_container,new Tab3() ).commit();
                    break;
            }
            return true;
        }
    });
}


    private void populateSuggestion() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,getProduct_url,null,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray jsonArray= response.getJSONArray("result");
                            suggestion = new String[jsonArray.length()];
                            for(int i=0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                id.add(jsonObject.getString("pid"));
                                name.add(jsonObject.getString("name"));
                                price.add(jsonObject.getString("price"));
                                image.add(jsonObject.getString("image_url"));
                            }
                            for(int j=0;j<name.size();j++)
                            {
                                suggestion[j] = name.get(j);
                            }

                            sv.setSuggestions(suggestion);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }

                , new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }

    private void showSearchView() {

        sv.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sv.closeSearch();
                saveSearch(query);
                ShowSearch(query);
                //Toast.makeText(getApplicationContext(),query,Toast.LENGTH_LONG).show();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return  true;
            }
        });
    }

    private void ShowSearch(final String query)

    {

        String url = "https://gcsubhash20.000webhostapp.com/customer/search.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("HomePage", response);
                        // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();


                        ArrayList<Product> products = new JsonConverter<Product>()
                                .toArrayList(response, Product.class);
                        Intent intent=new Intent(getApplicationContext(), Main2Activity.class);
                        final Product product = products.get(0);
                        intent.putExtra("product", product);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null) {
                            Log.d("HomePage", error.toString());
                            Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();


                params.put("pname", query);

                return params;
            }


        };

        Singleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


    }

    private void saveSearch(String query) {

        for (int i = 0; i < name.size(); i++) {
            if (query.equals(name.get(i))) {
                tempId = id.get(i);
                break;
            }
        }

        if (sharedPreferences.contains("ID")) {
            sb.setLength(0);
            oldValue = sharedPreferences.getString("ID", "");
            sb.append(tempId).append(",").append(oldValue);
            editor.putString("ID", sb.toString());
            editor.apply();
        }else{
            editor.putString("ID",sb.append(tempId).append(",").toString());
            editor.apply();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchAction = menu.findItem(R.id.search);
        sv.setMenuItem(searchAction);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.search);

        if(item.getItemId()==R.id.recentSearch){
            //Toast.makeText(getApplicationContext(),sharedPreferences.getString("ID",""),Toast.LENGTH_SHORT).show();
           startActivity(new Intent(HomePage.this,HandleHistory.class));
        }
        if(item.getItemId()==R.id.cart){
            //Toast.makeText(getApplicationContext(),sharedPreferences.getString("ID",""),Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomePage.this,cardActivity.class));
        }
        if(item.getItemId()==R.id.profile){
            //Toast.makeText(getApplicationContext(),sharedPreferences.getString("ID",""),Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomePage.this,ScrollingActivity.class));
        }
        if(item.getItemId()==R.id.not){
            //Toast.makeText(getApplicationContext(),sharedPreferences.getString("ID",""),Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomePage.this,Notification.class));
        }
        return  true;
    }
}

