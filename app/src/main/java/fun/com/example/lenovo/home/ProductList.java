package fun.com.example.lenovo.home;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import fun.com.example.lenovo.recycle.Product;
import fun.com.example.lenovo.recycle.R;

/**
 * Created by SumanThp on 7/5/2017.
 */

public class ProductList extends Fragment  {

    CardView mobileCard, laptopCard, jacketCard, tShirtCard, shoesCard;
    RecyclerView recyclerView;
    ArrayList<Product> productList;
    int [] icon = {R.drawable.mobile1,
            R.drawable.laptop1,
            R.drawable.jacket1,
            R.drawable.t_shirt1,
            R.drawable.shoes1,
            R.drawable.camera,
            R.drawable.book,
            R.drawable.access};

    String [] text ={"Mobile",
            "Laptop",
            "Jacket",
            "T-Shirt",
            "Shoe",
    "Camera",
    "Book",
    "Accesories"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recycleview,container,false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycle420);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),4));
        ViewCategory adapter = new ViewCategory(getActivity(),icon,text);

        recyclerView.setAdapter(adapter);

        return v;

    }


}
