package fun.com.example.lenovo.home;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import fun.com.example.lenovo.recycle.R;

/**
 * Created by suman on 6/11/2017.
 */

public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.ViewHolder> {

    FragmentManager fm ;
    int dotsCount;
    ImageView[] dotsImage;
    Context context;
    ViewHolder vholder;

    public AdsAdapter(Context ctx){
        context = ctx;
        //fm = ((FragmentActivity) ctx).getSupportFragmentManager();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.ads,parent,false);

       ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       vholder = holder;

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(context);
        holder.viewPager.setAdapter(viewPagerAdapter);

        dotsCount = viewPagerAdapter.getCount();
        dotsImage = new ImageView[dotsCount];

        final Handler handler = new Handler();

         final Runnable runnableCode = new Runnable() {

             @Override
             public void run() {
                 if(vholder.viewPager.getCurrentItem()==0)
                     vholder.viewPager.setCurrentItem(1);
                else if(vholder.viewPager.getCurrentItem()==1)
                     vholder.viewPager.setCurrentItem(2);
                 else if(vholder.viewPager.getCurrentItem()==2)
                     vholder.viewPager.setCurrentItem(3);
                 else if(vholder.viewPager.getCurrentItem()==3)
                     vholder.viewPager.setCurrentItem(4);
                 else
                     vholder.viewPager.setCurrentItem(0);
             }
         };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnableCode);
            }
        }, 3000, 3000);

        for(int i=0;i<dotsCount;i++){
            dotsImage[i] = new ImageView(context);
            dotsImage[i].setImageDrawable(ContextCompat.getDrawable(context,R.drawable.dot_nonactive));

            LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,0,4,0);
            dotsImage[i].setLayoutParams(params);
            holder.dotsLayout.addView(dotsImage[i]);
        }
        dotsImage[0].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dot_active));
        holder.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0; i<dotsCount;i++){
                    dotsImage[i].setImageDrawable(ContextCompat.getDrawable(context,R.drawable.dot_nonactive));
                }

                dotsImage[position].setImageDrawable(ContextCompat.getDrawable(context,R.drawable.dot_active));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ViewPager viewPager;
        LinearLayout dotsLayout;
        public ViewHolder(View itemView) {
            super(itemView);

            viewPager = (ViewPager) itemView.findViewById(R.id.adv);
            dotsLayout = (LinearLayout) itemView.findViewById(R.id.dot_layout);
        }
    }

}

