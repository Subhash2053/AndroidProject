package fun.com.example.lenovo.home;

import android.app.Activity;

/**
 * Created by Lenovo on 8/3/2017.
 */



import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fun.com.example.lenovo.recycle.*;
import fun.com.example.lenovo.recycle.ProductAdapter;

/**
 * Created by SumanThp on 7/8/2017.
 */

public class ViewCategory extends RecyclerView.Adapter<ViewCategory.ViewHolder> {
    // public ArrayList<String> name = new ArrayList<>();
    // public ArrayList<String> price = new ArrayList<>();
    // public ArrayList<String> image_name = new ArrayList<>();

    Context ctx;
    int temp_pos = 0;
    String brand;
    int[] icon;
    String[] titles;



    public ViewCategory(Context context, int[] icon, String[] titles){
        this.ctx = context;
        this.icon = icon;
        this.titles=titles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categories_layout, parent, false);

        ViewHolder ViewHolder = new ViewHolder(view);
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //final Product product1 = products.get(temp_pos+1);
        //temp_pos++;

        holder.tvName.setText(titles[position]);



        // holder.tvPrice1.setText("Rs." + product1.price);
        holder.IvImage.setImageResource(icon[position]);
        if(titles[position]=="Mobile")
        {
            holder.IvImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(ctx,Mobile.class);
                    ctx.startActivity(in);
                }
            });
        }






    }

    @Override
    public int getItemCount() {

        return 8;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cvProduct;

        public TextView tvName;
        public ImageView IvImage;


        public ViewHolder(View itemView) {
            super(itemView);
            cvProduct = (CardView)itemView.findViewById(R.id.Card);
            IvImage = (ImageView)itemView.findViewById(R.id.cat_mob);
            tvName = (TextView)itemView.findViewById(R.id.tvname);



        }
    }
}
