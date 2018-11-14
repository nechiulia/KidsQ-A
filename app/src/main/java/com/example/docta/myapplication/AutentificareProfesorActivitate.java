package com.example.docta.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AutentificareProfesorActivitate extends AppCompatActivity {

    private Button btn_login;
    private Button btn_back;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_autentificare_profesor);

        btn_login=findViewById(R.id.loginprof_btn_login);
       btn_back=findViewById(R.id.loginprof_btn_back);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent= new Intent(getApplicationContext(), ListaEleviActivitate.class);
                startActivity(intent);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(), PrincipalaActivitate.class);
                startActivity(intent);
            }
        });
    }
}
