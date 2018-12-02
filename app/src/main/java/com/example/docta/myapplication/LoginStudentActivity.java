package com.example.docta.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.docta.myapplication.Classes.Student;
import com.example.docta.myapplication.util.Constants;

public class LoginStudentActivity extends AppCompatActivity {

    private Button btn_back;
    private Button btn_create;
    private Spinner spn_age;
    private TextInputEditText tie_name;
    private RadioGroup rg_gender;
    Intent intent;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_student);
        intent=getIntent();

        if(savedInstanceState==null){
            String title = getString(R.string.Titlu_CreareContElev);
            this.setTitle(title);
        }

        btn_back=findViewById(R.id.lecc_btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(),StudentProfileActivity.class);
                startActivity(intent);
            }
        });

        spn_age =findViewById(R.id.lecc_spinner_varsta);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.lecc_spn_varsta,R.layout.support_simple_spinner_dropdown_item);
        initComponents();
    }

    private void initComponents() {
        btn_back = findViewById(R.id.lecc_btn_back);
        spn_age = findViewById(R.id.lecc_spinner_varsta);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.lecc_spn_varsta, R.layout.support_simple_spinner_dropdown_item);
        spn_age.setAdapter(adapter);
        btn_create = findViewById(R.id.lecc_btn_creare);
        tie_name =findViewById(R.id.lecc_tid_numeavatar);
        rg_gender =findViewById(R.id.lecc_rg_fb);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });


        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(),StudentProfileActivity.class);
                startActivity(intent);
            }
        });

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreferences=getSharedPreferences(Constants.CONT_STATUT_PREF,MODE_PRIVATE);
                String statut= sharedPreferences.getString(Constants.USER_PREF, getString(R.string.principala_utilizator_elev_pref_message));
                if (statut.compareTo(getString(R.string.principala_utilizator_profesor_pref_message))==0) {
                   if(isValid()){
                        String name = tie_name.getText().toString();
                        int gender = rg_gender.getCheckedRadioButtonId();
                        int age = Integer.parseInt(spn_age.getSelectedItem().toString());

                        Student student = new Student(name, age, gender);
                        intent.putExtra(Constants.ADD_STUDENT_KEY, student);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                } else if (statut.compareTo(getString(R.string.principala_utilizator_elev_pref_message))==0){
                    if(isValid()){
                        intent = new Intent(getApplicationContext(), HomePageActivity.class);
                        intent.putExtra(Constants.NAME_KEY, tie_name.getText().toString());
                        startActivity(intent);
                    }
                }

            }
        });


    }
    public boolean isValid(){

        if(tie_name.getText().toString().trim().isEmpty() || tie_name.getText().toString().contains(" ")){
            tie_name.setError(getString(R.string.creare_cont_elev_numeavatar_eroare));
            return false;
        }else if(rg_gender.getCheckedRadioButtonId()==-1){
            Toast.makeText(getApplicationContext(),getString(R.string.creare_cont_elev_gen_eroare),Toast.LENGTH_LONG).show();
            return false;
        }else if(spn_age.getSelectedItem().toString().trim().equals(getString(R.string.creare_cont_elev_varsta_alegere))){
            Toast.makeText(getApplicationContext(),R.string.creare_cont_elev_varsta_eroare,Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}