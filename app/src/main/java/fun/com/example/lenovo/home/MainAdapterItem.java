package fun.com.example.lenovo.home;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import fun.com.example.lenovo.recycle.R;


/**
 * Created by suman on 5/27/2017.
 */
public class MainAdapterItem extends RecyclerView.Adapter<MainAdapterItem.ViewHolder> {
    private int[] mDataSet;
    private Context ctx;

    public MainAdapterItem(Context ctx, int[] mDataSet) {
        this.ctx = ctx;
        this.mDataSet = mDataSet;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_row,parent,false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mImageView.setImageResource(mDataSet[position]);
        holder.toolbar.inflateMenu(R.menu.cart_add);
        holder.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.atc)
                    Toast.makeText(ctx,"clcikd",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDataSet.length;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public Toolbar toolbar;
        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.item_row);
            toolbar = (Toolbar) itemView.findViewById(R.id.toolbar);
        }
    }
}
