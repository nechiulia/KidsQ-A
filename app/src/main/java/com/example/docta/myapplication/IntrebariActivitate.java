package com.example.docta.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class IntrebariActivitate extends AppCompatActivity {
    private TextView tvNrIntrebare;
    private Button btnConfirm;
    private int nrCurent=1;
    private RadioGroup rgOptiuniRaspuns;
    private RadioButton rbR1;
    private RadioButton rbR2;
    private RadioButton rbR3;
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
        rbR1=findViewById(R.id.intrebari_rb_raspuns1);
        rbR2=findViewById(R.id.intrebari_rb_raspuns2);
        rbR3=findViewById(R.id.intrebari_rb_raspuns3);


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
                else if(rbR1.isChecked() || rbR2.isChecked()||rbR3.isChecked()){
                    tvNrIntrebare.setText(nrCurent+getString(R.string.intrebari_tv_nr_intrebari));
                    rgOptiuniRaspuns.clearCheck();
                }
                else {
                    Toast.makeText(getApplicationContext(), getString(R.string.intrebarea_zilei_toast__selecteaza_raspuns), Toast.LENGTH_LONG).show();
                    nrCurent--;
                }


            }
        };
    }
}
