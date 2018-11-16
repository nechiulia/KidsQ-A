package com.example.docta.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class IntrebareaZileiActivitate extends AppCompatActivity {

    private Button btn_rezultat;
    private RadioGroup rg_optiuni;
    private RadioButton rb_rapuns3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_intrebarea_zilei);
        initComponents();

        btn_rezultat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    Intent intent = new Intent(getApplicationContext(), RezultatActivitate.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void initComponents(){
        btn_rezultat=findViewById(R.id.intrebarea_zilei_btn_confirm);
        rg_optiuni = findViewById(R.id.intrebarea_zilei_rg_raspunsuri);
        rb_rapuns3 = findViewById(R.id.intrebarea_zilei_rb_raspuns3);
    }

    public boolean isValid(){
        if(rg_optiuni.getCheckedRadioButtonId()==-1){
            rb_rapuns3.setError(getString(R.string.intrebarea_zilei_raspuns_eroare));
            Toast.makeText(getApplicationContext(),getString(R.string.intrebarea_zilei_raspuns_eroare),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
