package com.example.docta.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.docta.myapplication.clase.SetIntrebari;
import com.example.docta.myapplication.clase.Intrebare;
import com.example.docta.myapplication.util.Constante;

import java.util.ArrayList;

public class LetsPlayActivity extends AppCompatActivity {
    private Button btnMatematica;
    private Button btnViata;
    private Button btnAnimale;
    private Button btnLitere;
    private Button btnFructe;
    private static final String URL = Constante.URL_JSON_TESTE;


    private SetIntrebari setIntrebari;
    SharedPreferences sharedPreferences;

    private ArrayList<Intrebare> listaIntrebariDificultate;
    private ArrayList<Intrebare> listaIntrebariTest;
    String dificultate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lets_play);
        if(savedInstanceState==null){
            String titlu = getString(R.string.Titlu_SaNeJucam);
            this.setTitle(titlu);
        }
        setIntrebari = (SetIntrebari) getIntent().getSerializableExtra(Constante.SET_INTREBARI_KEY);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = getSharedPreferences(Constante.SETARI_ELEV_PREF,MODE_PRIVATE);
        initComponents();
    }
    private void initComponents(){

        btnMatematica= findViewById(R.id.jucam_btn_matematica);
        btnLitere=findViewById(R.id.jucam_btn_litere);
        btnFructe=findViewById(R.id.jucam_btn_fructe_legume);
        btnViata=findViewById(R.id.jucam_btn_viata_dezicuzi);
        btnAnimale=findViewById(R.id.jucam_btn_animale);
        listaIntrebariTest=new ArrayList<>();

        btnMatematica.setOnClickListener(deschideTestMatematica());
        btnFructe.setOnClickListener(deschideTestFructe());
        btnLitere.setOnClickListener(deschideTestLitere());
        btnAnimale.setOnClickListener(deschideTestAnimale());
        btnViata.setOnClickListener(deschideTestViata());
        dificultate = sharedPreferences.getString(Constante.DIFICULTATE_PREF,null);
        initializareListeDificultate();
    }
    private void initializareListeDificultate(){
        listaIntrebariDificultate=new ArrayList<>();
        if(setIntrebari!=null){
            if(dificultate.equals(Constante.USOR_DIFICULTATE_TEST)){
                listaIntrebariDificultate=setIntrebari.getUsor();
            }
            else if(dificultate.equals(Constante.MEDIU_DIFICULTATE_TEST)){
                listaIntrebariDificultate=setIntrebari.getMediu();
            }
            else if(dificultate.equals(Constante.GREU_DIFICULTATE_TEST)){
                listaIntrebariDificultate=setIntrebari.getGreu();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), getString(R.string.jucam_toast_nu_exista_intrebari_dificultate), Toast.LENGTH_LONG).show();
        }
    }
    private View.OnClickListener deschideTestAnimale(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listaIntrebariDificultate.size()!=0) {
                    for (int i = 0; i < listaIntrebariDificultate.size(); i++) {
                        if (listaIntrebariDificultate.get(i).getOptiuni().getCategorie().equals(Constante.CATEGORIE_ANIMALE)) {
                            listaIntrebariTest.add(listaIntrebariDificultate.get(i));
                        }
                    }
                    Intent intent = new Intent(getApplicationContext(), QuestionsActivity.class);
                    intent.putExtra(Constante.LISTA_INTREBARI_KEY,listaIntrebariTest);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),getString(R.string.jucam_toast_nu_exista_intrebari_dificultate), Toast.LENGTH_LONG).show();
                }

        }

        };
    }
    private View.OnClickListener deschideTestLitere(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listaIntrebariDificultate.size()!=0) {
                    for (int i = 0; i < listaIntrebariDificultate.size(); i++) {
                        if (listaIntrebariDificultate.get(i).getOptiuni().getCategorie().equals(Constante.CATEGORIE_LITERE)) {
                            listaIntrebariTest.add(listaIntrebariDificultate.get(i));
                        }
                    }
                    Intent intent = new Intent(getApplicationContext(), QuestionsActivity.class);
                    intent.putExtra(Constante.LISTA_INTREBARI_KEY,listaIntrebariTest);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),getString(R.string.jucam_toast_nu_exista_intrebari_dificultate), Toast.LENGTH_LONG).show();
                }
            }
        };

    }
    private View.OnClickListener deschideTestFructe(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listaIntrebariDificultate.size()!=0) {
                    for (int i = 0; i < listaIntrebariDificultate.size(); i++) {
                        if (listaIntrebariDificultate.get(i).getOptiuni().getCategorie().equals(Constante.CATEGORIE_FRUCTE)) {
                            listaIntrebariTest.add(listaIntrebariDificultate.get(i));
                        }
                    }
                    Intent intent = new Intent(getApplicationContext(), QuestionsActivity.class);
                    intent.putExtra(Constante.LISTA_INTREBARI_KEY,listaIntrebariTest);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),getString(R.string.jucam_toast_nu_exista_intrebari_dificultate), Toast.LENGTH_LONG).show();
                }
            }
        };
    }
    private View.OnClickListener deschideTestViata(){

        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(listaIntrebariDificultate.size()!=0) {
                    for (int i = 0; i < listaIntrebariDificultate.size(); i++) {
                        if (listaIntrebariDificultate.get(i).getOptiuni().getCategorie().equals(Constante.CATEGORIE_VIATA)) {
                            listaIntrebariTest.add(listaIntrebariDificultate.get(i));
                        }
                    }
                    Intent intent = new Intent(getApplicationContext(), QuestionsActivity.class);
                    intent.putExtra(Constante.LISTA_INTREBARI_KEY,listaIntrebariTest);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),getString(R.string.jucam_toast_nu_exista_intrebari_dificultate), Toast.LENGTH_LONG).show();
                }
            }
        };
    }
    private View.OnClickListener deschideTestMatematica(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listaIntrebariDificultate.size()!=0) {
                    for (int i = 0; i < listaIntrebariDificultate.size(); i++) {
                        if (listaIntrebariDificultate.get(i).getOptiuni().getCategorie().equals(Constante.CATEGORIE_MATEMATICA)) {
                            listaIntrebariTest.add(listaIntrebariDificultate.get(i));
                        }
                    }
                    Intent intent = new Intent(getApplicationContext(), QuestionsActivity.class);
                    intent.putExtra(Constante.LISTA_INTREBARI_KEY,listaIntrebariTest);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),getString(R.string.jucam_toast_nu_exista_intrebari_dificultate), Toast.LENGTH_LONG).show();
                }
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
