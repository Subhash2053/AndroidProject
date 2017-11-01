package fun.com.example.lenovo.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fun.com.example.lenovo.recycle.*;

/**
 * Created by SumanThp on 7/8/2017.
 */

public class ViewMobile extends RecyclerView.Adapter<ViewMobile.ViewHolder> {
   // public ArrayList<String> name = new ArrayList<>();
  // public ArrayList<String> price = new ArrayList<>();
   // public ArrayList<String> image_name = new ArrayList<>();
    private ArrayList<Product> products;
    public String getMiImage_url = "https://gcsubhash20.000webhostapp.com/customer";
    Context ctx;
    int temp_pos = 0;
    String brand;



    public ViewMobile(Context context, ArrayList<Product> products){
        this.ctx = context;
        this.products = products;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mi_layout, parent, false);

        ViewHolder ViewHolder = new ViewHolder(view);
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Product product = products.get(position);
        //final Product product1 = products.get(temp_pos+1);
        //temp_pos++;

        holder.tvName.setText(product.name);

        holder.tvPrice.setText("Rs." + product.price);
        //holder.tvName1.setText(product1.name);

       // holder.tvPrice1.setText("Rs." + product1.price);

        String fullUrl = "https://gcsubhash20.000webhostapp.com/customer/" + product.image_url;

        Picasso.with(ctx)
                .load(fullUrl)
                .placeholder(R.drawable.logo)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.ivImageUrl);
        /*String fullUrl1 = "https://gcsubhash20.000webhostapp.com/customer/" + product1.image_url;

        Picasso.with(ctx)
                .load(fullUrl1)
                .placeholder(R.drawable.logo)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.ivImageUrl1);
                */
        holder.ivImageUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,product.name+product.pid,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ctx, Main2Activity.class);
                intent.putExtra("product", product);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount() {
        if(products != null){
            return products.size();
            //return 5;
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cvProduct,cvProduct1;
        public ImageView ivImageUrl,ivImageUrl1;
        public TextView tvName,tvName1;
        public TextView tvPrice,tvPrice1;


        public ViewHolder(View itemView) {
            super(itemView);
            cvProduct = (CardView)itemView.findViewById(R.id.cvProduct);
            ivImageUrl = (ImageView)itemView.findViewById(R.id.recommendImage);
            tvName = (TextView)itemView.findViewById(R.id.tvName);
            tvPrice = (TextView)itemView.findViewById(R.id.tvPrice);


        }
    }
}
