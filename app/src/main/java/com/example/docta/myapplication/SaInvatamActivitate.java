package com.example.docta.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class SaInvatamActivitate extends AppCompatActivity {
    private Button btnAnimale;
    private Button btnFructe;
    private Button btnLegume;
    private Button btnTransport;
    private Button btnTimp;
    private Button btnCulori;
    private Button btnCifre;
    private Button btnLitere;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_sa_invatam);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initComponents();
    }
private  void initComponents(){
        btnAnimale=findViewById(R.id.invatam_btn_animale);
        btnCifre=findViewById(R.id.invatam_btn_cifre);
        btnCulori=findViewById(R.id.invatam_btn_culori);
        btnFructe=findViewById(R.id.invatam_btn_fructe);
        btnLegume=findViewById(R.id.invatam_btn_legume);
        btnLitere=findViewById(R.id.invatam_btn_litere);
        btnTimp=findViewById(R.id.invatam_btn_timp);
        btnTransport=findViewById(R.id.invatam_btn_transport);

        btnAnimale.setOnClickListener(startCateg());
        btnCulori.setOnClickListener(startCateg());
        btnTransport.setOnClickListener(startCateg());
        btnTimp.setOnClickListener(startCateg());
        btnCifre.setOnClickListener(startCateg());
        btnFructe.setOnClickListener(startCateg());
        btnLegume.setOnClickListener(startCateg());
        btnLitere.setOnClickListener(startCateg());
}

private View.OnClickListener startCateg(){
    return new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), InvatamCategorieActivitate.class);
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
