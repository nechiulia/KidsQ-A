package com.example.docta.myapplication.Activities;

import android.annotation.SuppressLint;
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

import com.example.docta.myapplication.Classes.Database.TeacherDAO;
import com.example.docta.myapplication.Classes.Network.HttpManager;
import com.example.docta.myapplication.Classes.Teacher;
import com.example.docta.myapplication.Classes.TeacherParser;
import com.example.docta.myapplication.Classes.TeacherSet;
import com.example.docta.myapplication.R;
import com.example.docta.myapplication.util.Constants;

import org.json.JSONException;

import java.util.ArrayList;

public class LoginTeacherActivity extends AppCompatActivity {

    private TextInputEditText tie_email;
    private TextInputEditText tie_password;
    private Button btn_login;
    private Button btn_back;
    Intent intent;
    private SharedPreferences sharedPreferences;
    private TeacherSet setTeachers;
    private ArrayList<Teacher> TeacherAccounts;
    private TeacherDAO teacherDAO;
    private Boolean isChecked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_teacher);
        if(savedInstanceState==null){
            String title = getString(R.string.Titlu_AutentificareProfesor);
            this.setTitle(title);
        }
        initComponents();
        isChecked = sharedPreferences.getBoolean(getString(R.string.LOGIN_SHARED_JSONKEY),false);
        if(!isChecked) {
            @SuppressLint("StaticFieldLeak") HttpManager manager = new HttpManager() {
                @Override
                protected void onPostExecute(String s) {
                    try {
                        setTeachers = TeacherParser.fromJson(s);
                        TeacherAccounts = setTeachers.getTeacherList();
                        teacherDAO.open();
                        teacherDAO.insertTeacherAccountsInDatabase(TeacherAccounts);
                        teacherDAO.close();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // Toast.makeText(getApplicationContext(),setTeachers.toString(),Toast.LENGTH_LONG).show();
                }
            };
            manager.execute(Constants.URL_JSON_ACCOUNTS);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(getString(R.string.LOGIN_SHARED_JSONKEY),true);
            editor.commit();
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()) {
                    String email = tie_email.getText().toString();
                    String password = tie_password.getText().toString();
                    teacherDAO.open();
                    if(teacherDAO.LoginTeacherFromDatabase(email,password)) {
                        intent = new Intent(getApplicationContext(), ListStudentsActivity.class);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Constants.PASSWORD_PREF, tie_password.getText().toString());
                        editor.putString(Constants.EMAIL_PREF, tie_email.getText().toString());
                        boolean result = editor.commit();
                        teacherDAO.close();
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"Ati gresit parola sau email-ul",Toast.LENGTH_LONG).show();
                    }
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
        tie_email = findViewById(R.id.loginteacher_tid_email);
        tie_password = findViewById(R.id.loginteacher_tid_password);
        btn_login=findViewById(R.id.loginteacher_btn_login);
        btn_back=findViewById(R.id.logintacher_btn_back);
        sharedPreferences=getSharedPreferences(Constants.PASSWORD_PROF_PREF, MODE_PRIVATE);
        teacherDAO = new TeacherDAO(getApplicationContext());

    }

    public boolean isValid(){
        if(TextUtils.isEmpty(tie_email.getText()) || tie_email.getText().toString().trim().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(tie_email.getText().toString()).matches()){
            tie_email.setError(getText(R.string.autentificare_profesor_email_eroare));
            Toast.makeText(getApplicationContext(),R.string.autentificare_profesor_email_eroare,Toast.LENGTH_LONG).show();
            return false;
        }else if(tie_password.getText() == null || tie_password.getText().toString().trim().isEmpty() || tie_password.getText().toString().contains(" ")){
            tie_password.setError(getText(R.string.autentificare_profesor_parola_eroare));
            Toast.makeText(getApplicationContext(),R.string.autentificare_profesor_parola_eroare,Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
