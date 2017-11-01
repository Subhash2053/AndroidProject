package fun.com.example.lenovo.home;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

import fun.com.example.lenovo.recycle.R;

/**
 * Created by SumanThp on 7/5/2017.
 */

public class AdFragment extends Fragment {
    ViewPager viewPager;
    Timer timer;
    TimerTask timerTask;
    LinearLayout dotsLayout;
    int dotsCount;
    ImageView[] dotsImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ads, container, false);

        viewPager = (ViewPager) v.findViewById(R.id.adv);
        dotsLayout = (LinearLayout) v.findViewById(R.id.dot_layout);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0);
        final Handler handler = new Handler();
        timer = new Timer();
        //timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);
        /*final Runnable runnableCode = new Runnable() {

            @Override
            public void run() {
                if(viewPager.getCurrentItem()==0)
                    viewPager.setCurrentItem(1);
                else if(viewPager.getCurrentItem()==1)
                    viewPager.setCurrentItem(2);
                else if(viewPager.getCurrentItem()==2)
                    viewPager.setCurrentItem(3);
                else if(viewPager.getCurrentItem()==3)
                    viewPager.setCurrentItem(4);
                else if(viewPager.getCurrentItem()==4)
                    viewPager.setCurrentItem(5);
                else
                    viewPager.setCurrentItem(0);
            }
        };
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnableCode);
            }
        }, 3000, 3000);*/

        dotsCount = viewPagerAdapter.getCount();
        dotsImage = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dotsImage[i] = new ImageView(getActivity());
            dotsImage[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.dot_nonactive));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(4, 0, 4, 0);
            dotsImage[i].setLayoutParams(params);
            dotsLayout.addView(dotsImage[i]);
        }
        dotsImage[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.dot_active));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++) {
                    dotsImage[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.dot_nonactive));
                }
                dotsImage[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.dot_active));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return v;
    }
    /*public class MyTimerTask extends TimerTask {

        @Override
        public void run(){

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewPager.getCurrentItem()==0)
                        viewPager.setCurrentItem(1);
                    else if(viewPager.getCurrentItem()==1)
                        viewPager.setCurrentItem(2);
                    else if(viewPager.getCurrentItem()==2)
                        viewPager.setCurrentItem(3);
                    else if(viewPager.getCurrentItem()==3)
                        viewPager.setCurrentItem(4);
                    else
                        viewPager.setCurrentItem(0);
                }
            });

        }
    }*/

}
