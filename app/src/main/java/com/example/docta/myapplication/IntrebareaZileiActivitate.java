package com.example.docta.myapplication;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.CountDownTimer;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.docta.myapplication.clase.Intrebare;
import com.example.docta.myapplication.util.Constante;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class IntrebareaZileiActivitate extends AppCompatActivity {

    private Button btn_rezultat;
    private TextView countdown_text;
    private Button btn_countdown;
    private TextView tvIntrebareaZilei;
    private CountDownTimer countDownTimer;
    private long timeLeftInMiliseconds=600000; //10 min
    private boolean timerunning;
    private RadioButton rbR1;
    private RadioButton rbR2;
    private RadioButton rbR3;
    private RadioGroup rg_optiuni;
    private Intrebare intrebareaZilei;
    private ImageView imgV_imagine;

    private double punctaj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_intrebarea_zilei);
        if(savedInstanceState==null){
            String titlu = getString(R.string.Titlu_IntrebareaZilei);
            this.setTitle(titlu);
        }
        initComponents();


    }

    private void initComponents(){
        punctaj=0;
        btn_rezultat=findViewById(R.id.intrebarea_zilei_btn_confirm);
        countdown_text=findViewById(R.id.intrebarea_zilei_tv_countdown);
        btn_countdown=findViewById(R.id.intrebarea_zilei_btn_incepe_test);
        btn_rezultat.setVisibility(View.INVISIBLE);
        rg_optiuni = findViewById(R.id.intrebarea_zilei_rg_raspunsuri);
        rbR1 = findViewById(R.id.intrebarea_zilei_rb_raspuns1);
        rbR2 = findViewById(R.id.intrebarea_zilei_rb_raspuns2);
        rbR3 = findViewById(R.id.intrebarea_zilei_rb_raspuns3);
        tvIntrebareaZilei=findViewById(R.id.intrebarea_zilei_tv_intrebare);
        imgV_imagine=findViewById(R.id.intrebarea_zilei_iv_original);
        intrebareaZilei= (Intrebare)getIntent().getSerializableExtra(Constante.INTREBAREA_ZILEI_KEY);
        btn_countdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               btn_rezultat.setVisibility(View.VISIBLE);
                startStop();
            }
        });
        btn_countdown.performClick();
        btn_rezultat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValid()) {
                    int rbCorect;
                    for (int i = 0; i < 3; i++) {
                        if (intrebareaZilei.getRaspunsuri().get(i).isCorect()) {
                            rbCorect = rg_optiuni.getChildAt(i).getId();
                            if (rbCorect == rg_optiuni.getCheckedRadioButtonId()) {
                                Toast toastCorect = Toast.makeText(getApplicationContext(), getString(R.string.toast_raspuns_corect), Toast.LENGTH_SHORT);
                                View view1 = toastCorect.getView();
                                int colorGreen = ResourcesCompat.getColor(getResources(), R.color.LimeGreen, null);
                                view1.getBackground().setColorFilter(colorGreen, PorterDuff.Mode.SRC_IN);
                                toastCorect.show();
                                punctaj += intrebareaZilei.getOptiuni().getPunctaj();
                                //nrIntrebariCorecte++;
                            } else {
                                Toast toastGresit = Toast.makeText(getApplicationContext(), getString(R.string.toast_raspuns_gresit), Toast.LENGTH_SHORT);
                                View view2 = toastGresit.getView();
                                int colorRed = ResourcesCompat.getColor(getResources(), R.color.fireBrick, null);
                                view2.getBackground().setColorFilter(colorRed, PorterDuff.Mode.SRC_IN);
                                toastGresit.show();
                            }
                        }
                    }
                    Intent intent = new Intent(getApplicationContext(), RezultatActivitate.class);
                    startActivity(intent);
                }
            }
        });
        imgV_imagine.setOnClickListener(openShowImage());
        initializareTextConntroale();
    }
    private View.OnClickListener openShowImage(){
     return new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(getApplicationContext(),ShowImageActivity.class);
             intent.putExtra("Imagine", intrebareaZilei.getOptiuni().getImagine());
             startActivity(intent);
         }
     } ;
    }
    private void initializareTextConntroale(){
        tvIntrebareaZilei.setText(intrebareaZilei.getTextIntrebare());
        rbR1.setText(intrebareaZilei.getRaspunsuri().get(0).getTextRaspuns());
        rbR2.setText(intrebareaZilei.getRaspunsuri().get(1).getTextRaspuns());
        rbR3.setText(intrebareaZilei.getRaspunsuri().get(2).getTextRaspuns());
        loadImageFromJson(intrebareaZilei.getOptiuni().getImagine());
        rg_optiuni.clearCheck();
    }
    private void loadImageFromJson(String urlJson){
        Picasso.with(getApplicationContext()).load(urlJson).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                .into(imgV_imagine, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

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

    public boolean isValid(){
        if(rg_optiuni.getCheckedRadioButtonId()==-1){
            rbR3.setError(getString(R.string.intrebarea_zilei_raspuns_eroare));
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
