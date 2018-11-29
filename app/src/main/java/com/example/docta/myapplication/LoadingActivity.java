package com.example.docta.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class LoadingActivity extends AppCompatActivity {
    private ImageView imgTrannzitie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        imgTrannzitie = findViewById(R.id.incarcare_imagineSplash);

        Animation tranzitie = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.tranzitie_poza);
        imgTrannzitie.startAnimation(tranzitie);

        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    startActivity(new Intent(getApplicationContext(),LoginPageActivity.class));
                    finish();
                }
            }
        };
        timer.start();
    }
}
