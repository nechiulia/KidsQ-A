package com.example.docta.myapplication.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.docta.myapplication.Classes.Adaptor.StudentAdapter;
import com.example.docta.myapplication.Classes.Database.StudentDAO;
import com.example.docta.myapplication.Classes.Database.TeacherDAO;
import com.example.docta.myapplication.R;
import com.example.docta.myapplication.Classes.util.Constants;

public class SettingsActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    private TextInputEditText tie_email;
    private TextInputEditText tie_old_pass;
    private TextInputEditText tie_new_pass;
    private TextInputEditText tie_confirm_pass;

    private Button btn_save;
    private ImageButton imgBtn_delete_account;
    private ImageButton imgBtn_log_out;
    private SharedPreferences sharedPreferences;
    Intent intent = getIntent();
    private TeacherDAO teacherDAO;
    private StudentDAO studentDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        if(savedInstanceState==null){
            String title = getString(R.string.Titlu_SetariProfesor);
            this.setTitle(title);
        }

        initComponent();
        Menu menu = bottomNavigationView.getMenu();
        MenuItem item = menu.getItem(1);
        item.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.menu_resultest:
                        startActivity(new Intent(getApplicationContext(),ResultTestVisualizationActivity.class));
                        finish();
                        break;
                    case R.id.menu_lista:
                        startActivity(new Intent(getApplicationContext(),ListStudentsActivity.class));
                        finish();
                        break;
                }
                return false;
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()) {
                    String newPass = tie_new_pass.getText().toString();
                    String email = sharedPreferences.getString(Constants.EMAIL_PREF,null);
                    teacherDAO.open();
                    if(teacherDAO.UpdateTeacherAccount(newPass,email)!=-1) {
                        teacherDAO.close();
                        Toast.makeText(getApplicationContext(), getString(R.string.setari_prof_toast_succes), Toast.LENGTH_LONG).show();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Constants.PASSWORD_PREF, tie_new_pass.getText().toString());
                        editor.apply();
                    }
                }
            }
        });

        imgBtn_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),LoginPageActivity.class));
                    finish();
            }
        });

        imgBtn_delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle(R.string.settings_title_stergereCont)
                        .setMessage(getString(R.string.settings_teacher_alertdialog_message))
                        .setPositiveButton(getString(R.string.positive_button), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String email = sharedPreferences.getString(Constants.EMAIL_PREF,null);
                                teacherDAO.open();
                                if(teacherDAO.DeleteTeacherAccount(email)>=0){
                                    teacherDAO.close();
                                    Toast.makeText(getApplicationContext(),getString(R.string.settings_teacher_alertdialog_delete_successful),Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(),LoginPageActivity.class));
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(),getString(R.string.settings_teacher_alertdialog_delete_failed),Toast.LENGTH_LONG).show();
                                }
                            }
                        }).setNegativeButton(getString(R.string.negative_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }
        });
    }

    private void initComponent(){
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        btn_save =findViewById(R.id.settings_btn_save);
        imgBtn_delete_account = findViewById(R.id.settings_btn_delete_account);
        imgBtn_log_out = findViewById(R.id.settings_btn_logout_acc);
        tie_email = findViewById(R.id.settings_tid_email);
        tie_old_pass = findViewById(R.id.settings_tid_passwordO);
        tie_new_pass = findViewById(R.id.settings_tid_passwordN);
        tie_confirm_pass = findViewById(R.id.settings_tid_passwordC);
        sharedPreferences=getSharedPreferences(Constants.PASSWORD_PROF_PREF, MODE_PRIVATE);
        teacherDAO = new TeacherDAO(getApplicationContext());
        studentDAO = new StudentDAO(this);
    }

    private boolean isValid(){
        String parolaVeche= sharedPreferences.getString(Constants.PASSWORD_PREF, null);
        String email = sharedPreferences.getString(Constants.EMAIL_PREF,null);
        if(tie_email.getText().toString().compareTo(email)!=0 || tie_old_pass.getText().toString().compareTo(parolaVeche)!=0) {
            Toast.makeText(getApplicationContext(), getString(R.string.setari_prof_toast_parolanecoresp),Toast.LENGTH_LONG).show();
            tie_old_pass.setText("");
            return false;}
            if (TextUtils.isEmpty(tie_email.getText()) || !Patterns.EMAIL_ADDRESS.matcher(tie_email.getText().toString()).matches()
                    || tie_email.getText().toString().trim().isEmpty()) {
                tie_email.setError(getText(R.string.autentificare_profesor_email_eroare));
                return false;
            } else if ( tie_old_pass.getText().toString().trim().isEmpty() || tie_old_pass.getText().toString().contains(" ")) {
                tie_old_pass.setError(getString(R.string.setari_parolaVeche_eroare));
                return false;
            } else if (tie_new_pass.getText() == null || tie_new_pass.getText().toString().trim().isEmpty() || tie_new_pass.getText().toString().contains(" ")) {
                tie_new_pass.setError(getString(R.string.autentificare_profesor_parola_eroare));
                return false;
            } else if (tie_confirm_pass.getText().toString().compareTo(tie_new_pass.getText().toString()) != 0) {
                tie_confirm_pass.setError(getString(R.string.setari_parolaConfirm_eroare));
                return false;
            }
        return true;
    }
}
