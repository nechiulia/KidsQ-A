package com.example.docta.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RezultatActivitate extends AppCompatActivity {

    private Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_rezultat);
        initComponent();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), PaginaPrincipalaJocActivitate.class);
                startActivity(intent);
            }
        });
    }

    private void initComponent(){
        btn_back=findViewById(R.id.rezultat_btn_inapoi);
    }
}
