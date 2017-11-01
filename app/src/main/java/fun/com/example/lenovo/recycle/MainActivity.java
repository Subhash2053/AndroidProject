package fun.com.example.lenovo.recycle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.android.json.JsonConverter;
import com.miguelcatalan.materialsearchview.MaterialSearchView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fun.com.example.lenovo.profile.ScrollingActivity;
import fun.com.example.lenovo.cart.cardActivity;
//import com.journaldev.searchview.databinding.ActivityMainBinding;



public class MainActivity extends AppCompatActivity  {
    private final int SPLASH_DISPLAY_LENGTH = 1000;


    final String TAG = "MainActivity";
    //SharedPreferences pref;
    //SharedPreferences.Editor editor;
TextView user;
    //ActivityMainBinding activityMainBinding;
    private ListView listView;
    private ArrayList<String> stringArrayList;
   // private ListViewAdapter adapter;
   MaterialSearchView searchView;
    final ArrayList<String> list = new ArrayList<String>();
    String[] arr;

    RecyclerView rvItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        user=(TextView)findViewById(R.id.itemTitle);

       // initToolbar();

        SharedPreferences sharedpref=getSharedPreferences("login", Context.MODE_PRIVATE);

       String username=sharedpref.getString("name", "invalid");
        String cid=sharedpref.getString("Cust_id", "invalid");
        if (username=="invalid"){
            Intent in = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(in);
        }
       //Toast.makeText(getApplicationContext(),"Hello"+ username, Toast.LENGTH_LONG).show();


       user.setText("Hello, " + username );
       searchviewcode();


        //  String password = pref.getString("password", "");




        rvItem = (RecyclerView) findViewById(R.id.rvItem);
        rvItem.setHasFixedSize(true);

        rvItem.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        String url = "https://gcsubhash20.000webhostapp.com/customer/product.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                       // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        ArrayList<Product> productList = new JsonConverter<Product>()
                                .toArrayList(response, Product.class);

                        ProductAdapter adapter = new ProductAdapter(getApplicationContext(), productList);

                        rvItem.setAdapter(adapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null) {
                            Log.d(TAG, error.toString());
                            Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

        Singleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


    }

    private void searchviewcode() {
        getdata();
        searchView = (MaterialSearchView) findViewById(R.id.search_view1);
        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        searchView.setEllipsize(true);
        searchView.setVoiceSearch(true);

        //searchView.setBackgroundColor(Color.TRANSPARENT);
        searchView.bringToFront();


        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });
    }

    void getdata(){




        String url = "https://gcsubhash20.000webhostapp.com/customer/getName.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        showJSON(response);


                    }

                    private void showJSON(String response) {
                        // ArrayList<String> stringArrayList = new ArrayList<>();


                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray result = jsonObject.getJSONArray("result");
                            // stringArray = new String[result.length()];
                            for (int i = 0; i < result.length(); i++) {
                                JSONObject worker = result.getJSONObject(i);

                                String Name=worker.getString("name");

                                list.add(Name);

                                //stringArray[i] = worker.getString("prod_name");

                                //Toast.makeText(getApplicationContext(), price+qty, Toast.LENGTH_LONG).show();

                            }
                            arr = list.toArray(new String[list.size()]);




                            //stringArray =   product.toArray(new Product[product.size()]);
                            //System.out.println(array[0] + " " + array[1] + " " + array[2]);


                            //stringArray =  stringArrayList.toArray(new String[stringArrayList.size()]);





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null) {
                            Log.d(TAG, error.toString());
                            Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

        Singleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


    }



//    private ArrayList<Item> generatedDummy(){
//        ArrayList<Item> list = new ArrayList<>();
//        for(int i = 0; i < 50; i++){
//            if(i % 3 == 0){
//                Item item = new Item();
//                item.id = i;
//                item.text = "Angkor Wat " + i;
//                item.img = "https://lonelyplanetwp.imgix.net/2016/01/angkor-wat-with-water.jpg";
//                list.add(item);
//            }
//            else if(i % 7 == 0){
//                Item item = new Item();
//                item.id = i;
//                item.text = "Bayon " + i;
//                item.img = "http://www.livingif.com/wp-content/gallery/bayon/bayon-cambodia-11.jpg";
//                list.add(item);
//            }
//            else if(i % 11 == 0){
//                Item item = new Item();
//                item.id = i;
//                item.text = "Something text " + i;
//                item.img = "http://www.aangkortourguide.com/images/cambodia_bayon.jpg";
//                list.add(item);
//            }
//            else{
//                Item item = new Item();
//                item.id = i;
//                item.text = "Dummy text " + i;
//                item.img = "http://www.worldwidetoursagency.com/wp-content/uploads/2016/01/phnom-penh-tour_sinhcafe-travel.jpg";
//                list.add(item);
//            }
//
//
//        }
//        return list;
//    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
       MenuItem item = menu.findItem(R.id.action_search);
      searchView.setMenuItem(item);
              return true;
    }
    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this,
                    ScrollingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        if (id == R.id.logout) {
            SharedPreferences myPrefs = getSharedPreferences("login",
                    MODE_PRIVATE);
            SharedPreferences.Editor editor = myPrefs.edit();
            editor.clear();
            editor.commit();
            //AppState.getSingleInstance().setLoggingOut(true);

            Log.d(TAG, "Now log out and start the activity login");
            Intent intent = new Intent(MainActivity.this,
                    LoginActivity.class);
           intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        }
        if (id == R.id.cart_item) {
            Intent myIntent = new Intent(MainActivity.this, cardActivity.class);
            //myIntent.putExtra("key", value); //Optional parameters
            MainActivity.this.startActivity(myIntent);
        }




        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

