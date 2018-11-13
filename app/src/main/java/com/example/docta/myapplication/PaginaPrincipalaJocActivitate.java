package com.example.docta.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_pagina_principala_joc);
        initComponents();
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
        btnInvatam.setOnClickListener(startSaInvatam());
        btnJucam.setOnClickListener(startSaNeJucam());


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
                startActivity(intent);
            }
        };
    }


}
