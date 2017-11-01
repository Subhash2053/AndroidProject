package fun.com.example.lenovo.profile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fun.com.example.lenovo.recycle.R;

/**
 * Created by srijan on 7/17/2017.
 */

public class order_adapter extends RecyclerView.Adapter<order_adapter.ViewHolder> {

    Context ctx;
    ArrayList<String> Name = new ArrayList<>();

    ArrayList<String> Price = new ArrayList<>();
    ArrayList<Integer> Quan = new ArrayList<>();
    ArrayList<String> Image = new ArrayList<>();
    ArrayList<Integer> productID = new ArrayList<>();
    ArrayList<String> Shipping_address= new ArrayList<>();
   // AlertDialog.Builder builder;
    order1 order;


    ArrayAdapter adapter;

    String image_url = "http://10.0.2.2/customer/img/";


    //Data iniitialization

    public order_adapter(Context ctx, ArrayList<String> Name, ArrayList<String> Price, ArrayList<Integer> Quan, ArrayList<String> Image, ArrayList<Integer> productID,ArrayList<String> Shipping_address){

        this.ctx = ctx;

        this.Name = Name;
        this.order=order;
        this.Price=Price;
        this.Quan=Quan;
        this.Image=Image;
        this.productID = productID;
        this.Shipping_address = Shipping_address;
    }

    //getting view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(ctx).inflate(R.layout.order_list,parent,false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    //swtting values to the views
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Picasso.with(ctx).load(image_url + Image.get(position)).into(holder.imageView);
        holder.tv1.setText(Name.get(position));
        holder.tv2.setText(Price.get(position));

        holder.tv4.setText(""+Quan.get(position));
        holder.tv3.setText(Shipping_address.get(position));








    }


    //Getting no of item for recyclerview

    @Override
    public int getItemCount() {
        return Name.size();
    }


    //Object Initialization
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tv1,tv2,tv3,tv4;


        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            tv1 = (TextView) itemView.findViewById(R.id.name);
            tv2 = (TextView) itemView.findViewById(R.id.price);
            //tv3 = (TextView) itemView.findViewById(R.id.shipping);
            tv4 = (TextView) itemView.findViewById(R.id.quantity);


        }
    }
}

