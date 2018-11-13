package com.example.docta.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SetariDesignActivitate extends AppCompatActivity {

    private Button btn_back;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_setari_design);

        btn_back=findViewById(R.id.design_btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(), SetariElevActivitate.class);
                startActivity(intent);
            }
        });


    }
}
