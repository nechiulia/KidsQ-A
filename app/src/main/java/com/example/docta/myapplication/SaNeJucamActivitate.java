package com.example.docta.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import org.json.JSONException;

import java.util.List;

public class SaNeJucamActivitate extends AppCompatActivity {
    private Button btnMatematica;
    private Button btnMediu;
    private Button btnScoala;
   // private static final String URL = "https://api.myjson.com/bins/1avdxy";
    private SetIntrebari setIntrebari;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_sa_ne_jucam);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       /* @SuppressLint("StaticFieldLeak") HttpManager manager = new HttpManager(){
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
        manager.execute(URL);*/
        initComponents();
    }
    private void initComponents(){

        btnMatematica= findViewById(R.id.jucam_btn_matematica);
        btnMediu=findViewById(R.id.jucam_btn_mediul);
        btnScoala=findViewById(R.id.jucam_btn_scoala);

        btnMatematica.setOnClickListener(deschideTestMatematica());
        btnMediu.setOnClickListener(deschideTest());
        btnScoala.setOnClickListener(deschideTest());
    }
    private View.OnClickListener deschideTest(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IntrebariActivitate.class);
                startActivity(intent);
            }

        };

    }
    private View.OnClickListener deschideTestMatematica(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result="";
                Intent intent = new Intent(getApplicationContext(), IntrebariActivitate.class);
                /*List<Intrebare> intrebariUsoare= setIntrebari.getUsor();
                for(int i=0;i< intrebariUsoare.size();i++)
                if(intrebariUsoare.get(i).getOptiuni().getCategorie().equals(getString(R.string.jucam_intrebari_matematica)))
                    result+=intrebariUsoare.get(i).toString();
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
*/
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
