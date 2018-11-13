package com.example.docta.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class SetariActivitate extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_setari);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem item = menu.getItem(2);
        item.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.menu_clasament:
                        startActivity(new Intent(getApplicationContext(),ClasamentProfesorActivitate.class));
                        break;
                    case R.id.menu_lista:
                        startActivity(new Intent(getApplicationContext(),ListaEleviActivitate.class));
                        break;
                    case R.id.menu_profil:
                        startActivity(new Intent(getApplicationContext(),PareriActivitate.class));
                        break;
                }
                return false;
            }
        });
    }
}
