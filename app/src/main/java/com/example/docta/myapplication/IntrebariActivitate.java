package com.example.docta.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class IntrebariActivitate extends AppCompatActivity {
    private TextView tvNrIntrebare;
    private Button btnConfirm;
    private int nrCurent=1;
    private RadioGroup rgOptiuniRaspuns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_intrebari);
        initComponents();
    }

    private void initComponents(){
        tvNrIntrebare=findViewById(R.id.intrebari_tv_nr_intrebare);
        btnConfirm=findViewById(R.id.intrebari_btn_confirm);
        btnConfirm.setOnClickListener(confirm());
        rgOptiuniRaspuns= findViewById(R.id.intrebari_rg_raspunsuri);
        nrCurent=1;
        tvNrIntrebare.setText(nrCurent+getString(R.string.intrebari_tv_nr_intrebari));

    }

    public View.OnClickListener confirm(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nrCurent++;
                if(nrCurent==11){
                    Intent intent= new Intent(getApplicationContext(), RezultatActivitate.class);
                    startActivity(intent);
                }
                else {
                    tvNrIntrebare.setText(nrCurent+getString(R.string.intrebari_tv_nr_intrebari));
                    rgOptiuniRaspuns.clearCheck();
                }


            }
        };
    }
}
