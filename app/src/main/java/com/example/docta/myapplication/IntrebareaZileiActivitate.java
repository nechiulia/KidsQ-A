package com.example.docta.myapplication;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IntrebareaZileiActivitate extends AppCompatActivity {

    private Button btn_rezultat;
    private TextView countdown_text;
    private Button btn_countdown;

    private CountDownTimer countDownTimer;
    private long timeLeftInMiliseconds=600000; //10 min
    private boolean timerunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_intrebarea_zilei);
        initComponents();

        btn_rezultat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), RezultatActivitate.class);
                startActivity(intent);
            }
        });
    }

    private void initComponents(){

        btn_rezultat=findViewById(R.id.intrebarea_zilei_btn_confirm);
        countdown_text=findViewById(R.id.intrebarea_zilei_tv_countdown);
        btn_countdown=findViewById(R.id.intrebarea_zilei_btn_incepe_test);

        btn_countdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startStop();
            }
        });
    }

    private void startStop(){
        if(timerunning){
            //stopTimer();
        }else{
            startTimer();
        }
    }

    private void startTimer(){
        countDownTimer=new CountDownTimer(timeLeftInMiliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btn_countdown.setVisibility(View.INVISIBLE);
                timeLeftInMiliseconds=millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();
        timerunning=true;
    }



    private void updateTimer(){
        int minutes=(int) timeLeftInMiliseconds/60000;
        int seconds=(int) timeLeftInMiliseconds%60000/1000;

        String timeLeftText;

        timeLeftText=""+minutes;
        timeLeftText+=":";
        if(seconds<10) timeLeftText+="0";
        timeLeftText+=seconds;

        countdown_text.setText(timeLeftText);
    }




}
