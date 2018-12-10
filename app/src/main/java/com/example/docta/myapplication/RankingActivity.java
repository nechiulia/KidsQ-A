package com.example.docta.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.docta.myapplication.Classes.Database.StudentDAO;
import com.example.docta.myapplication.Classes.Student;
import com.example.docta.myapplication.util.Constants;

import java.lang.reflect.Array;
import java.util.ArrayList;

public  class RankingActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private SharedPreferences sharedPreferences;
    private ArrayList<Student> StudentsList;
    private StudentDAO StudentDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        if(savedInstanceState==null){
            String title = getString(R.string.Titlu_Clasament);
            this.setTitle(title);
        }
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        sharedPreferences=getSharedPreferences(Constants.CONT_STATUT_PREF,MODE_PRIVATE);
        String statut= sharedPreferences.getString(Constants.USER_PREF, getString(R.string.principala_utilizator_elev_pref_message));
        if(statut.equals(getString(R.string.principala_utilizator_elev_pref_message))){
            bottomNavigationView.setVisibility(View.GONE);

        }
        else {
            Menu menu = bottomNavigationView.getMenu();
            MenuItem menuItem = menu.getItem(3); //by default selecteaza indexul
            menuItem.setChecked(true);

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Intent intent;
                    switch (menuItem.getItemId()) {

                        case R.id.menu_lista:
                            intent = new Intent(RankingActivity.this,ListStudentsActivity.class);
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

}
