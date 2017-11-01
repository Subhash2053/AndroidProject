package fun.com.example.lenovo.home;

import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import fun.com.example.lenovo.recycle.R;


/**
 * Created by suman on 5/27/2017.
 */
public class MainAdapterHorItem extends RecyclerView.Adapter<MainAdapterHorItem.ViewHolder> {
    private String[] itemList;
    int[] images;
    private Context ctx;
    final int POS_FIRST = 0;
    final int POS_SECOND =1;
    int dotsCount;
    ImageView[] dotsImage;
    //AdsHolder vholder;
    public MainAdapterHorItem(Context ctx, String[] itemsList, int[] images) {
        this.ctx = ctx;
        this.itemList = itemsList;
        this.images = images;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(ctx).inflate(R.layout.item_row_card, parent, false);
                ViewHolder vh = new ViewHolder(v);
                return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //holder.recycle_hor_layout = new LinearLayoutManager(ctx);
        //holder.recycle_hor.setLayoutManager(holder.recycle_hor_layout);

       if(position==0){
            holder.recycle_hor_layout = new LinearLayoutManager(ctx);
            holder.recycle_hor.setLayoutManager(holder.recycle_hor_layout);
            holder.recycle_hor_adapter = new AdsAdapter(ctx);
            holder.recycle_hor.setAdapter(holder.recycle_hor_adapter);
            holder.recycle_hor.setHasFixedSize(true);
        }
        else if(position==1){
            holder.recycle_hor_layout = new LinearLayoutManager(ctx);
            holder.recycle_hor.setLayoutManager(holder.recycle_hor_layout);
           // holder.recycle_hor_adapter = new ProductList(ctx);
            holder.recycle_hor.setAdapter(holder.recycle_hor_adapter);
            holder.recycle_hor.setHasFixedSize(true);

        }
        else {
            holder.recycle_hor_layout = new LinearLayoutManager(ctx,LinearLayoutManager.HORIZONTAL,false);
            holder.recycle_hor.setLayoutManager(holder.recycle_hor_layout);
            //holder.textView.setText(itemList[position-2]);
            holder.recycle_hor_adapter = new MainAdapterItem(ctx, images);
            holder.recycle_hor.setAdapter(holder.recycle_hor_adapter);
            holder.recycle_hor.setHasFixedSize(true);
        }

    }


    @Override
    public int getItemCount() {
        return itemList.length+2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public CardView cardView;
        public RecyclerView recycle_hor;
        public RecyclerView.LayoutManager recycle_hor_layout;
        public RecyclerView.Adapter recycle_hor_adapter;
        public ViewHolder(View itemView) {
            super(itemView);
            recycle_hor = (RecyclerView) itemView.findViewById(R.id.recycle_hor);
            //textView = (TextView) itemView.findViewById(R.id.textViewCard);
            //ImageView = (ImageView) itemView.findViewById(R.id.item_row);
        }
    }

   /* private class AdsHolder extends ViewHolder {
        ViewPager viewPager;
        LinearLayout dotsLayout;
        public AdsHolder(View v1) {
            super(v1);
            viewPager = (ViewPager) v1.findViewById(R.id.adv);
            dotsLayout = (LinearLayout) v1.findViewById(R.id.dot_layout);
        }
    }

    private class Category extends ViewHolder {
        CardView mobileCard, laptopCard, jacketCard, tShirtCard, shoesCard;
        ImageView iv1,iv2,iv3,iv4,iv5;
        public Category(View v2) {
            super(v2);

            mobileCard = (CardView) v2.findViewById(R.id.mobileCard);
            laptopCard = (CardView) v2.findViewById(R.id.laptopCard);
            jacketCard = (CardView) v2.findViewById(R.id.jacketCard);
            tShirtCard = (CardView) v2.findViewById(R.id.tShirtCard);
            shoesCard = (CardView) v2.findViewById(R.id.shoesCard);

            iv1 = (ImageView) v2.findViewById(R.id.cat_mob);
            iv2 = (ImageView) v2.findViewById(R.id.cat_laptop);
            iv3 = (ImageView) v2.findViewById(R.id.cat_jacket);
            iv4 = (ImageView) v2.findViewById(R.id.cat_tShirt);
            iv5 = (ImageView) v2.findViewById(R.id.cat_shoes);
        }
    }*/
}
