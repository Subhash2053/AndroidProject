package fun.com.example.lenovo.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kosalgeek.android.json.JsonConverter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import fun.com.example.lenovo.recycle.Main2Activity;
import fun.com.example.lenovo.recycle.Product;
import fun.com.example.lenovo.recycle.R;
import fun.com.example.lenovo.recycle.Singleton;

/**
 * Created by SumanThp on 7/13/2017.
 */

public class Recommend extends Fragment {

    String[] history;
    String oldValue;
    SharedPreferences sharedPreferences;
    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    TextView tv;
    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> price = new ArrayList<>();
    ArrayList<String> image = new ArrayList<>();
    ArrayList<String> category = new ArrayList<>();
    ArrayList<String> brand = new ArrayList<>();

    ArrayList<String> newname = new ArrayList<>();
    ArrayList<String> newprice = new ArrayList<>();
    ArrayList<String> newimage = new ArrayList<>();
    ArrayList<String> newid = new ArrayList<>();
    ArrayList<String> newcategory = new ArrayList<>();
    ArrayList<String> newbrand = new ArrayList<>();
    String getProduct_url = "https://gcsubhash20.000webhostapp.com/customer/getSearchedProduct.php";
    String getProductImage_url = "https://gcsubhash20.000webhostapp.com/customer/";
    int[] images={R.drawable.spinner, R.drawable.spinner,R.drawable.spinner,R.drawable.spinner,R.drawable.spinner,R.drawable.spinner,R.drawable.spinner};
    String finalResult=null;
    ArrayList<String> recycleID = new ArrayList<>();
    ArrayList<String> recommendImages = new ArrayList<>();
    ArrayList<String> recommendname = new ArrayList<>();
    ArrayList<String> recommendprice = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recommend,container,false);

        rv = (RecyclerView) v.findViewById(R.id.recommendRecycle);
        tv = (TextView) v.findViewById(R.id.recommendTV);

        sharedPreferences = getActivity(). getSharedPreferences("MyPref", getActivity().MODE_PRIVATE);

        oldValue = sharedPreferences.getString("ID", "");
        history = oldValue.split(",");
        if(history.length >3)
                getData();
        return v;
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
                                brand.add(jsonObject.getString("Brand"));
                            }

                            for (int j = 0; j < history.length; j++) {
                                for (int k = 0; k < name.size(); k++) {
                                    if (history[j].equals(id.get(k))) {
                                        newid.add(id.get(k));
                                        newname.add(name.get(k));
                                        newprice.add(price.get(k));
                                        newimage.add(image.get(k));
                                        newcategory.add(category.get(k));
                                        newbrand.add(brand.get(k));
                                    }
                                }
                            }

                            compareData(newbrand,newprice);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                , new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjectRequest);
    }

    //Comparing each items
    private void compareData(ArrayList<String> newbrand, ArrayList<String> newprice) {

        Hashtable<String, Integer> ht = new Hashtable<>();
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<Integer> values = new ArrayList<>();
        String current;
        Collections.sort(newbrand);
        int count = 1;
        int pos = 0;
        int max;


        for (int i = 0; i < newbrand.size() - 1; i++) {
            current = newbrand.get(i);
            pos++;
            if (current.equals(newbrand.get(pos))) {
                count++;
            } else {
                values.add(count);
                count = 1;
            }
            if (!keys.contains(current)) {
                keys.add(current);
            }
        }
        values.add(count);

        max = Collections.max(values);

        for (int l = 0; l < values.size(); l++) {
            if (values.get(l) == max) {
                finalResult = keys.get(l);
            }
        }
        //Toast.makeText(getActivity(),finalResult,Toast.LENGTH_SHORT).show();

        for (int m = 0; m < brand.size();m++){
            if (finalResult.equals(brand.get(m))){
                recycleID.add(id.get(m));
            }
        }

       // Toast.makeText(getActivity(),""+recycleID.size(),Toast.LENGTH_SHORT).show();

        if(recycleID.size()>history.length) {
            ArrayList<String> temp = new ArrayList<>();
            for(int n=0;n<recycleID.size();n++) {
                for (int p = 0; p < history.length; p++) {
                    if (recycleID.get(n).equals(history[p])) {
                        temp.add(recycleID.get(n));
                    }
                }
            }
            for(int del=0;del<temp.size();del++){
                recycleID.remove(temp.get(del));
            }


            //Toast.makeText(getActivity(),""+recycleID.size(), Toast.LENGTH_SHORT).show();
        }
        else {
            ArrayList<String> temp = new ArrayList<>();
            for(int n=0;n<history.length;n++) {
                for (int p = 0; p < recycleID.size(); p++) {
                    if (history[n].equals(recycleID.get(p))) {
                        temp.add(recycleID.get(p));
                    }
                }
            }
            for(int del=0;del<temp.size();del++){
                recycleID.remove(temp.get(del));
            }

        }





        viewRecommend(recycleID);

    }


    //Does the job of recommending items
    private void viewRecommend(ArrayList<String> recycleID) {

        for(int first=0;first<recycleID.size();first++){
            for(int second=0;second<id.size();second++) {
                if (recycleID.get(first).equals(id.get(second))){
                    recommendImages.add(image.get(second));
                    recommendname.add(name.get(second));
                    recommendprice.add(price.get(second));
                }
            }
        }

        //Toast.makeText(getActivity(),""+recycleID.size(),Toast.LENGTH_SHORT).show();
        rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        rv.setAdapter(new MyAdapter(getActivity(),recommendImages,recommendname,recommendprice,finalResult));
    }


    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        ArrayList<String> img =new ArrayList<>();
        ArrayList<String> name =new ArrayList<>();
        ArrayList<String> price =new ArrayList<>();
        Context ctx;
        String comp;
        public MyAdapter(Context ctx,ArrayList<String> img,ArrayList<String> name,ArrayList<String> price,String comp) {
            this.ctx = ctx;
            this.img = img;
            this.name=name;
            this.price=price;
            this.comp = comp;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(ctx).inflate(R.layout.recommend_row,parent,false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
           final  String pname=name.get(position);
            holder.t1.setText(name.get(position));
            holder.t2.setText("Rs."+price.get(position));
            Picasso.with(ctx).load(getProductImage_url+img.get(position)).into(holder.iv);
            holder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                                    Intent intent=new Intent(getActivity(), Main2Activity.class);
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
                                        Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                    ){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();



                            params.put("pname",pname);

                            return params;
                        }


                    };

                    Singleton.getInstance(getActivity()).addToRequestQueue(stringRequest);


                }
            });
        }

        @Override
        public int getItemCount() {
            return img.size();
        }

        public class ViewHolder extends  RecyclerView.ViewHolder{
            ImageView iv;
            TextView t1,t2;
            public ViewHolder(View itemView) {
                super(itemView);
                iv = (ImageView) itemView.findViewById(R.id.recommendImage);
                t1=(TextView) itemView.findViewById(R.id.tvName);
                t2=(TextView) itemView.findViewById(R.id.tvPrice);
            }
        }
    }
}
