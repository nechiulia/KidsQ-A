package com.example.docta.myapplication;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class IntrebareaZileiActivitate extends AppCompatActivity {

    private Button btn_rezultat;
    private TextView countdown_text;
    private Button btn_countdown;

    private CountDownTimer countDownTimer;
    private long timeLeftInMiliseconds=600000; //10 min
    private boolean timerunning;
    private RadioGroup rgRaspunsuri;
    private RadioButton rbR1;
    private RadioButton rbR2;
    private RadioButton rbR3;
    private RadioGroup rg_optiuni;
    private RadioButton rb_rapuns3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_intrebarea_zilei);
        initComponents();

        btn_rezultat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbR1.isChecked() || rbR2.isChecked()||rbR3.isChecked()){
                    Intent intent=new Intent(getApplicationContext(), RezultatActivitate.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), getString(R.string.intrebarea_zilei_toast__selecteaza_raspuns), Toast.LENGTH_LONG).show();
                }

                if (isValid()) {
                    Intent intent = new Intent(getApplicationContext(), RezultatActivitate.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void initComponents(){
        rgRaspunsuri=findViewById(R.id.intrebarea_zilei_rg_raspunsuri);
        btn_rezultat=findViewById(R.id.intrebarea_zilei_btn_confirm);
        countdown_text=findViewById(R.id.intrebarea_zilei_tv_countdown);
        btn_countdown=findViewById(R.id.intrebarea_zilei_btn_incepe_test);
        btn_rezultat.setVisibility(View.INVISIBLE);
        rbR1=findViewById(R.id.intrebarea_zilei_rb_raspuns1);
        rbR2=findViewById(R.id.intrebarea_zilei_rb_raspuns2);
        rbR3=findViewById(R.id.intrebarea_zilei_rb_raspuns3);

        btn_countdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               btn_rezultat.setVisibility(View.VISIBLE);
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
        rg_optiuni = findViewById(R.id.intrebarea_zilei_rg_raspunsuri);
        rb_rapuns3 = findViewById(R.id.intrebarea_zilei_rb_raspuns3);
    }

    public boolean isValid(){
        if(rg_optiuni.getCheckedRadioButtonId()==-1){
            rb_rapuns3.setError(getString(R.string.intrebarea_zilei_raspuns_eroare));
            Toast.makeText(getApplicationContext(),getString(R.string.intrebarea_zilei_raspuns_eroare),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
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
