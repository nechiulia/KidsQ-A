package com.example.docta.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.docta.myapplication.util.Constante;


public class PrincipalaActivitate extends AppCompatActivity
{
    private Button elev_btn;
    private Button prof_btn;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_principala);
        initComponents();
        elev_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ContElevActivitate.class);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.putString(Constante.UTILIZATOR_PREF, getString(R.string.prinipala_utilizator_elev_pref_message));
                boolean result= editor.commit();
                startActivity(intent);

            }
        }) ;



        prof_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AutentificareProfesorActivitate.class);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.putString(Constante.UTILIZATOR_PREF, getString(R.string.prinipala_utilizator_profesor_pref_message));
                boolean result= editor.commit();
                startActivity(intent);
            }
        });


    }
    private void initComponents(){
        elev_btn=findViewById(R.id.main_btn_elev);
        prof_btn=findViewById(R.id.main_btn_prof);
        sharedPreferences=getSharedPreferences(Constante.CONT_STATUT_PREF, MODE_PRIVATE);

    }
}
