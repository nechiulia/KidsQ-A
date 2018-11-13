package com.example.docta.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.docta.myapplication.clase.Constante;

public class PrincipalaActivitate extends AppCompatActivity
{
    private Button elev_btn;
    private Button prof_btn;
    private  boolean statut;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_principala);

        elev_btn=findViewById(R.id.main_btn_elev);

        elev_btn.setOnClickListener(v -> {
            Intent intent=new Intent(getApplicationContext(),ContElevActivitate.class);
            /*statut=false;
            intent.putExtra(Constante.STATUT_KEY, statut);*/
            startActivity(intent);
        }
        );

        prof_btn=findViewById(R.id.main_btn_prof);

        prof_btn.setOnClickListener(v -> {
                    Intent intent=new Intent(getApplicationContext(),AutentificareProfesorActivitate.class);
                    statut=true;
                    intent.putExtra("STATUT KEY",statut);
                    startActivity(intent);
                }
        );


    }
}
