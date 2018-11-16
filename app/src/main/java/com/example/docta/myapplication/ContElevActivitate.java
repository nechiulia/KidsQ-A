package com.example.docta.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.docta.myapplication.util.Constante;

public class ContElevActivitate extends AppCompatActivity {

    private TextInputEditText tie_nume_avatar;
    private Button btn_back;
    private Button btn_autentificare;
    private Button btn_crearecont;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_cont_elev);

        initComponents();

         btn_back.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 onBackPressed();
             }
         });



         btn_autentificare.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(isValid()){
                     String nume = tie_nume_avatar.getText().toString();
                     intent = new Intent(getApplicationContext(), PaginaPrincipalaJocActivitate.class);
                     intent.putExtra(Constante.NUME_KEY, nume);
                     startActivity(intent);
                 }
             }
         });



         btn_crearecont.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 intent=new Intent(getApplicationContext(),CreareContElevActivitate.class);
                 startActivity(intent);
             }
         });

    }
    private void initComponents(){
        btn_autentificare=findViewById(R.id.loginelev_btn_autentificare);
        btn_crearecont=findViewById(R.id.loginelev_btn_crearecont);
        tie_nume_avatar = findViewById(R.id.autentificare_elev_tid_numeavatar);
        btn_back=findViewById(R.id.loginelev_btn_back);
    }

    public boolean isValid(){
        if(tie_nume_avatar.getText() == null || tie_nume_avatar.getText().toString().contains(" ") || tie_nume_avatar.getText().toString().trim().isEmpty()){
            tie_nume_avatar.setError(getText(R.string.cont_elev_numeavatar_eroare));
            Toast.makeText(getApplicationContext(),R.string.cont_elev_numeavatar_eroare,Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
