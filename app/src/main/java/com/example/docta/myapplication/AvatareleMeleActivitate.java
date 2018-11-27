package com.example.docta.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AvatareleMeleActivitate extends AppCompatActivity {

    private Button btn_cumpara;
    private Button btn_back;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_avatarele_mele);
        if(savedInstanceState==null){
            String titlu = getString(R.string.Titlu_AvatareleMele);
            this.setTitle(titlu);
        }
        init();



    }
    private void init(){
        btn_cumpara=findViewById(R.id.avatare_disponibile_btn_cumpara);
        btn_back=findViewById(R.id.avatare_disponibile_btn_inapoi);

        btn_cumpara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(), CumparaAvatareActivitate.class);
                startActivity(intent);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


}
