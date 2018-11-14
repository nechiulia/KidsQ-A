package com.example.docta.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IntrebareaZileiActivitate extends AppCompatActivity {

    private Button btn_rezultat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_intrebarea_zilei);
        initComponents();

        btn_rezultat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), RezultatActivitate.class);
                startActivity(intent);
            }
        });
    }

    private void initComponents(){

        btn_rezultat=findViewById(R.id.intrebarea_zilei_btn_confirm);
    }

}
