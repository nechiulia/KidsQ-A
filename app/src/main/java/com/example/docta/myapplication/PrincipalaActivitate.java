package com.example.docta.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class PrincipalaActivitate extends AppCompatActivity
{
    private Button elev_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_principala);

        elev_btn=findViewById(R.id.main_btn_elev);

        elev_btn.setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent=new Intent(getApplicationContext(),ContElevActivitate.class);
                                            startActivity(intent);
                                        }
                                    }
        );



    }
}
