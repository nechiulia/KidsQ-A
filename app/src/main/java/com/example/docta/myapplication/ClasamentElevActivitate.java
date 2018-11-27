package com.example.docta.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ClasamentElevActivitate extends AppCompatActivity {

    private Button btn_back;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_clasament_elev);
        if(savedInstanceState==null){
            String titlu = getString(R.string.Titlu_Clasament);
            this.setTitle(titlu);
        }
        initComponents();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    private void initComponents(){
        btn_back=findViewById(R.id.clasament_btn_back);

    }

}
