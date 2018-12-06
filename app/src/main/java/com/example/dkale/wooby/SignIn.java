package com.example.dkale.wooby;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class SignIn extends AppCompatActivity {
    ImageView image;
    final String TAG = "FirebaseSignIn";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Log.e(TAG,"Starting sign in activity");
        image = (ImageView) findViewById(R.id.aniImage);
        image.setImageResource(R.drawable.watchdog_man);
    }
}
