package com.example.docta.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.docta.myapplication.util.Constants;


public class LoginPageActivity extends AppCompatActivity
{
    private Button btn_student;
    private Button btn_teacher;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        if(savedInstanceState==null){
            String title = getString(R.string.Titlu_Principala);
            this.setTitle(title);
        }
        initComponents();
        btn_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),StudentProfileActivity.class);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.putString(Constants.USER_PREF, getString(R.string.principala_utilizator_elev_pref_message));
                boolean result = editor.commit();
                startActivity(intent);
            }
        }) ;



        btn_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),LoginTeacherActivity.class);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.putString(Constants.USER_PREF, getString(R.string.principala_utilizator_profesor_pref_message));
                boolean result= editor.commit();
                startActivity(intent);
            }
        });


    }
    private void initComponents(){
        btn_student =findViewById(R.id.main_btn_elev);
        btn_teacher =findViewById(R.id.main_btn_prof);
        sharedPreferences=getSharedPreferences(Constants.CONT_STATUT_PREF, MODE_PRIVATE);

    }
}
