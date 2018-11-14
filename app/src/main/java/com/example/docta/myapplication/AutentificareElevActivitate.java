package com.example.docta.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AutentificareElevActivitate extends AppCompatActivity {

    private Button btn_back;
    private Button btn_continua;
    private TextView tvNume;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_autentifcare_elev);
        initComponents();
        btn_back.setOnClickListener(v -> {
                    intent = new Intent(getApplicationContext(), ContElevActivitate.class);
                    startActivity(intent);
                    btn_back = findViewById(R.id.lecc_btn_back);
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(),ContElevActivitate.class);
                startActivity(intent);
            }
        });




        btn_continua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nume= tvNume.getText().toString();
                intent=new Intent(getApplicationContext(),PaginaPrincipalaJocActivitate.class);
                intent.putExtra("NUME_KEY", nume);
                startActivity(intent);
            }
        });

    }
    private void initComponents(){
        btn_continua=findViewById(R.id.autentificare_elev_btn_continua);
        btn_back=findViewById(R.id.autentificare_elev_btn_back);
        tvNume=findViewById(R.id.autentificare_elev_tid_numeavatar);
    }
}
