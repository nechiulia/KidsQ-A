package com.example.docta.myapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docta.myapplication.Classes.Database.StudentDAO;
import com.example.docta.myapplication.Classes.util.Constants;
import com.example.docta.myapplication.R;

public class UpdateStudentNameActivity extends AppCompatActivity {

    private EditText tid_avatar_name;
    private Button btn_change;
    Intent intent;
    String name;
    private StudentDAO studentDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student_name);
        intent = getIntent();
        name=getIntent().getStringExtra(Constants.CHANGE_NAME_KEY);
        init();


    }

    private void init() {
        btn_change=findViewById(R.id.update_student_name_btn_save);
        tid_avatar_name=findViewById(R.id.update_student_name_tid_name);

        btn_change.setOnClickListener(saveChanges());

        studentDAO=new StudentDAO(this);


    }

    private View.OnClickListener saveChanges() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = tid_avatar_name.getText().toString();
                if(tid_avatar_name.getText().toString().trim().isEmpty()){
                    Toast.makeText(getApplicationContext(),getString(R.string.update_student_name_err_necompletat),Toast.LENGTH_LONG).show();
                }
                studentDAO.open();
                if(studentDAO.verifyStudentsName(username)==false) {
                    studentDAO.close();
                    intent = getIntent();
                    intent.putExtra(Constants.SET_STUDENT_NAME_KEY, username);
                    setResult(RESULT_OK, intent);
                    finish();


                }else{
                    Toast.makeText(getApplicationContext(),getString(R.string.update_student_name_err_exista),Toast.LENGTH_LONG).show();
                }
            }
        };
    }

}
