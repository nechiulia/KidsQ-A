package com.example.docta.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class IntrebariActivitate extends AppCompatActivity {
    private TextView tvNrIntrebare;
    private int nrCurent=1;
    private RadioGroup rg_raspunsuri;
    private RadioButton rb_raspuns;
    private Button btn_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_intrebari);
        initComponents();
    }


    private View.OnClickListener confirmRaspuns(){
        return new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (isValid()) {
                   // rb_raspuns.setTextColor(Color.GREEN);

                    nrCurent++;
                    if(nrCurent==11){
                        Intent intent= new Intent(getApplicationContext(), RezultatActivitate.class);
                        startActivity(intent);
                    }
                    else {
                        tvNrIntrebare.setText(nrCurent + getString(R.string.intrebari_tv_nr_intrebari));
                        rg_raspunsuri.clearCheck();
                    }
                }
            }
        };

    }

    public boolean isValid(){
        if(rg_raspunsuri.getCheckedRadioButtonId()==-1){
            rb_raspuns.setError(getString(R.string.intrebarea_zilei_raspuns_eroare));
            Toast.makeText(getApplicationContext(),getString(R.string.intrebarea_zilei_raspuns_eroare),Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            rb_raspuns.setError(null);
            return true;
        }

    }

    private void initComponents(){
        tvNrIntrebare=findViewById(R.id.intrebari_tv_nr_intrebare);
        rg_raspunsuri = findViewById(R.id.intrebari_rg_raspunsuri);
        rb_raspuns = findViewById(R.id.intrebari_rb_raspuns3);
        btn_confirm = findViewById(R.id.intrebari_btn_confirm);
        btn_confirm.setOnClickListener(confirmRaspuns());
        nrCurent=1;
        tvNrIntrebare.setText(nrCurent+getString(R.string.intrebari_tv_nr_intrebari));



    }


}
