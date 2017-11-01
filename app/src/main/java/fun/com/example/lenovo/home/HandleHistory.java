package fun.com.example.lenovo.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fun.com.example.lenovo.recycle.R;

/**
 * Created by SumanThp on 7/15/2017.
 */

public class HandleHistory extends AppCompatActivity {
    String[] history;
    String oldValue;
    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    SharedPreferences sharedPreferences;
    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> price = new ArrayList<>();
    ArrayList<String> image = new ArrayList<>();
    ArrayList<String> category = new ArrayList<>();
    ArrayList<String> brand = new ArrayList<>();
    String getProduct_url = "https://gcsubhash20.000webhostapp.com/customer/getSearchedProduct.php";
    String getProductImage_url = "https://gcsubhash20.000webhostapp.com/customer/";

    ArrayList<String> newname = new ArrayList<>();
    ArrayList<String> newprice = new ArrayList<>();
    ArrayList<String> newimage = new ArrayList<>();
    ArrayList<String> newid = new ArrayList<>();
    ArrayList<String> newcategory = new ArrayList<>();
    ArrayList<String> newbrand = new ArrayList<>();
    Toolbar tb;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        rv = (RecyclerView) findViewById(R.id.listMain);
        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        tb = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(tb);
        tb.setTitle("Recent Search");

        oldValue = sharedPreferences.getString("ID", "");
        history = oldValue.split(",");
        //Toast.makeText(getApplicationContext(),history[1],Toast.LENGTH_SHORT).show();
        getData();
    }

    private void getData() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getProduct_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                id.add(jsonObject.getString("pid"));
                                name.add(jsonObject.getString("name"));
                                price.add(jsonObject.getString("price"));
                                image.add(jsonObject.getString("image_url"));
                                category.add(jsonObject.getString("Category"));
                                //brand.add(jsonObject.getString("Brand"));
                            }

                            for (int j = 0; j < history.length; j++) {
                                for (int k = 0; k < name.size(); k++) {
                                    if (history[j].equals(id.get(k))) {
                                        newid.add(id.get(k));
                                        newname.add(name.get(k));
                                        newprice.add(price.get(k));
                                        newimage.add(image.get(k));
                                        newcategory.add(category.get(k));
                                      //  newbrand.add(brand.get(k));
                                    }
                                }
                            }
                            rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            rv.setAdapter(new MyAdapter(getApplicationContext(), newname, newprice, newimage,newcategory));
                            rv.setHasFixedSize(true);
                        } catch (JSONException e) {
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

        /*public static void getAllData(ArrayList<String> ID, ArrayList<String> NAME, ArrayList<String> PRICE, ArrayList<String> IMG, ArrayList<String> CAT, ArrayList<String> BRAND){
            ID = newid;
            NAME = newname;
            PRICE = newprice;
            IMG = newimage;
            CAT = newcategory;
            BRAND = newbrand;
        }*/


        private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

            Context ctx;
            ArrayList<String> newname = new ArrayList<>();
            ArrayList<String> newprice = new ArrayList<>();
            ArrayList<String> newimage = new ArrayList<>();
            ArrayList<String> newid = new ArrayList<>();
            ArrayList<String> newcategory = new ArrayList<>();
            ArrayList<String> newbrand = new ArrayList<>();

            public MyAdapter(Context ctx, ArrayList<String> newname, ArrayList<String> newprice, ArrayList<String> newimage,ArrayList<String> newcategory) {
                this.ctx = ctx;
                this.newname = newname;
                this.newprice = newprice;
                this.newimage = newimage;
                this.newcategory = newcategory;
               // this.newbrand = newbrand;
            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(ctx).inflate(R.layout.search_list_row,parent,false);

                return new ViewHolder(v);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, final int position) {
                holder.tvname.setText(newname.get(position));
                holder.tvprice.setText(newprice.get(position));

                Picasso.with(ctx).load(getProductImage_url+newimage.get(position)).into(holder.iv);
                //Picasso.with(ctx).load(getProductImage_url+"/Mobile"+"/Mi/"+newimage.get(position)).into(holder.iv);
                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(ctx,""+newname.get(position),Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public int getItemCount() {
                return newname.size();
            }

            public class ViewHolder extends RecyclerView.ViewHolder {
                ImageView iv;
                TextView tvname,tvprice;
                CardView cardView;
                View root;
                public ViewHolder(View itemView) {
                    super(itemView);
                    root = itemView;
                    iv = (ImageView) itemView.findViewById(R.id.searchResultImageView);
                    tvname = (TextView) itemView.findViewById(R.id.searchResultTvName);
                    tvprice = (TextView) itemView.findViewById(R.id.searchResultTvPrice);
                    //cardView = (CardView) itemView.findViewById(R.id.cardItem);
                }
            }
        }
    }
