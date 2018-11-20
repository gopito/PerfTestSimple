package com.example.asmolianin.perftestsimple;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";
    private ImageView imageView;
    private AssetManager assetManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.image);
        assetManager = getAssets();

        displayOneImage(imageView);
        Intent intentWithExtra = getIntent();
        int activityStartDelay = intentWithExtra.getIntExtra("Delay",0);
        if (activityStartDelay > 0) {

            try {
                Thread.sleep(activityStartDelay);
            } catch (InterruptedException e) {
                //ignore

            }
        }
        long finishTime = SystemClock.uptimeMillis();
        long deltaTime = ((finishTime - ColdStartProvider.getStartTime()));
        Log.d(TAG, String.format("Started time: %d ", ColdStartProvider.getStartTime()));
        Log.d(TAG, String.format("Finish time: %d ", finishTime));
        Log.d(TAG, String.format("onStreamDataRendered %d ms = %d s", deltaTime, TimeUnit.MILLISECONDS.toSeconds(deltaTime)));
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.setAction("ru.ok.android.BroadcastPerfomance");
        intent.putExtra("Time", deltaTime);
        sendBroadcast(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    public void displayOneImage(ImageView v) {

        try {

            InputStream is = assetManager.open("img/spider-gwen.jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            v.setImageBitmap(bitmap);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
