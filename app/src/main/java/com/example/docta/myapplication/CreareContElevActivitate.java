package com.example.docta.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docta.myapplication.util.Constante;
import com.example.docta.myapplication.clase.Elev;

import java.util.Date;

public class CreareContElevActivitate extends AppCompatActivity {

    private Button btn_back;
    private Button btn_creare;
    private Spinner spn_varsta;
    private TextView tvNume;
    private RadioGroup rgGen;
    Intent intent;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_creare_cont_elev);
        intent=getIntent();

        btn_back=findViewById(R.id.lecc_btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(),ContElevActivitate.class);
                startActivity(intent);
            }
        });

        spn_varsta=findViewById(R.id.lecc_spinner_varsta);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.lecc_spn_varsta,R.layout.support_simple_spinner_dropdown_item);
        initComponents();
    }

    private void initComponents() {
        btn_back = findViewById(R.id.lecc_btn_back);
        spn_varsta = findViewById(R.id.lecc_spinner_varsta);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.lecc_spn_varsta, R.layout.support_simple_spinner_dropdown_item);
        spn_varsta.setAdapter(adapter);
        btn_creare = findViewById(R.id.lecc_btn_creare);
        tvNume=findViewById(R.id.lecc_tid_numeavatar);
        rgGen=findViewById(R.id.lecc_rg_fb);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* intent = new Intent(getApplicationContext(), ContElevActivitate.class);
                startActivity(intent);*/
               onBackPressed();
            }
        });


        btn_creare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(),ContElevActivitate.class);
                startActivity(intent);
            }
        });

        btn_creare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreferences=getSharedPreferences(Constante.CONT_STATUT_PREF,MODE_PRIVATE);
                String statut= sharedPreferences.getString(Constante.UTILIZATOR_PREF, "elev");
                if (statut.compareTo(getString(R.string.principala_utilizator_profesor_pref_message))==0) {
                    String nume = tvNume.getText().toString();
                    int gen= rgGen.getCheckedRadioButtonId();
                    int varsta = Integer.parseInt(spn_varsta.getSelectedItem().toString());

                    Elev elev = new Elev(nume, varsta, gen);
                    intent.putExtra(Constante.ADAUGARE_ELEV_KEY,elev);
                    setResult(RESULT_OK, intent);
                    finish();
                } else if (statut.compareTo(getString(R.string.principala_utilizator_elev_pref_message))==0){
                    intent = new Intent(getApplicationContext(), PaginaPrincipalaJocActivitate.class);
                    intent.putExtra(Constante.NUME_KEY, tvNume.getText().toString());
                    startActivity(intent);
                }

            }
        });

    }
}