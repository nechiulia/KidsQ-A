package com.example.docta.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.docta.myapplication.clase.SetIntrebari;
import com.example.docta.myapplication.clase.SetIntrebariParser;
import com.example.docta.myapplication.clase.HttpManager;
import com.example.docta.myapplication.clase.Intrebare;
import com.example.docta.myapplication.util.Constante;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class SaNeJucamActivitate extends AppCompatActivity {
    private Button btnMatematica;
    private Button btnViata;
    private Button btnAnimale;
    private Button btnLitere;
    private Button btnFructe;

    private static final String URL = Constante.URL_JSON_TESTE;

    SharedPreferences sharedPreferencesCategorie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_sa_ne_jucam);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferencesCategorie = getSharedPreferences(Constante.CATEGORIE_TEST_PREF,MODE_PRIVATE);

        initComponents();
    }
    private void initComponents(){

        btnMatematica= findViewById(R.id.jucam_btn_matematica);
        btnLitere=findViewById(R.id.jucam_btn_litere);
        btnFructe=findViewById(R.id.jucam_btn_fructe_legume);
        btnViata=findViewById(R.id.jucam_btn_viata_dezicuzi);
        btnAnimale=findViewById(R.id.jucam_btn_animale);

        btnMatematica.setOnClickListener(deschideTestMatematica());
        btnFructe.setOnClickListener(deschideTestFructe());
        btnLitere.setOnClickListener(deschideTestLitere());
        btnAnimale.setOnClickListener(deschideTestAnimale());
        btnViata.setOnClickListener(deschideTestViata());

    }
    private View.OnClickListener deschideTestAnimale() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferencesCategorie.edit();
                editor.putString(Constante.CATEGORIE_PREF, Constante.CATEGORIE_ANIMALE);
                boolean result = editor.commit();
                Intent intent = new Intent(getApplicationContext(), IntrebariActivitate.class);
                startActivity(intent);
            }
        };

    }
    private View.OnClickListener deschideTestLitere(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPreferencesCategorie.edit();
                editor.putString(Constante.CATEGORIE_PREF, Constante.CATEGORIE_LITERE);
                boolean result = editor.commit();
                Intent intent = new Intent(getApplicationContext(), IntrebariActivitate.class);
                startActivity(intent);
            }};

            }
    private View.OnClickListener deschideTestFructe(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferencesCategorie.edit();
                editor.putString(Constante.CATEGORIE_PREF, Constante.CATEGORIE_FRUCTE);
                boolean result = editor.commit();
                Intent intent = new Intent(getApplicationContext(), IntrebariActivitate.class);
                startActivity(intent);
            }
        };
    }
    private View.OnClickListener deschideTestViata(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferencesCategorie.edit();
                editor.putString(Constante.CATEGORIE_PREF, Constante.CATEGORIE_VIATA);
                boolean result = editor.commit();
                Intent intent = new Intent(getApplicationContext(), IntrebariActivitate.class);
                startActivity(intent);

            }

        };


    }
    private View.OnClickListener deschideTestMatematica(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferencesCategorie.edit();
                editor.putString(Constante.CATEGORIE_PREF, Constante.CATEGORIE_MATEMATICA);
                boolean result = editor.commit();
                Intent intent = new Intent(getApplicationContext(), IntrebariActivitate.class);
                startActivity(intent);
            }

        };

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id= item.getItemId();
        if(id==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
