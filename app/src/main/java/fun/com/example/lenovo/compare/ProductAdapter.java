package fun.com.example.lenovo.compare;

/**
 * Created by Lenovo on 6/4/2017.
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

import fun.com.example.lenovo.recycle.Main2Activity;
import fun.com.example.lenovo.recycle.Product;
import fun.com.example.lenovo.recycle.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private ArrayList<Product> products;

    public ProductAdapter(Context context, ArrayList<Product> products){
        this.context = context;
        this.products = products;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_layout, parent, false);

        ProductViewHolder productViewHolder = new ProductViewHolder(view);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        final Product product = products.get(position);
        holder.tvName.setText(product.name);

        holder.tvPrice.setText("" + product.price);

        String fullUrl = "http://gcsubhash20.000webhostapp.com/customer/" + product.image_url;

        Picasso.with(context)
                .load(fullUrl)
                .placeholder(R.drawable.logo)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.ivImageUrl);
        holder.ivImageUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,product.name+product.pid,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, Main2Activity.class);
                intent.putExtra("product", product);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(products != null){
            //return products.size();
            return 5;
        }
        return 0;
    }


    //ViewHolder class
    public static class ProductViewHolder extends RecyclerView.ViewHolder{

        public CardView cvProduct;
        public ImageView ivImageUrl;
        public TextView tvName;
        public TextView tvPrice;


        public ProductViewHolder(View itemView) {
            super(itemView);
            cvProduct = (CardView)itemView.findViewById(R.id.cvProduct);
            ivImageUrl = (ImageView)itemView.findViewById(R.id.recommendImage);
            tvName = (TextView)itemView.findViewById(R.id.tvName);
            tvPrice = (TextView)itemView.findViewById(R.id.tvPrice);


        }
    }
}
