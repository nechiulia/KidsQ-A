package com.example.docta.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyAvatarsActivity extends AppCompatActivity {

    private Button btn_buy;
    private Button btn_back;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_avatars);
        if(savedInstanceState==null){
            String title = getString(R.string.Titlu_AvatareleMele);
            this.setTitle(title);
        }
        init();



    }
    private void init(){
        btn_buy =findViewById(R.id.avatare_disponibile_btn_cumpara);
        btn_back=findViewById(R.id.avatare_disponibile_btn_inapoi);

        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(), PurchaseAvatarsActivity.class);
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
