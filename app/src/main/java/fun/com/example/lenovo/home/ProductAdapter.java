package fun.com.example.lenovo.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import fun.com.example.lenovo.recycle.R;

/**
 * Created by SumanThp on 7/6/2017.
 */

class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
    Context ctx;
    int[] images;
    int[] images1;
    String[] mobile;



    public ProductAdapter(Context ctx, int[] images,int[] images1,String[] mobile){
        this.ctx = ctx;
        this.images = images;
        this.images1 = images1;
        this.mobile = mobile;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.grid_single_item,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.imageView1.setImageResource(images[position]);
        holder.imageView2.setImageResource(images1[position]);

        holder.imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0:
                        break;
                    case 1:
                        Intent i0 = new Intent(ctx,Mi_Mobile.class);
                        i0.putExtra("Brand","Samsung");
                        ctx.startActivity(i0);
                        break;
                    case 2:
                        break;
                    case 3:
                        Intent i3 = new Intent(ctx,Mi_Mobile.class);
                        i3.putExtra("Brand","LG");
                        ctx.startActivity(i3);
                        break;
                    case 4:
                        break;
                    case 5:
                        Intent i2 = new Intent(ctx,Mi_Mobile.class);
                        i2.putExtra("Brand","Huawei");
                        ctx.startActivity(i2);
                        break;

                }
            }
        });

        holder.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0:
                        Intent i0 = new Intent(ctx,Mi_Mobile.class);
                        i0.putExtra("Brand","ZTE");
                        ctx.startActivity(i0);
                            break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        Intent i1 = new Intent(ctx,Mi_Mobile.class);
                        i1.putExtra("Brand","Xiaomi");
                        ctx.startActivity(i1);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView1, imageView2;
        View root;
        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            imageView1 = (ImageView) itemView.findViewById(R.id.gridImage1);
            imageView2 = (ImageView) itemView.findViewById(R.id.gridImage2);


        }
    }
}
