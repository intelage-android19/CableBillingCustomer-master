package com.efunhub.cabelbillingcustomer.activity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.efunhub.cabelbillingcustomer.R;
import com.efunhub.cabelbillingcustomer.utility.SessionManager;


public class SplashActivity extends AppCompatActivity {

    private ImageView ivLogo;
    private TextView tvAppname;

    // Animation
    private Animation animLogo, animAppName;

    // Session Manager
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        init();

        //After loading animatio set animation to particuler view(Button, Image, etc.)
        ivLogo.setAnimation(animLogo);
        tvAppname.setAnimation(animAppName);

        Thread t = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                    session.checkLogin();
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    private void init() {
        session = new SessionManager(getApplicationContext());

        //Load animations
        animLogo = AnimationUtils.loadAnimation(this, R.anim.logo_anim);
        animAppName = AnimationUtils.loadAnimation(this, R.anim.appname_anim);

        ivLogo = findViewById(R.id.ivLogo);
        tvAppname = findViewById(R.id.tvAppname);
    }
}
