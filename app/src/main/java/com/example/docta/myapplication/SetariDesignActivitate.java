package com.example.docta.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SetariDesignActivitate extends AppCompatActivity {

    private Button btn_back;
    private Spinner spn_fonturi;
    private Spinner spn_dimensiuni;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_setari_design);
        if(savedInstanceState==null){
            String titlu = getString(R.string.Titlu_Design);
            this.setTitle(titlu);
        }

        spn_fonturi=findViewById(R.id.design_spn_font);
        ArrayAdapter<CharSequence> adapter_font=ArrayAdapter.createFromResource(getApplicationContext(), R.array.setari_design_spn_font_text, R.layout.support_simple_spinner_dropdown_item);
        spn_fonturi.setAdapter(adapter_font);

        spn_dimensiuni=findViewById(R.id.design_spn_textsize);
        ArrayAdapter<CharSequence> adapter_dimensiuni=ArrayAdapter.createFromResource(getApplicationContext(), R.array.setari_design_spn_dimensiuni_text, R.layout.support_simple_spinner_dropdown_item);
        spn_dimensiuni.setAdapter(adapter_dimensiuni);

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
