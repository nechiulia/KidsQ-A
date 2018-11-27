package com.example.docta.myapplication;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.docta.myapplication.R;
import com.example.docta.myapplication.clase.Sarcini;
import com.example.docta.myapplication.util.Constante;

import java.util.ArrayList;
import java.util.List;

public class SarciniActivitate extends AppCompatActivity {

    private ListView lvSarcini;
    private FloatingActionButton fab;
    private List<Sarcini> lvS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_sarcini);
        if(savedInstanceState==null){
            String titlu = getString(R.string.Titlu_Sarcini);
            this.setTitle(titlu);
        }
        lvSarcini=findViewById(R.id.sarcini_lv_task);
        fab=findViewById(R.id.sarcini_fab_adaugare);

        ArrayAdapter<Sarcini> adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,lvS);
        lvSarcini.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SarciniAdaugareActivitate.class);
                startActivityForResult(intent, Constante.ADAUGARE_SAA_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==Constante.ADAUGARE_SAA_REQUEST_CODE && resultCode==RESULT_OK  && data!=null)
        {
            Sarcini sarcini=data.getParcelableExtra(Constante.ADAUGA_SAA_KEY);

            if (sarcini!=null)
            {
                lvS.add(sarcini);
                ArrayAdapter<Sarcini> adapter=(ArrayAdapter<Sarcini>) lvSarcini.getAdapter();
                adapter.notifyDataSetChanged();
            }

            else
            {
                Toast.makeText(getApplicationContext(),getString(R.string.sa_txt_eroare), Toast.LENGTH_LONG).show();
            }

        }

    }
}
