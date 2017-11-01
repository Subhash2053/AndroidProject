package fun.com.example.lenovo.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import fun.com.example.lenovo.recycle.R;


/**
 * Created by suman on 5/27/2017.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private int[] mDataSet;
    private Context ctx;

    public MainAdapter(Context ctx, int[] mDataSet) {
        this.ctx = ctx;
        this.mDataSet = mDataSet;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.ctg_row,parent,false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mImageView.setImageResource(mDataSet[position]);
    }


    @Override
    public int getItemCount() {
        return mDataSet.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.imageview);
        }
    }
}
