package com.example.docta.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ContElevActivitate extends AppCompatActivity {

    private TextInputEditText tie_nume_avatar;
    private Button btn_back;
    private Button btn_autentificare;
    private Button btn_crearecont;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_cont_elev);

        tie_nume_avatar = findViewById(R.id.autentificare_elev_tid_numeavatar);
         btn_back=findViewById(R.id.loginelev_btn_back);

        /* btn_back.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 intent=new Intent(getApplicationContext(),PrincipalaActivitate.class);
                 startActivity(intent);
             }
         });*/

         btn_autentificare=findViewById(R.id.loginelev_btn_autentificare);

         btn_autentificare.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String nume = tie_nume_avatar.getText().toString();

                 intent=new Intent(getApplicationContext(),PaginaPrincipalaJocActivitate.class);
                 intent.putExtra("NUME_KEY",nume);
                 startActivity(intent);
             }
         });

         btn_crearecont=findViewById(R.id.loginelev_btn_crearecont);

         btn_crearecont.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 intent=new Intent(getApplicationContext(),CreareContElevActivitate.class);
                 startActivity(intent);
             }
         });

    }
}
