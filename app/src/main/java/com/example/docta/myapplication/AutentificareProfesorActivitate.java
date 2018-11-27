package com.example.docta.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.docta.myapplication.util.Constante;

public class AutentificareProfesorActivitate extends AppCompatActivity {

    private TextInputEditText tie_email;
    private TextInputEditText tie_parola;
    private Button btn_login;
    private Button btn_back;
    Intent intent;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_autentificare_profesor);
        if(savedInstanceState==null){
            String titlu = getString(R.string.Titlu_AutentificareProfesor);
            this.setTitle(titlu);
        }
        initComponents();


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()) {
                    intent = new Intent(getApplicationContext(), ListaEleviActivitate.class);
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putString(Constante.PAROLA_PREF, tie_parola.getText().toString());
                    editor.putString(Constante.EMAIL_PREF, tie_email.getText().toString());
                    boolean result= editor.commit();
                    startActivity(intent);
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
    }
    private void initComponents(){
        tie_email = findViewById(R.id.loginprof_tid_email);
        tie_parola = findViewById(R.id.loginprof_tid_parola);
        btn_login=findViewById(R.id.loginprof_btn_login);
        btn_back=findViewById(R.id.loginprof_btn_back);
        sharedPreferences=getSharedPreferences(Constante.PAROLA_PROF_PREF, MODE_PRIVATE);

    }

    public boolean isValid(){
        if(TextUtils.isEmpty(tie_email.getText()) || tie_email.getText().toString().trim().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(tie_email.getText().toString()).matches()){
            tie_email.setError(getText(R.string.autentificare_profesor_email_eroare));
            Toast.makeText(getApplicationContext(),R.string.autentificare_profesor_email_eroare,Toast.LENGTH_LONG).show();
            return false;
        }else if(tie_parola.getText() == null || tie_parola.getText().toString().trim().isEmpty() || tie_parola.getText().toString().contains(" ")){
            tie_parola.setError(getText(R.string.autentificare_profesor_parola_eroare));
            Toast.makeText(getApplicationContext(),R.string.autentificare_profesor_parola_eroare,Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
