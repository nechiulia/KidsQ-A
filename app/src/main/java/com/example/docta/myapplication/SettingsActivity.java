package com.example.docta.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.docta.myapplication.util.Constante;

public class SettingsActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    private TextInputEditText tie_email;
    private TextInputEditText tie_parola_veche;
    private TextInputEditText tie_parola_noua;
    private TextInputEditText tie_parola_confirm;

    private Button btn_salveaza;
    private SharedPreferences sharedPreferences;
    Intent intent = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        if(savedInstanceState==null){
            String titlu = getString(R.string.Titlu_SetariProfesor);
            this.setTitle(titlu);
        }
        initComponent();
        Menu menu = bottomNavigationView.getMenu();
        MenuItem item = menu.getItem(2);
        item.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.menu_clasament:
                        startActivity(new Intent(getApplicationContext(),TeachersRankingActivity.class));
                        break;
                    case R.id.menu_lista:
                        startActivity(new Intent(getApplicationContext(),ListStudentsActivity.class));
                        break;
                    case R.id.menu_profil:
                        startActivity(new Intent(getApplicationContext(),HomePageActivity.class));
                        break;
                }
                return false;
            }
        });

        btn_salveaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()) {
                    Toast.makeText(getApplicationContext(),getString(R.string.setari_prof_toast_succes),Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putString(Constante.PAROLA_PREF, tie_parola_noua.getText().toString());
                    boolean result= editor.commit();
                    finish();
                }

            }
        });
    }

    private void initComponent(){


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        btn_salveaza=findViewById(R.id.setari_salveaza_btn);
        tie_email = findViewById(R.id.setari_email_edit);
        tie_parola_veche = findViewById(R.id.setari_parolaV_edit);
        tie_parola_noua = findViewById(R.id.setari_parolaN_edit);
        tie_parola_confirm = findViewById(R.id.setari_parolaC_edit);
        sharedPreferences=getSharedPreferences(Constante.PAROLA_PROF_PREF, MODE_PRIVATE);
    }

    private boolean isValid(){
        String parolaVeche= sharedPreferences.getString(Constante.PAROLA_PREF, null);
        String email = sharedPreferences.getString(Constante.EMAIL_PREF,null);
        if(tie_email.getText().toString().compareTo(email)!=0 || tie_parola_veche.getText().toString().compareTo(parolaVeche)!=0) {
            Toast.makeText(getApplicationContext(), getString(R.string.setari_prof_toast_parolanecoresp),Toast.LENGTH_LONG).show();
            tie_parola_veche.setText("");
            return false;}
            if (TextUtils.isEmpty(tie_email.getText()) || !Patterns.EMAIL_ADDRESS.matcher(tie_email.getText().toString()).matches()
                    || tie_email.getText().toString().trim().isEmpty()) {
                tie_email.setError(getText(R.string.autentificare_profesor_email_eroare));
                return false;
            } else if ( tie_parola_veche.getText().toString().trim().isEmpty() || tie_parola_veche.getText().toString().contains(" ")) {
                tie_parola_veche.setError(getString(R.string.setari_parolaVeche_eroare));
                return false;
            } else if (tie_parola_noua.getText() == null || tie_parola_noua.getText().toString().trim().isEmpty() || tie_parola_noua.getText().toString().contains(" ")) {
                tie_parola_noua.setError(getString(R.string.autentificare_profesor_parola_eroare));
                return false;
            } else if (tie_parola_confirm.getText().toString().compareTo(tie_parola_noua.getText().toString()) != 0) {
                tie_parola_confirm.setError(getString(R.string.setari_parolaConfirm_eroare));
                return false;
            }

        return true;
    }


}
