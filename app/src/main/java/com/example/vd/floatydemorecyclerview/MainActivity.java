package com.example.vd.floatydemorecyclerview;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bezyapps.floatieslibrary.Floaty;
import com.bezyapps.floatieslibrary.FloatyOrientationListener;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 12;
    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String[] myDataset = new String[]{"Hello", "World", "Test 1", "Test 2"};
    Floaty floaty;
    TextView floatyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(this,myDataset);
        recyclerView.setAdapter(mAdapter);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = Floaty.createNotification(this, "Floaty Demo", "Service Running", R.mipmap.ic_launcher, resultPendingIntent);

        // Inflate the Views that are to be used as HEAD and BODY of The Window
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.ic_launcher);
        View head = imageView;
        // You should not add click listeners to head as it will be overridden, but the purpose of not making head just
        // an ImageView is so you can add multiple views in it, and show and hide the relevant views to notify user etc.
        View body = LayoutInflater.from(this).inflate(R.layout.list_item_view, null);
        floatyText = (TextView) body.findViewById(R.id.textViewContent);
        floaty = Floaty.createInstance(this, head, body, 42, notification, new FloatyOrientationListener() {
            @Override
            public void beforeOrientationChange(Floaty floaty) {
                Toast.makeText(MainActivity.this, "Orientation Change Start", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterOrientationChange(Floaty floaty) {
                Toast.makeText(MainActivity.this, "Orientation Change End", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @TargetApi(Build.VERSION_CODES.M)
    public void startFloatyForAboveAndroidL() {
        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, PERMISSION_REQUEST_CODE);
        } else {
            floaty.startService();
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (Settings.canDrawOverlays(this)) {
                floaty.startService();
            } else {
                Spanned message = Html.fromHtml("Please allow this permission, so <b>Floaties</b> could be drawn.");
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
