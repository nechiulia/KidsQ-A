package com.example.docta.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AutentificareElevActivitate extends AppCompatActivity {

    private Button btn_back;
    private Button btn_continua;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_autentifcare_elev);

        btn_back=findViewById(R.id.lae_btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(),ContElevActivitate.class);
                startActivity(intent);
            }
        });



        btn_continua=findViewById(R.id.lea_btn_continua);
        btn_continua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(),PaginaPrincipalaJocActivitate.class);
                startActivity(intent);
            }
        });

    }
}
