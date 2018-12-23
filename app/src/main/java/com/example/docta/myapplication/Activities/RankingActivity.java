package com.example.docta.myapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.docta.myapplication.R;
import com.example.docta.myapplication.Classes.util.Constants;

public  class RankingActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private SharedPreferences sharedPreferences;
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
        String statut= sharedPreferences.getString(Constants.USER_STATUT_PREF, getString(R.string.principala_utilizator_elev_pref_message));
        if(statut.equals(getString(R.string.principala_utilizator_elev_pref_message))){
            bottomNavigationView.setVisibility(View.GONE);
        }

    }

}
