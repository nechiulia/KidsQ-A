package com.example.docta.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.docta.myapplication.clase.Constante;
import com.example.docta.myapplication.clase.Elev;
import com.example.docta.myapplication.clase.EleviAdaptor;

import java.util.ArrayList;
import java.util.List;

public class ListaEleviActivitate extends AppCompatActivity {

    private Button btn_adauga_elev;
     List<Elev> elevi= new ArrayList<>();
    private ListView lvElevi;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_lista_elevi);
        initComponents();

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()) {


                    case R.id.menu_setari:
                        intent = new Intent(getApplicationContext(),SetariActivitate.class);

                        startActivity(intent);
                        break;
                    case R.id.menu_clasament:
                        intent = new Intent(getApplicationContext(),ClasamentProfesorActivitate.class);

                        startActivity(intent);
                        break;

                    case R.id.menu_profil:
                        startActivity(new Intent(getApplicationContext(),PaginaPrincipalaJocActivitate.class));
                        break;

                }
                return false;
            }
        });

        btn_adauga_elev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), CreareContElevActivitate.class);
                //startActivity(intent);
                startActivityForResult(intent, Constante.ADAUGARE_ELEV_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constante.ADAUGARE_ELEV_REQUEST_CODE
                && resultCode == RESULT_OK && data != null ) {

            Elev elev = data.getParcelableExtra(Constante.ADAUGARE_ELEV_KEY);
            if(elev!=null){
                Toast.makeText(getApplicationContext(),
                        elev.toString(),
                        Toast.LENGTH_LONG).show();
                elevi.add(elev);

                EleviAdaptor adapter = (EleviAdaptor) lvElevi.getAdapter();
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Elev adaugat", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.lea_transfer_mesaj),
                    Toast.LENGTH_LONG).show();
        }

    }

    private void initComponents()
    { bottomNavigationView = findViewById(R.id.bottomNavigationView);
        btn_adauga_elev=findViewById(R.id.listaElevi_btn_adaugaElev);
        lvElevi= findViewById(R.id.listaElevi_lv_listaElevi);
        EleviAdaptor adapter = new EleviAdaptor(getApplicationContext(),
                R.layout.lv_elevi_rand, elevi, getLayoutInflater());

        lvElevi.setAdapter(adapter);
    }
}
