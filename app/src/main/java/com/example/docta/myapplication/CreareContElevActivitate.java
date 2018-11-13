package com.example.docta.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class CreareContElevActivitate extends AppCompatActivity {

    private Button btn_back;
    private Button btn_creare;
    private Spinner spn_varsta;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_creare_cont_elev);

        btn_back=findViewById(R.id.lecc_btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(),ContElevActivitate.class);
                startActivity(intent);
            }
        });

        spn_varsta=findViewById(R.id.lecc_spinner_varsta);
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(getApplicationContext(),R.array.lecc_spn_varsta,R.layout.support_simple_spinner_dropdown_item);
        spn_varsta.setAdapter(adapter);

        btn_creare=findViewById(R.id.lecc_btn_creare);
        btn_creare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(),PaginaPrincipalaJocActivitate.class);
                startActivity(intent);
            }
        });


    }
}
