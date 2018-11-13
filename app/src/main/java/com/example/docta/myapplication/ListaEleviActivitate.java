package com.example.docta.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ListaEleviActivitate extends AppCompatActivity {

    private Button btn_adauga_elev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BottomNavigationView bottomNavigationView;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_lista_elevi);
        initComponent();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
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
                startActivity(intent);
            }
        });
    }

    private void initComponent(){
        btn_adauga_elev=findViewById(R.id.listaElevi_btn_adaugaElev);
    }
}
