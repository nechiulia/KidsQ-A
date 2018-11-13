package com.example.docta.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docta.myapplication.clase.Constante;
import com.example.docta.myapplication.clase.Elev;

import java.util.Date;

public class CreareContElevActivitate extends AppCompatActivity {

    private Button btn_back;
    private Button btn_creare;
    private Spinner spn_varsta;
    private TextView tvNume;
    private RadioButton rbBaiat;
    private RadioButton rbFata;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_creare_cont_elev);
        intent=getIntent();
        initComponents();
    }

    private void initComponents() {
        btn_back = findViewById(R.id.lecc_btn_back);
        spn_varsta = findViewById(R.id.lecc_spinner_varsta);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.lecc_spn_varsta, R.layout.support_simple_spinner_dropdown_item);
        spn_varsta.setAdapter(adapter);

        btn_creare = findViewById(R.id.lecc_btn_creare);
        tvNume=findViewById(R.id.lecc_tid_numeavatar);
        rbBaiat= (RadioButton)findViewById(R.id.lecc_rb_baietel);
        rbFata=(RadioButton)findViewById(R.id.lecc_rb_fetita);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), ContElevActivitate.class);
                startActivity(intent);
            }
        });


        btn_creare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean statut = getIntent().getBooleanExtra(Constante.STATUT_KEY, true);
                if (statut==true) {
                    String nume = tvNume.getText().toString();
                    boolean gen= (rbBaiat.isChecked())? true:false;
                    int varsta = Integer.parseInt(spn_varsta.getSelectedItem().toString());

                    Elev elev = new Elev(nume, varsta, gen);
                    intent.putExtra(Constante.ADAUGARE_ELEV_KEY,elev);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    intent = new Intent(getApplicationContext(), PaginaPrincipalaJocActivitate.class);
                    startActivity(intent);
                }

            }
        });

    }
}