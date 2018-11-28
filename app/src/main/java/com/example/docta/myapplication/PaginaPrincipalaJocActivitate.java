package com.example.docta.myapplication;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docta.myapplication.clase.Intrebare;
import com.example.docta.myapplication.clase.Network.HttpManager;
import com.example.docta.myapplication.clase.SetIntrebari;
import com.example.docta.myapplication.clase.SetIntrebariParser;
import com.example.docta.myapplication.util.Constante;

import org.json.JSONException;

import java.util.ArrayList;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class PaginaPrincipalaJocActivitate extends AppCompatActivity {

    private Button btnJucam;
    private Button btnInvatam;
    private Button btnClasament;
    private Button btnSetari;
    private Button btnAvatareleMele;
    private Button btnTestulZilei;
    private Button btnIntrebareaZilei;
    private ImageButton imgBtnParere;
    private TextView tvNumeAvatar;
    private Button btnInapoiProfesor;
    private Button btnSarcini;
    private SharedPreferences sharedPreferences;
    private ImageButton imgBtnAjutor;
    private static final String URL = Constante.URL_JSON_TESTE;
    private SetIntrebari setIntrebari;
    private ArrayList<Intrebare> intrebariTestulZilei;
    private ProgressDialog progressDialog;
    Intent intent;
    private Boolean isChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_pagina_principala_joc);
        isChecked = getIntent().getBooleanExtra("Validare",false);
        if(savedInstanceState==null){
            String titlu = getString(R.string.Titlu_PaginaPrincipalaJoc);
            this.setTitle(titlu);
        }

                @SuppressLint("StaticFieldLeak") HttpManager manager = new HttpManager() {

                    @Override
                    protected void onPostExecute(String s) {
                        try {
                            setIntrebari = SetIntrebariParser.fromJson(s);
                            if(!isChecked) {
                                incarcareDate();
                            }
//                            if (this.getDone().equals("done")) {
//                                Toast.makeText(getApplicationContext(), "S-au incarcat testele!", Toast.LENGTH_LONG).show();
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), getString(R.string.jucam_parsare_eroare), Toast.LENGTH_LONG).show();
                        }
                    }

                };
        manager.execute(URL);
        initComponents();
    }

    private void incarcareDate(){
            progressDialog = new ProgressDialog(PaginaPrincipalaJocActivitate.this);
            progressDialog.setMax(100);
            progressDialog.setMessage("Se incarca testele...");
            progressDialog.setTitle("Descarcare");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
            progressDialog.getButton(ProgressDialog.BUTTON_NEUTRAL).setVisibility(View.INVISIBLE);

            @SuppressLint("StaticFieldLeak") Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    progressDialog.incrementProgressBy(1);
                }
            };

            new Thread(() -> {
                while (progressDialog.getProgress() <= progressDialog.getMax()) {
                    try {
                        Thread.sleep(50);
                        handler.sendMessage(handler.obtainMessage());
                        if (progressDialog.getProgress() == progressDialog.getMax()) {
                            progressDialog.dismiss();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

    }

    private void initComponents(){
        btnJucam=findViewById(R.id.ppj_btn_jucam);
        btnInvatam=findViewById(R.id.ppj_btn_invatam);
        btnAvatareleMele=findViewById(R.id.ppj_btn_avatareleMele);
        btnClasament=findViewById(R.id.ppj_btn_clasament);
        btnIntrebareaZilei=findViewById(R.id.ppj_btn_intrebareaZilei);
        btnTestulZilei=findViewById(R.id.ppj_btn_testulZilei);
        btnSetari=findViewById(R.id.ppj_btn_setari);
        imgBtnParere=findViewById(R.id.ppj_imgBtn_star);
        tvNumeAvatar=findViewById(R.id.ppj_tv_bunVenit);
        btnInapoiProfesor=findViewById(R.id.ppj_btn_inapoi_la_profesor);
        imgBtnAjutor=findViewById(R.id.ppj_imgBtn_intrebare);
        btnSarcini=findViewById(R.id.ppj_btn_sarcini);

        sharedPreferences= getSharedPreferences(Constante.CONT_STATUT_PREF, MODE_PRIVATE);
        String utilizator= sharedPreferences.getString(Constante.UTILIZATOR_PREF, getString(R.string.ppj_utilizator_default_pref));

        if(utilizator.compareTo(getString(R.string.principala_utilizator_profesor_pref_message))==0){
            btnInapoiProfesor.setVisibility(View.VISIBLE);
        }
        else {
            btnInapoiProfesor.setVisibility(View.INVISIBLE);
        }
        String nume= getIntent().getStringExtra(Constante.NUME_KEY);
        if (nume==null){
            tvNumeAvatar.setText(getString(R.string.ppj_tv_bine_ai_venit_null));
        }
        else {
        tvNumeAvatar.setText(getString(R.string.ppj_tv_bineAiVenit)+ nume);
        }
        btnInvatam.setOnClickListener(startSaInvatam());
        btnJucam.setOnClickListener(startSaNeJucam());
        btnIntrebareaZilei.setOnClickListener(deschideIntrebareaZilei());
        btnTestulZilei.setOnClickListener(deschideTest());
        btnAvatareleMele.setOnClickListener(deschideAvatare());
        btnClasament.setOnClickListener(deschideClasament());
        btnSetari.setOnClickListener(deschideSetari());
        imgBtnParere.setOnClickListener(deschidePareri());
        btnInapoiProfesor.setOnClickListener(inapoiLaProfesor());
        imgBtnAjutor.setOnClickListener(deschideAjutor());
        btnSarcini.setOnClickListener(startSarcini());
   }

   private View.OnClickListener startSarcini()
   {
       return new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),SarciniActivitate.class);
            startActivity(intent);
           }
       };
   }
   private View.OnClickListener inapoiLaProfesor(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            finish();
        }};
   }
   private View.OnClickListener startSaInvatam(){
       return new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(getApplicationContext(), SaInvatamActivitate.class);
               startActivity(intent);
           }
       };
    }
    private View.OnClickListener startSaNeJucam(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SaNeJucamActivitate.class);
                intent.putExtra("ppp",setIntrebari);
                startActivity(intent);
            }
        };
    }
    private View.OnClickListener deschideIntrebareaZilei(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), IntrebareaZileiActivitate.class);
                startActivity(intent);
            }
        };
    }
    private View.OnClickListener deschideTest(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intrebariTestulZilei = setIntrebari.getGreu();
                Intent intent = new Intent(getApplicationContext(), IntrebariActivitate.class);
                intent.putExtra(Constante.TESTUL_ZILEI,getString(R.string.Valoare_TestulZilei));
                intent.putExtra("lll",intrebariTestulZilei);
                startActivity(intent);
                finish();
            }

        };

    }
    private View.OnClickListener deschideAvatare(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AvatareleMeleActivitate.class);
                startActivity(intent);
            }
        };

    }

    private View.OnClickListener deschideClasament(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ClasamentElevActivitate.class);
                startActivity(intent);
            }
        };

    }

    private View.OnClickListener deschideSetari(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SetariElevActivitate.class);
                startActivity(intent);
            }

        };

    }

    private View.OnClickListener deschidePareri(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PareriActivitate.class);
                startActivity(intent);
            }

        };

    }
    private View.OnClickListener deschideAjutor(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AjutorActivitate.class);
                startActivity(intent);
            }
        };
    }



}
