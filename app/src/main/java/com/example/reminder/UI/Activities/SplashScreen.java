package com.example.reminder.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.reminder.R;

import org.w3c.dom.Text;

public class SplashScreen extends AppCompatActivity {

    Animation topAnim, bottomAnim , middleAnim , logoAnim;
    TextView topText , middleText , bottomText;
    ImageView logo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        topText = findViewById(R.id.topText);
        middleText = findViewById(R.id.middleText);
        bottomText = findViewById(R.id.bottomText);
        logo = findViewById(R.id.logo);



        topAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.top_text_anim);
        bottomAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom_text_anim);
        middleAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.middle_text_anim);
        logoAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.logo_anim);

        topText.setAnimation(topAnim);
        middleText.setAnimation(middleAnim);
        bottomText.setAnimation(bottomAnim);
        logo.setAnimation(logoAnim);

        bottomAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
}