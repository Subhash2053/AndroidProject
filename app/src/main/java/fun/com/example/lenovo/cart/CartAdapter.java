package fun.com.example.lenovo.cart;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fun.com.example.lenovo.recycle.Product;
import fun.com.example.lenovo.recycle.ProductAdapter;
import fun.com.example.lenovo.recycle.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

/**
 * Created by srijan shrestha on 6/6/2017.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    int pos;
    //AlertDialog.Builder builder;
    String value;
    String[] numbers = {"none","1","2","3","4","5","6","7","8","9","10","11","12","13","14"};


    private Context context;
    private ArrayList<Product> products;

    public CartAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }


  //  String image_url = "http://10.0.2.2/images/";
   String delete_cart_url = "https://gcsubhash20.000webhostapp.com/customer/delete_cart.php";
    String edit_quan_url = "https://gcsubhash20.000webhostapp.com/customer/edit_quan.php";





    //getting view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_custom_layout, parent, false);



        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    //swtting values to the views
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Product product = products.get(position);
        holder.tv1.setText(product.name);
        holder.tv2.setText("Rs." + product.price);
        holder.tv4.setText("" + product.qty);
        int cartqty=product.qty;
        final int id= product.pid;
        String fullUrl = "https://gcsubhash20.000webhostapp.com/customer/" + product.image_url;


        Picasso.with(context)
                .load(fullUrl)
                .placeholder(R.drawable.logo)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.imageView);



       holder.s.setAdapter(new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, numbers));
       // holder.s.setSelection(Integer.parseInt(cartQuan.get(position)));
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final NiftyDialogBuilder Dialog = NiftyDialogBuilder.getInstance(v.getRootView().getContext());
                Dialog
                        .withTitle("Delete")
                        .withMessage("Are you Sure ?")
                        .withDialogColor("#1c90ec")
                        .withButton1Text("Yes")
                        .withButton2Text("Cancel")
                        .withDuration(200)
                        .withEffect(Effectstype.Fall)
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(final View v) {
                                pos = holder.getAdapterPosition();
                                products.remove(pos);


                                notifyItemChanged(pos);
                                notifyItemRangeChanged(position, products.size());
                                notifyDataSetChanged();
                                StringRequest request = new StringRequest(Request.Method.POST, delete_cart_url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(v.getRootView().getContext(),"Sucess"+id, Toast.LENGTH_LONG).show();

                                    }
                                }
                                        , new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(v.getRootView().getContext(),"Fail", Toast.LENGTH_LONG).show();
                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                      // Toast.makeText(v.getRootView().getContext(),"Good", Toast.LENGTH_LONG).show();

                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("prod_id", String.valueOf(id));

                                        return params;
                                    }
                                };
                                RequestQueue requestQueue = Volley.newRequestQueue(context);
                                requestQueue.add(request);
                                Dialog.cancel();
                            }

                        })
                        .setButton2Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Dialog.cancel();
                            }
                        }).show();


            }
        });




        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.s.setVisibility(View.VISIBLE);
                holder.saveBtn.setVisibility(View.VISIBLE);
                holder.s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        value = holder.s.getSelectedItem().toString();
                        //Toast.makeText(ctx,((TextView)holder.s.getSelectedView()).getText().toString() ,Toast.LENGTH_SHORT).show();
                        holder.tv4.setText(value);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        }
        );


                holder.saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //String newValue = holder.tv4.getText().toString();
                        //holder.tv4.setText(newValue);
                        //notifyItemChanged(position);
                        //notifyDataSetChanged();
                        holder.s.setVisibility(View.INVISIBLE);
                        holder.saveBtn.setVisibility(View.INVISIBLE);
                        //for changing quantity in database


                        StringRequest request = new StringRequest(Request.Method.POST, edit_quan_url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(context,"Saved Successfully",Toast.LENGTH_SHORT).show();
                            }
                        }
                                , new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("quantity", holder.tv4.getText().toString());
                                params.put("product_id", String.valueOf(id));
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        requestQueue.add(request);

                    }
                });

    }


    //Getting no of item for recyclerview

    @Override
    public int getItemCount() {
        if(products != null){
            return products.size();
        }
        return 0;
    }


    //Object Initialization
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        CardView cardView;
        TextView tv1,tv2,tv3,tv4;
        Button editBtn,deleteBtn,saveBtn;
        Spinner s;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView=(CardView)itemView.findViewById(R.id.cardV);

            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            tv1 = (TextView) itemView.findViewById(R.id.name);
            tv2 = (TextView) itemView.findViewById(R.id.price);
            //tv3 = (TextView) itemView.findViewById(R.id.description);
            tv4 = (TextView) itemView.findViewById(R.id.quantity);

            editBtn = (Button) itemView.findViewById(R.id.edit);
            deleteBtn = (Button) itemView.findViewById(R.id.delete);
            s=(Spinner)itemView.findViewById(R.id.spinner);
            saveBtn = (Button) itemView.findViewById(R.id.save);
        }
    }
}
