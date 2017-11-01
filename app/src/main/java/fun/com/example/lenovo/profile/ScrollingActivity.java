package fun.com.example.lenovo.profile;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fun.com.example.lenovo.recycle.LoginActivity;
import fun.com.example.lenovo.recycle.MainActivity;
import fun.com.example.lenovo.recycle.R;



public class ScrollingActivity extends AppCompatActivity {
    ImageView i1,i2,i3,i4,DP;
    TextView t1,t2,t3;
    CardView cv;
    FloatingActionButton i5;
    private String getDpUrl = "http://10.0.2.2/upload/getDp.php";
    String image_url = "http://10.0.2.2/upload/uploads";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        cv = (CardView) findViewById(R.id.card_view2);
        i1=(ImageView) findViewById(R.id.about);
        i2=(ImageView) findViewById(R.id.order);
        i3=(ImageView) findViewById(R.id.wishlist);
        i4=(ImageView) findViewById(R.id.notification);
        t1=(TextView) findViewById(R.id.t1);
        t2=(TextView) findViewById(R.id.t2);
        t3=(TextView) findViewById(R.id.t3);
        i5=   (FloatingActionButton) findViewById(R.id.fab);
        DP=(ImageView)findViewById(R.id.circleImageView);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(ScrollingActivity.this,about.class  );
                startActivity(intent);
            }
        });

        i5.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(ScrollingActivity.this,upload1.class  );
                startActivity(intent);
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(ScrollingActivity.this,order1.class  );
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.signout) {
            SharedPreferences myPrefs = getSharedPreferences("login",
                    MODE_PRIVATE);
            SharedPreferences.Editor editor = myPrefs.edit();
            editor.clear();
            editor.commit();
            //AppState.getSingleInstance().setLoggingOut(true);

            Log.d("ScrollingActivity", "Now log out and start the activity login");
            Intent intent = new Intent(ScrollingActivity.this,
                    LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        if (id==R.id.Edit){
            Intent i= new Intent(this,edit.class);
            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }
}
