package fun.com.example.lenovo.home;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.android.json.JsonConverter;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fun.com.example.lenovo.cart.CartAdapter;
import fun.com.example.lenovo.cart.Confirm2Activity;
import fun.com.example.lenovo.cart.ConfirmActivity;
import fun.com.example.lenovo.cart.cardActivity;
import fun.com.example.lenovo.recycle.Product;
import fun.com.example.lenovo.recycle.R;
import fun.com.example.lenovo.recycle.Singleton;

/**
 * Created by Lenovo on 7/31/2017.
 */

public class Tab3 extends Fragment {
    final String TAG = String.valueOf(getActivity());
    TextView Total;
    RecyclerView rvItem;
    //private SwipeRefreshLayout refreshLayout;
    public String cid;

    private Button pay;
    private String paymentAmount;
    private String paymentAmount1;
    private static final String CONFIG = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    private static final String CLIENT_ID = "ASargK7poj9-6OUF7ylP9xsVTiVNoxrE6s0Lq1bzwZ9zLhHEieNZDXPxG0xt2Ygb_Tq3aGXpGgQsa-OG";

    private static final int REQUEST_PAYMENT = 1;
    private static PayPalConfiguration config = new PayPalConfiguration()

            .environment(CONFIG)
            .clientId(CLIENT_ID)
            .merchantName("Shop")
            .merchantPrivacyPolicyUri(Uri.parse("http://www.example.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("http://www.example.com/legal"));

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview =inflater.inflate(R.layout.cart_layout,container,false);
        Total=(TextView)rootview.findViewById(R.id.TotalPrice);
        // refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        SharedPreferences sharedpref=this.getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);

        String username=sharedpref.getString("name", "invalid");
        cid=sharedpref.getString("Cust_id", "invalid");




        pay = (Button)rootview.findViewById(R.id.pay);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDialog2 alert = new ViewDialog2();
                alert.showDialog(getActivity(), "Error de conexión al servidor");
            }


        });
        Intent intent = new Intent(getActivity(),PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        this.getActivity().startService(intent);



       // Toolbar toolbar = (Toolbar)rootview. findViewById(R.id.toolbar);
       // this.getActivity().setSupportActionBar(toolbar);

        getprice();
        rvItem = (RecyclerView)rootview. findViewById(R.id.recycle);
        rvItem.setHasFixedSize(true);

        rvItem.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        String url = "https://gcsubhash20.000webhostapp.com/customer/getCart.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();


                        ArrayList<Product> productList = new JsonConverter<Product>()
                                .toArrayList(response, Product.class);

                        CartAdapter adapter = new CartAdapter(getActivity(), productList);

                        rvItem.setAdapter(adapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null) {
                            Log.d(TAG, error.toString());
                            Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("cust_id", cid);

                return params;
            }
        };


        Singleton.getInstance(getActivity()).addToRequestQueue(stringRequest);


        return rootview;
    }
    private void getprice() {
        String url = "https://gcsubhash20.000webhostapp.com/customer/getTotal.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);

                        // Toast.makeText(cardActivity.this, response, Toast.LENGTH_LONG).show();
                        showJSON(response);




                    }

                    private void showJSON(String response) {
                        int total = 0;

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray result = jsonObject.getJSONArray("result");
                            for(int i=0;i<result.length();i++) {
                                JSONObject worker = result.getJSONObject(i);
                                String price = worker.getString("price");
                                String qty = worker.getString("qty");
                                total=total + Integer.parseInt(price)* Integer.parseInt(qty);

                                //Toast.makeText(getApplicationContext(), price+qty, Toast.LENGTH_LONG).show();


                            }
                            //Toast.makeText(getApplicationContext(), ""+total, Toast.LENGTH_LONG).show();
                            Total.setText("Total Pice is Rs. "+total);
                            paymentAmount=String.valueOf(total/103.56);
                            paymentAmount1=String.valueOf(total);
                            //  Toast.makeText(getApplicationContext(), paymentAmount, Toast.LENGTH_LONG).show();




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null) {
                            Log.d(TAG, error.toString());
                            Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("custid",cid);

                return params;
            }
        };


        Singleton.getInstance(getActivity()).addToRequestQueue(stringRequest);


    }




    private class ViewDialog2 implements View.OnClickListener {
        public void showDialog(Activity activity, String msg) {
            final Dialog dialog = new Dialog(activity,R.style.Dialog);
            // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setTitle("Choose payment Method");


            dialog.setCancelable(true);
            dialog.setContentView(R.layout.payment);
            // String[] numbers = {"none","1","2","3","4","5","6","7","8","9","10","11","12","13","14"};
            // String[] name = new String[10];
            // ArrayList[] arrayList = new ArrayList[]();
            // List<String> list;
            Button cod=(Button) dialog.findViewById(R.id.cod);
            Button paypal=(Button) dialog.findViewById(R.id.paypal);

            paypal.setOnClickListener(this);




            cod.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                    //Toast.makeText(getApplicationContext(),stringArray[1],Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getActivity(), ConfirmActivity.class);
                    intent.putExtra("PaymentAmount", paymentAmount1);

                    startActivity(intent);

                }
            });

            dialog.show();

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){


                case R.id.paypal:

                    PayPalPayment item = new PayPalPayment(new BigDecimal(String.valueOf(paymentAmount)), "USD", "Mobiles",PayPalPayment.PAYMENT_INTENT_SALE);

                    Intent in = new Intent(getActivity(),PaymentActivity.class);

                    in.putExtra(PaymentActivity.EXTRA_PAYMENT,item);

                    startActivityForResult(in, REQUEST_PAYMENT);


                    break;


            }


        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        if (requestCode == REQUEST_PAYMENT){


            if(resultCode == Activity.RESULT_OK){


                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if(confirm != null){

                    try {

                        System.out.println("Responses" +confirm) ;

                        Log.i("PayPal Example Payments" , confirm.toJSONObject().toString());

                        JSONObject obj = new JSONObject(confirm.toJSONObject().toString());


                        String paymentID=obj.getJSONObject("response").getString("id");
                        System.out.println("payment id:-=="+paymentID);
                        // Starting a new activity for the payment details and also putting the payment details with intent
                        startActivity(new Intent(getActivity(), Confirm2Activity.class)
                                .putExtra("PaymentDetails", paymentID)
                                .putExtra("PaymentAmount", paymentAmount1));




                        Toast.makeText(getActivity(), paymentID ,Toast.LENGTH_LONG).show();


                    }catch(JSONException e){

                        Log.e("Payment demo", "failure occured:",e);
                    }

                }

            }else if(requestCode == Activity.RESULT_CANCELED){



                Log.i("Paymentdemo","The user cancelled");


            }else if(requestCode == PaymentActivity.RESULT_EXTRAS_INVALID){

                Log.i("paymentdemo","Invalid payment Submitted");


            }


        }

    }

}


