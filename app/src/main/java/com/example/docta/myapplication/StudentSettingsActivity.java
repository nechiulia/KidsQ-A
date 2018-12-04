package com.example.docta.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.docta.myapplication.util.Constants;

public class StudentSettingsActivity extends AppCompatActivity {

    private Button btn_back;
    private Button btn_design;
    private Button btn_logout;
    Intent intent;
    private Spinner spn_difficulty;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_students);
        initComps();
        if(savedInstanceState==null){
            String title = getString(R.string.Titlu_SetariCont);
            this.setTitle(title);
        }


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.DIFFICULTY_PREF, spn_difficulty.getSelectedItem().toString());
                int selectedPosition = spn_difficulty.getSelectedItemPosition();
                editor.putInt(Constants.SPINNER_POSITION, selectedPosition);
                editor.commit();
                intent=new Intent(getApplicationContext(), HomePageActivity.class);
                intent.putExtra(Constants.DOWNLOAD_DONE,true);
                startActivity(intent);
                finish();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(), LoginPageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_design.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(), SetDesignActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initComps(){
        spn_difficulty =findViewById(R.id.settings_spn_difficulty);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getApplicationContext(), R.array.setari_spn_dificultati, R.layout.support_simple_spinner_dropdown_item);
        spn_difficulty.setAdapter(adapter);

        btn_back=findViewById(R.id.settings_btn_back);
        btn_design=findViewById(R.id.settings_btn_design);
        btn_logout=findViewById(R.id.settings_btn_logout);

        sharedPreferences = getSharedPreferences(Constants.STUDENT_SETTINGS_PREF,MODE_PRIVATE);
        restoreDifficulty();
    }


    private void restoreDifficulty(){
        spn_difficulty.setSelection(sharedPreferences.getInt(Constants.SPINNER_POSITION,0));
    }
}
