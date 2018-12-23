package com.example.docta.myapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.docta.myapplication.Classes.Database.StudentDAO;
import com.example.docta.myapplication.Classes.util.Student;
import com.example.docta.myapplication.R;
import com.example.docta.myapplication.Classes.util.Constants;

import java.io.ByteArrayOutputStream;

public class SignUpStudentActivity extends AppCompatActivity {

    private Button btn_back;
    private Button btn_create;
    private Spinner spn_age;
    private TextInputEditText tie_name;
    private RadioGroup rg_gender;
    private ImageView img_boy;
    private ImageView img_girl;
    Intent intent;
    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPreferencesUser;

    private StudentDAO studentDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_student);
        studentDAO = new StudentDAO(this);
        intent=getIntent();

        if(savedInstanceState==null){
            String title = getString(R.string.Titlu_CreareContElev);
            this.setTitle(title);
        }

        btn_back=findViewById(R.id.signup_btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(),LoginStudentActivity.class);
                startActivity(intent);
            }
        });

        spn_age =findViewById(R.id.signup_spinner_age);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.lecc_spn_varsta,R.layout.support_simple_spinner_dropdown_item);
        initComponents();
    }

    private void initComponents() {
        btn_back = findViewById(R.id.signup_btn_back);
        spn_age = findViewById(R.id.signup_spinner_age);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.lecc_spn_varsta, R.layout.support_simple_spinner_dropdown_item);
        spn_age.setAdapter(adapter);
        btn_create = findViewById(R.id.signup_btn_create);
        tie_name =findViewById(R.id.signup_tid_avatarname);
        rg_gender =findViewById(R.id.signup_rg_sex);
        img_boy = findViewById(R.id.signup_img_boy);
        img_girl = findViewById(R.id.signup_img_girl);
        sharedPreferencesUser= getSharedPreferences(Constants.USERNAME_PREF,MODE_PRIVATE);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });


        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(),LoginStudentActivity.class);
                startActivity(intent);
            }
        });

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreferences=getSharedPreferences(Constants.CONT_STATUT_PREF,MODE_PRIVATE);
                String statut= sharedPreferences.getString(Constants.USER_STATUT_PREF, getString(R.string.principala_utilizator_elev_pref_message));
                if (statut.compareTo(getString(R.string.principala_utilizator_profesor_pref_message))==0) {
                   if(isValid()){
                        Student student = initStudent();
                        studentDAO.open();
                        if(!studentDAO.verifyStudentsName(tie_name.getText().toString())) {
                            studentDAO.insertStudentByTeacher(student);
                            studentDAO.close();
                            intent.putExtra(Constants.ADD_STUDENT_KEY, student);
                            setResult(RESULT_OK, intent);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),getString(R.string.signup_student_insert_err_exista),Toast.LENGTH_LONG).show();
                        }
                    }
                } else if (statut.compareTo(getString(R.string.principala_utilizator_elev_pref_message))==0){
                    if(isValid()){
                        Student student = initStudent();
                        studentDAO.open();
                        if(!studentDAO.verifyStudentsName(tie_name.getText().toString())) {
                        studentDAO.insertStudentByStudent(student);
                        studentDAO.close();
                        sharedPreferences=getSharedPreferences(Constants.USERNAME_PREF,MODE_PRIVATE);
                        SharedPreferences.Editor editor= sharedPreferencesUser.edit();
                        editor.putString(Constants.USERNAME_KEY, tie_name.getText().toString());
                        editor.apply();
                        intent = new Intent(getApplicationContext(), HomePageActivity.class);
                        startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(),getString(R.string.signup_student_insert_err_exista),Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });



    }

    private Student initStudent(){

        String name = tie_name.getText().toString();
        int gender = rg_gender.getCheckedRadioButtonId();
        int age = Integer.parseInt(spn_age.getSelectedItem().toString());
        byte[] avatar;
        double score =1000;
        if(gender == R.id.signup_rb_boy ){
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.sboy);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG,100,stream);
            avatar = stream.toByteArray();
        }else{
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.sgirl);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG,100,stream);
            avatar = stream.toByteArray();
        }
        sharedPreferences=getSharedPreferences(Constants.CONT_STATUT_PREF,MODE_PRIVATE);
        String statut= sharedPreferences.getString(Constants.USER_STATUT_PREF, getString(R.string.principala_utilizator_elev_pref_message));
        sharedPreferences = getSharedPreferences(Constants.PASSWORD_PROF_PREF,MODE_PRIVATE);
        String email = sharedPreferences.getString(Constants.EMAIL_PREF,null);
        Student student;
        if(statut.compareTo(getString(R.string.principala_utilizator_profesor_pref_message))==0){
             student = new Student(name,avatar,age,gender,score,email);
        }else{
             student = new Student(name,avatar, age,score, gender);
        }
        return student;
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