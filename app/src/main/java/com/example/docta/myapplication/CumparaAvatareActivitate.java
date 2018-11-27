package com.example.docta.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CumparaAvatareActivitate extends AppCompatActivity {

    private Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_cumpara_avatare);
        if(savedInstanceState==null){
            String titlu = getString(R.string.Titlu_Avatare);
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
        btn_back=findViewById(R.id.avatare_btn_inapoi);
    }
}
