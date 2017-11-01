package fun.com.example.lenovo.home;


import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.android.json.JsonConverter;
import com.miguelcatalan.materialsearchview.MaterialSearchView;


import java.util.ArrayList;

import fun.com.example.lenovo.recycle.*;

import static android.content.ContentValues.TAG;
import static android.support.v7.recyclerview.R.attr.layoutManager;

/**
 * Created by suman on 5/27/2017.
 */

public class Home extends Fragment {
    private RecyclerView mRecyclerView, mRecyclerViewItem;
    private RecyclerView.LayoutManager mLayoutManager,mLayoutManagerItem;
    private RecyclerView.Adapter mAdapter,mAdapterItem;
    //private int [] mDataSet={R.drawable.shoe,R.drawable.shoe,R.drawable.shoe,R.drawable.shoe,R.drawable.shoe,R.drawable.shoe,R.drawable.shoe,R.drawable.shoe};
    private int[] mItems = {R.drawable.s9,R.drawable.s9,R.drawable.s9,R.drawable.s9,R.drawable.s9};
    private String [] itemList={"Recommendation","Mobile","Shoes"};
    FragmentManager fm;
    Button b;
    SharedPreferences sharedPreferences;
    String oldValue;
    String[] history;

    @Override
      public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.homelayout,container,false);
        fm = getFragmentManager();

        sharedPreferences = getActivity().getSharedPreferences("MyPref",getActivity().MODE_PRIVATE);

        oldValue = sharedPreferences.getString("ID", "");
        history = oldValue.split(",");

        fm.beginTransaction().replace(R.id.adsLayout,new AdFragment()).commit();
        fm.beginTransaction().replace(R.id.categoryLayout,new ProductList()).commit();

        if(history.length > 3) {
            fm.beginTransaction().replace(R.id.recommendFragment, new Recommend()).commit();
        }

        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        //mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        String url = "https://gcsubhash20.000webhostapp.com/customer/product.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Home", response);
                        // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        ArrayList<Product> productList = new JsonConverter<Product>()
                                .toArrayList(response, Product.class);

                        fun.com.example.lenovo.recycle.ProductAdapter adapter = new fun.com.example.lenovo.recycle.ProductAdapter(getActivity(), productList);

                        mRecyclerView.setAdapter(adapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null) {
                            Log.d(TAG, error.toString());
                            Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

        Singleton.getInstance(getActivity()).addToRequestQueue(stringRequest);




        //fm.beginTransaction().replace(R.id.recommendFragment, new Recommend()).commit();
        //mRecyclerView = (RecyclerView) rootview.findViewById(R.id.recycler_view);
        /*mRecyclerViewItem = (RecyclerView) rootview.findViewById(R.id.recycler_view1);
        //mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        //mLayoutManagerItem = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        mLayoutManagerItem = new LinearLayoutManager(getActivity());
        //mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerViewItem.setLayoutManager(mLayoutManagerItem);
        //mAdapter = new MainAdapter(getActivity(),mDataSet);
        //mAdapterItem = new MainAdapterItem(getActivity(),mItems);
        mAdapterItem = new MainAdapterHorItem(getActivity(),itemList,mItems);
        //mRecyclerView.setAdapter(mAdapter);
        mRecyclerViewItem.setAdapter(mAdapterItem);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerViewItem.setHasFixedSize(true);*/


        return rootview;
    }


}


