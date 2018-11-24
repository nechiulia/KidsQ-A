package com.example.docta.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.docta.myapplication.util.Constante;

public class SetariElevActivitate extends AppCompatActivity {

    private Button btn_back;
    private Button btn_design;
    private Button btn_logout;
    Intent intent;
    private Spinner spn_dificultati;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_setari_elev);
        initComps();


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constante.DIFICULTATE_PREF, spn_dificultati.getSelectedItem().toString());
                editor.commit();
                intent=new Intent(getApplicationContext(), PaginaPrincipalaJocActivitate.class);
                startActivity(intent);
                finish();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(), PrincipalaActivitate.class);
                startActivity(intent);
                finish();
            }
        });

        btn_design.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(), SetariDesignActivitate.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initComps(){
        spn_dificultati=findViewById(R.id.setari_spn_dificultate);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getApplicationContext(), R.array.setari_spn_dificultati, R.layout.support_simple_spinner_dropdown_item);
        spn_dificultati.setAdapter(adapter);

        btn_back=findViewById(R.id.setari_btn_back);
        btn_design=findViewById(R.id.setari_btn_design);
        btn_logout=findViewById(R.id.setari_btn_logout);

        sharedPreferences = getSharedPreferences(Constante.SETARI_ELEV_PREF,MODE_PRIVATE);
    }

    private void restoreDificultate(){

    }
}
