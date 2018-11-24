package com.example.docta.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.docta.myapplication.util.Constante;

public class RezultatActivitate extends AppCompatActivity {

    private Button btn_back;
    private TextView tvPunctaj;
    private TextView tvNrIntrebariCorecte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_rezultat);

        initComponents();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), PaginaPrincipalaJocActivitate.class);
                startActivity(intent);
            }
        });
    }

    private void initComponents(){
        double punctaj= getIntent().getDoubleExtra(Constante.PUNCTAJ_KEY,0);
        int nrIntrebariCorecte=getIntent().getIntExtra(Constante.NR_INTREBARI_CORECTE,0);

        btn_back=findViewById(R.id.rezultat_btn_inapoi);
        tvPunctaj=findViewById(R.id.rezultat_tv_puncte);
        tvNrIntrebariCorecte=findViewById(R.id.rezultat_tv_raspunsuri_corecte);
        tvPunctaj.setText(String.valueOf(punctaj));
        tvNrIntrebariCorecte.setText(String.valueOf(nrIntrebariCorecte));
    }
}
