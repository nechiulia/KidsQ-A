package com.example.docta.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public  class TeachersRankingActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_ranking);
        if(savedInstanceState==null){
            String titlu = getString(R.string.Titlu_Clasament);
            this.setTitle(titlu);
        }
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3); //by default selecteaza indexul
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()) {

                    case R.id.menu_lista:
                        intent = new Intent(TeachersRankingActivity.this,ListStudentsActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.menu_setari:
                        intent = new Intent(getApplicationContext(),SettingsActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.menu_profil:
                        startActivity(new Intent(getApplicationContext(),HomePageActivity.class));
                        finish();
                        break;
                }
                return false;
            }
        });
    }

}
