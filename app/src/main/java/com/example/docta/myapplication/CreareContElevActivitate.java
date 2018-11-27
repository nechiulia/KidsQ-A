package com.example.docta.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
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
    private TextInputEditText tie_nume;
    private RadioGroup rgGen;
    Intent intent;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_creare_cont_elev);
        intent=getIntent();

        if(savedInstanceState==null){
            String titlu = getString(R.string.Titlu_CreareContElev);
            this.setTitle(titlu);
        }

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
        tie_nume=findViewById(R.id.lecc_tid_numeavatar);
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
                   if(isValid()){
                        String nume = tie_nume.getText().toString();
                        int gen = rgGen.getCheckedRadioButtonId();
                        int varsta = Integer.parseInt(spn_varsta.getSelectedItem().toString());

                        Elev elev = new Elev(nume, varsta, gen);
                        intent.putExtra(Constante.ADAUGARE_ELEV_KEY, elev);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                } else if (statut.compareTo(getString(R.string.principala_utilizator_elev_pref_message))==0){
                    if(isValid()){
                        intent = new Intent(getApplicationContext(), PaginaPrincipalaJocActivitate.class);
                        intent.putExtra(Constante.NUME_KEY, tie_nume.getText().toString());
                        startActivity(intent);
                    }
                }

            }
        });


    }
    public boolean isValid(){

        if(tie_nume.getText().toString().trim().isEmpty() || tie_nume.getText().toString().contains(" ")){
            tie_nume.setError(getString(R.string.creare_cont_elev_numeavatar_eroare));
            return false;
        }else if(rgGen.getCheckedRadioButtonId()==-1){
            Toast.makeText(getApplicationContext(),getString(R.string.creare_cont_elev_gen_eroare),Toast.LENGTH_LONG).show();
            return false;
        }else if(spn_varsta.getSelectedItem().toString().trim().equals(getString(R.string.creare_cont_elev_varsta_alegere))){
            Toast.makeText(getApplicationContext(),R.string.creare_cont_elev_varsta_eroare,Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}