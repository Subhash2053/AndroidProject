package fun.com.example.lenovo.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import fun.com.example.lenovo.recycle.R;

/**
 * Created by SumanThp on 7/4/2017.
 */

public class Mobile extends AppCompatActivity{
    GridView gridView;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String[] mobile={"apple","samsung","sony","lg","htc","huawei"};
    String[] mobile1={};
    int[] images = {R.drawable.apple1,
                    R.drawable.sams1,
                    R.drawable.sony1,
                    R.drawable.lg1,
                    R.drawable.htc1,
                    R.drawable.huawei2};
    int[] images1 = {R.drawable.zte,
            R.drawable.oppo1,
            R.drawable.moto1,
            R.drawable.micromax1,
            R.drawable.mi1,
            R.drawable.oneplus1};
    ImageView iv1,iv2,iv3,iv4,iv5;
    ArrayList<Integer> imgList;
    Toolbar tb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_category);

       recyclerView = (RecyclerView) findViewById(R.id.category_recycle);
        tb = (Toolbar) findViewById(R.id.tb);
        setSupportActionBar(tb);
        tb.setTitle("Mobile");
       tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
         adapter = new ProductAdapter(Mobile.this,images,images1,mobile);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }
}
