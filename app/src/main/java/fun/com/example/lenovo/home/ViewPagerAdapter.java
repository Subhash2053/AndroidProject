package fun.com.example.lenovo.home;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import fun.com.example.lenovo.recycle.R;

/**
 * Created by suman on 6/10/2017.
 */
public class ViewPagerAdapter extends PagerAdapter{

    Context context;
    int[] images = {R.drawable.phone2,R.drawable.ppp,R.drawable.blade,R.drawable.phone2,R.drawable.phone2,R.drawable.phone2};
    ImageView imageView;

    public ViewPagerAdapter(Context context){
        this.context = context;

    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View v = LayoutInflater.from(context).inflate(R.layout.ads_image_layout,null);
        imageView = (ImageView)  v.findViewById(R.id.adsImage);
        imageView.setImageResource(images[position]);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        ViewPager vp = (ViewPager)container;
        vp.addView(v,0);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager)container;
        View v = (View) object;
        vp.removeView(v);
    }
}
