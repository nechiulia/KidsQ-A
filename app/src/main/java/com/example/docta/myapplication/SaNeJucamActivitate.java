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
   // private static final String URL = "https://api.myjson.com/bins/o60v6";
//   private static final String URL = "https://api.myjson.com/bins/g2j1e";
    private static final String URL = Constante.URL_JSON_TESTE;


    private SetIntrebari setIntrebari;
    SharedPreferences sharedPreferences;
    private List<Intrebare> listaIntrebariUsoare;
    private List<Intrebare> listaIntrebariMedii;
    private List<Intrebare> listaIntrebariGrele;

    public static ArrayList<Intrebare> listaIntrebariMatematica;
    public static ArrayList<Intrebare> listaIntrebariAnimale;
    public static ArrayList<Intrebare> listaIntrebariLitere;
    public static ArrayList<Intrebare> listaIntrebariFructeSiLegume;
    public static ArrayList<Intrebare> listaIntrebariViata;
    public static ArrayList<Intrebare> listaIntrebariTest;
    String dificultate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_sa_ne_jucam);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = getSharedPreferences(Constante.SETARI_ELEV_PREF,MODE_PRIVATE);

        @SuppressLint("StaticFieldLeak") HttpManager manager = new HttpManager(){
            @Override
            protected void onPostExecute(String s) {
                try {
                    setIntrebari=SetIntrebariParser.fromJson(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),getString(R.string.jucam_parsare_eroare), Toast.LENGTH_LONG).show();
                }

            }
        };
        manager.execute(URL);
        initComponents();
    }
    private void initComponents(){

        btnMatematica= findViewById(R.id.jucam_btn_matematica);
        btnLitere=findViewById(R.id.jucam_btn_litere);
        btnFructe=findViewById(R.id.jucam_btn_fructe_legume);
        btnViata=findViewById(R.id.jucam_btn_viata_dezicuzi);
        btnAnimale=findViewById(R.id.jucam_btn_animale);
        listaIntrebariTest=new ArrayList<Intrebare>();
        listaIntrebariUsoare= new ArrayList<>();
        listaIntrebariMedii= new ArrayList<>();
        listaIntrebariGrele= new ArrayList<>();
        btnMatematica.setOnClickListener(deschideTestMatematica());
        btnFructe.setOnClickListener(deschideTestFructe());
        btnLitere.setOnClickListener(deschideTestLitere());
        btnAnimale.setOnClickListener(deschideTestAnimale());
        btnViata.setOnClickListener(deschideTestViata());
       dificultate = sharedPreferences.getString(Constante.DIFICULTATE_PREF,null);

    }
    private View.OnClickListener deschideTestAnimale(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( dificultate.equals(Constante.USOR_DIFICULTATE_TEST)){
                    if(setIntrebari!=null){
                        listaIntrebariUsoare=setIntrebari.getUsor();
                        for(int i=0; i< listaIntrebariUsoare.size();i++){
                            if(listaIntrebariUsoare.get(i).getOptiuni().getCategorie().equals(Constante.CATEGORIE_ANIMALE)){
                                listaIntrebariTest.add(listaIntrebariUsoare.get(i));
                            }
                      }
                        Intent intent = new Intent(getApplicationContext(), IntrebariActivitate.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), getString(R.string.toast_nu_exista_intrebari), Toast.LENGTH_LONG).show();
                    }

                }
                else if( dificultate.equals(Constante.MEDIU_DIFICULTATE_TEST)){
                    listaIntrebariMedii=setIntrebari.getMediu();
                    for(int i=0; i< listaIntrebariMedii.size();i++){
                        if(listaIntrebariMedii.get(i).getOptiuni().getCategorie().equals(Constante.CATEGORIE_ANIMALE)){
                            listaIntrebariTest.add(listaIntrebariMedii.get(i));
                        }
                        Intent intent = new Intent(getApplicationContext(), IntrebariActivitate.class);
                        //  intent.putParcelableArrayListExtra("Intrebari matematica key", listaIntrebariMatematica);
                        startActivity(intent);
                    }
                }
                else if( dificultate.equals(Constante.GREU_DIFICULTATE_TEST)){
                    listaIntrebariGrele=setIntrebari.getGreu();
                    for(int i=0; i< listaIntrebariGrele.size();i++){
                        if(listaIntrebariGrele.get(i).getOptiuni().getCategorie().equals(Constante.CATEGORIE_ANIMALE)){
                            listaIntrebariTest.add(listaIntrebariGrele.get(i));
                        }
                        Intent intent = new Intent(getApplicationContext(), IntrebariActivitate.class);
                        //intent.putParcelableArrayListExtra("Intrebari matematica key", listaIntrebariMatematica);
                        //intent.putExtra("lista intrebari key", listaIntrebariMatematica);
                        startActivity(intent);
                    }
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
                        //   intent.putParcelableArrayListExtra("Intrebari matematica key", listaIntrebariMatematica);
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
                        //  intent.putParcelableArrayListExtra("Intrebari matematica key", listaIntrebariMatematica);
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
                        //intent.putParcelableArrayListExtra("Intrebari matematica key", listaIntrebariMatematica);
                        //intent.putExtra("lista intrebari key", listaIntrebariLitere);
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
