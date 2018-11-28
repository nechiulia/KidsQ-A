package com.example.docta.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.example.docta.myapplication.clase.Network.HttpManager;
import com.example.docta.myapplication.clase.Intrebare;
import com.example.docta.myapplication.util.Constante;

import org.json.JSONException;

import java.util.ArrayList;

public class SaNeJucamActivitate extends AppCompatActivity {
    private Button btnMatematica;
    private Button btnViata;
    private Button btnAnimale;
    private Button btnLitere;
    private Button btnFructe;
   // private static final String URL = "https://api.myjson.com/bins/o60v6";
//   private static final String URL = "https://api.myjson.com/bins/g2j1e";
    private static final String URL = Constante.URL_JSON_TESTE;


    private SetIntrebari setIntrebari;
    SharedPreferences sharedPreferences;
    private ArrayList<Intrebare> listaIntrebariUsoare;
    private ArrayList<Intrebare> listaIntrebariMedii;
    private ArrayList<Intrebare> listaIntrebariGrele;
    private ArrayList<Intrebare> listaIntrebariDificultate;
    private ArrayList<Intrebare> listaIntrebariTest;
    String dificultate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_sa_ne_jucam);
        if(savedInstanceState==null){
            String titlu = getString(R.string.Titlu_SaNeJucam);
            this.setTitle(titlu);
        }
        setIntrebari = (SetIntrebari) getIntent().getSerializableExtra("ppp");
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
        listaIntrebariUsoare= new ArrayList<>();
        listaIntrebariMedii= new ArrayList<>();
        listaIntrebariGrele= new ArrayList<>();
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
                    Intent intent = new Intent(getApplicationContext(), IntrebariActivitate.class);

                    intent.putExtra("lll",listaIntrebariTest);
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
                if( dificultate.equals(Constante.USOR_DIFICULTATE_TEST)){
                    if(setIntrebari!=null){
                        listaIntrebariUsoare=setIntrebari.getUsor();
                        for(int i=0; i< listaIntrebariUsoare.size();i++){
                            if(listaIntrebariUsoare.get(i).getOptiuni().getCategorie().equals(Constante.CATEGORIE_LITERE)){
                                listaIntrebariTest.add(listaIntrebariUsoare.get(i));
                            }
                        }
                        Intent intent = new Intent(getApplicationContext(), IntrebariActivitate.class);
                        intent.putExtra("lll",listaIntrebariTest);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), R.string.toast_nu_exista_intrebari, Toast.LENGTH_LONG).show();
                    }

                }
                else if( dificultate.equals(Constante.MEDIU_DIFICULTATE_TEST)){
                    listaIntrebariMedii=setIntrebari.getMediu();
                    for(int i=0; i< listaIntrebariMedii.size();i++){
                        if(listaIntrebariMedii.get(i).getOptiuni().getCategorie().equals(Constante.CATEGORIE_LITERE)){
                            listaIntrebariTest.add(listaIntrebariMedii.get(i));
                        }
                        Intent intent = new Intent(getApplicationContext(), IntrebariActivitate.class);
                        intent.putExtra("lll",listaIntrebariTest);
                        startActivity(intent);
                    }
                }
                else if( dificultate.equals(Constante.GREU_DIFICULTATE_TEST)){
                    listaIntrebariGrele=setIntrebari.getGreu();
                    for(int i=0; i< listaIntrebariGrele.size();i++){
                        if(listaIntrebariGrele.get(i).getOptiuni().getCategorie().equals(Constante.CATEGORIE_LITERE)){
                            listaIntrebariTest.add(listaIntrebariGrele.get(i));
                        }
                        Intent intent = new Intent(getApplicationContext(), IntrebariActivitate.class);
                        intent.putExtra("lll",listaIntrebariTest);
                        startActivity(intent);
                    }
                }

            }

        };


    }
    private View.OnClickListener deschideTestFructe(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( dificultate.equals(Constante.USOR_DIFICULTATE_TEST)){
                    if(setIntrebari!=null){
                        listaIntrebariUsoare=setIntrebari.getUsor();
                        for(int i=0; i< listaIntrebariUsoare.size();i++){
                            if(listaIntrebariUsoare.get(i).getOptiuni().getCategorie().equals(Constante.CATEGORIE_FRUCTE)){
                                listaIntrebariTest.add(listaIntrebariUsoare.get(i));
                            }
                        }
                        Intent intent = new Intent(getApplicationContext(), IntrebariActivitate.class);
                        //   intent.putParcelableArrayListExtra("Intrebari matematica key", listaIntrebariMatematica);
                        intent.putExtra("lll",listaIntrebariTest);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), R.string.toast_nu_exista_intrebari, Toast.LENGTH_LONG).show();
                    }

                }
                else if( dificultate.equals(Constante.MEDIU_DIFICULTATE_TEST)){
                    listaIntrebariMedii=setIntrebari.getMediu();
                    for(int i=0; i< listaIntrebariMedii.size();i++){
                        if(listaIntrebariMedii.get(i).getOptiuni().getCategorie().equals(Constante.CATEGORIE_FRUCTE)){
                            listaIntrebariTest.add(listaIntrebariMedii.get(i));
                        }
                        Intent intent = new Intent(getApplicationContext(), IntrebariActivitate.class);
                        //  intent.putParcelableArrayListExtra("Intrebari matematica key", listaIntrebariMatematica);
                        intent.putExtra("lll",listaIntrebariTest);
                        startActivity(intent);
                    }
                }
                else if( dificultate.equals(Constante.GREU_DIFICULTATE_TEST)){
                    listaIntrebariGrele=setIntrebari.getGreu();
                    for(int i=0; i< listaIntrebariGrele.size();i++){
                        if(listaIntrebariGrele.get(i).getOptiuni().getCategorie().equals(Constante.CATEGORIE_FRUCTE)){
                            listaIntrebariTest.add(listaIntrebariGrele.get(i));
                        }
                        Intent intent = new Intent(getApplicationContext(), IntrebariActivitate.class);
                        //intent.putParcelableArrayListExtra("Intrebari matematica key", listaIntrebariMatematica);
                       // intent.putExtra("lista intrebari key", listaIntrebariMatematica);
                        intent.putExtra("lll",listaIntrebariTest);
                        startActivity(intent);
                    }
                }

            }

        };

    }
    private View.OnClickListener deschideTestViata(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( dificultate.equals(Constante.USOR_DIFICULTATE_TEST)){
                    if(setIntrebari!=null){
                        listaIntrebariUsoare=setIntrebari.getUsor();
                        for(int i=0; i< listaIntrebariUsoare.size();i++){
                            if(listaIntrebariUsoare.get(i).getOptiuni().getCategorie().equals(Constante.CATEGORIE_VIATA)){
                                listaIntrebariTest.add(listaIntrebariUsoare.get(i));
                                Toast.makeText(getApplicationContext(), listaIntrebariUsoare.get(i).toString(),Toast.LENGTH_LONG).show();
                            }
                        }
                        Intent intent = new Intent(getApplicationContext(), IntrebariActivitate.class);
                        //   intent.putParcelableArrayListExtra("Intrebari matematica key", listaIntrebariMatematica);
                        intent.putExtra("lll",listaIntrebariTest);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), R.string.toast_nu_exista_intrebari, Toast.LENGTH_LONG).show();
                    }

                }
                else if( dificultate.equals(Constante.MEDIU_DIFICULTATE_TEST)){
                    listaIntrebariMedii=setIntrebari.getMediu();
                    for(int i=0; i< listaIntrebariMedii.size();i++){
                        if(listaIntrebariMedii.get(i).getOptiuni().getCategorie().equals(Constante.CATEGORIE_VIATA)){
                            listaIntrebariTest.add(listaIntrebariMedii.get(i));
                        }
                        Intent intent = new Intent(getApplicationContext(), IntrebariActivitate.class);
                        //  intent.putParcelableArrayListExtra("Intrebari matematica key", listaIntrebariMatematica);
                        intent.putExtra("lll",listaIntrebariTest);
                        startActivity(intent);
                    }
                }
                else if( dificultate.equals(Constante.GREU_DIFICULTATE_TEST)){
                    listaIntrebariGrele=setIntrebari.getGreu();
                    for(int i=0; i< listaIntrebariGrele.size();i++){
                        if(listaIntrebariGrele.get(i).getOptiuni().getCategorie().equals(Constante.CATEGORIE_VIATA)){
                            listaIntrebariTest.add(listaIntrebariGrele.get(i));
                        }
                        Intent intent = new Intent(getApplicationContext(), IntrebariActivitate.class);
                        //intent.putParcelableArrayListExtra("Intrebari matematica key", listaIntrebariMatematica);
                       // intent.putExtra("lista intrebari key", listaIntrebariMatematica);
                        intent.putExtra("lll",listaIntrebariTest);
                        startActivity(intent);
                    }
                }

            }
        };


    }
    private View.OnClickListener deschideTestMatematica(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaIntrebariTest= new ArrayList<>();
                listaIntrebariUsoare=new ArrayList<>();
                if( dificultate.equals(Constante.USOR_DIFICULTATE_TEST)){
                    if(setIntrebari!=null){
                        listaIntrebariUsoare=setIntrebari.getUsor();
                        for(int i=0; i< listaIntrebariUsoare.size();i++){
                            if(listaIntrebariUsoare.get(i).getOptiuni().getCategorie().equals(Constante.CATEGORIE_MATEMATICA)){
                                listaIntrebariTest.add(listaIntrebariUsoare.get(i));
                            //    Toast.makeText(getApplicationContext(), listaIntrebariUsoare.get(i).toString(),Toast.LENGTH_LONG).show();
                            }


                        }
                        Intent intent = new Intent(getApplicationContext(), IntrebariActivitate.class);
                     //   intent.putParcelableArrayListExtra("Intrebari matematica key", listaIntrebariMatematica);
                        intent.putExtra("lll",listaIntrebariTest);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), R.string.toast_nu_exista_intrebari, Toast.LENGTH_LONG).show();
                    }

                }
                else if( dificultate.equals(Constante.MEDIU_DIFICULTATE_TEST)){
                    listaIntrebariMedii=setIntrebari.getMediu();
                    for(int i=0; i< listaIntrebariMedii.size();i++){
                        if(listaIntrebariMedii.get(i).getOptiuni().getCategorie().equals(Constante.CATEGORIE_MATEMATICA)){
                            listaIntrebariTest.add(listaIntrebariMedii.get(i));
                        }
                        Intent intent = new Intent(getApplicationContext(), IntrebariActivitate.class);
                      //  intent.putParcelableArrayListExtra("Intrebari matematica key", listaIntrebariMatematica);
                        intent.putExtra("lll",listaIntrebariTest);
                        startActivity(intent);
                    }
                }
                else if( dificultate.equals(Constante.GREU_DIFICULTATE_TEST)){
                    listaIntrebariGrele=setIntrebari.getGreu();
                    for(int i=0; i< listaIntrebariGrele.size();i++){
                        if(listaIntrebariGrele.get(i).getOptiuni().getCategorie().equals(Constante.CATEGORIE_MATEMATICA)){
                            listaIntrebariTest.add(listaIntrebariGrele.get(i));
                        }
                        Intent intent = new Intent(getApplicationContext(), IntrebariActivitate.class);
                        //intent.putParcelableArrayListExtra("Intrebari matematica key", listaIntrebariMatematica);
                       // intent.putExtra("lista intrebari key", listaIntrebariMatematica);
                        intent.putExtra("lll",listaIntrebariTest);
                        startActivity(intent);
                    }
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
