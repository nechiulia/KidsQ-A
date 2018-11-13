package com.example.docta.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class SaNeJucamActivitate extends AppCompatActivity {
    private Button btnLogica;
    private Button btnMediu;
    private Button btnScoala;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_sa_ne_jucam);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initComponents();
    }
    private void initComponents(){

        btnLogica= findViewById(R.id.jucam_btn_logica);
        btnMediu=findViewById(R.id.jucam_btn_mediul);
        btnScoala=findViewById(R.id.jucam_btn_scoala);

        btnLogica.setOnClickListener(deschideTest());
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id= item.getItemId();
        if(id==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
