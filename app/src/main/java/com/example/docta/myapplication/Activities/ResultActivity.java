package com.example.docta.myapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.docta.myapplication.Classes.Database.StudentDAO;
import com.example.docta.myapplication.Classes.Database.TestResultDAO;
import com.example.docta.myapplication.Classes.util.TestResult;
import com.example.docta.myapplication.R;
import com.example.docta.myapplication.Classes.util.Constants;

import java.util.Date;

public class ResultActivity extends AppCompatActivity {

    private Button btn_back;
    private TextView tv_score;
    private TextView tv_no_correct_answers;
    private SharedPreferences sharedPreferences;
    private StudentDAO studentDAO;
    private TestResultDAO testResultDAO;
    private int no_correct_answers;
    private double score;
    private String username;
    private SharedPreferences sharedPreferencesUser;
    private String  categ;
    private String dificult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        if(savedInstanceState==null){
            String title = getString(R.string.Titlu_Rezultat);
            this.setTitle(title);
        }
        initComponents();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                intent.putExtra(Constants.DOWNLOAD_DONE,true);
                if(categ.equals(Constants.DAILY_QUESTION_KEY )|| categ.equals(Constants.TEST_OF_THE_DAY)){
                    sharedPreferences = getSharedPreferences(Constants.TIME_PREF,MODE_PRIVATE);
                    SharedPreferences.Editor editor2 = sharedPreferences.edit();
                    Date date = new Date(System.currentTimeMillis());
                    Long millis = date.getTime();
                    if(categ.equals(Constants.DAILY_QUESTION_KEY )){
                        editor2.putLong(Constants.TIMEKEY_PREF, millis);
                        editor2.apply();
                    }
                    else{
                        editor2.putLong(Constants.TIMEKEYTEST_PREF, millis);
                        editor2.apply();
                    }

                    testResultDAO.open();
                    testResultDAO.insertTestResult(new TestResult(Constants.CATEG_SPEC ,Constants.CATEG_SPEC ,username ,no_correct_answers ,score));
                    testResultDAO.close();
                } else{
                    testResultDAO.open();
                    testResultDAO.insertTestResult(new TestResult(dificult ,categ ,username ,no_correct_answers ,score));
                    testResultDAO.close();
                }
                startActivity(intent);
                finish();
            }
        });
    }

    private void initComponents() {
        sharedPreferencesUser = getSharedPreferences(Constants.USERNAME_PREF,MODE_PRIVATE);
        username = sharedPreferencesUser.getString(Constants.USERNAME_KEY,getString(R.string.default_user_pref));
        score = getIntent().getDoubleExtra(Constants.SCORE_KEY, 0);
        no_correct_answers = getIntent().getIntExtra(Constants.NO_CORECT_ANSWERS, 0);
        btn_back = findViewById(R.id.result_btn_back);
        tv_score = findViewById(R.id.result_tv_points);
        tv_no_correct_answers = findViewById(R.id.result_tv_correctanswear);
        tv_score.setText(String.valueOf(score));
        tv_no_correct_answers.setText(String.valueOf(no_correct_answers));
        studentDAO = new StudentDAO(this);
        studentDAO.open();
        studentDAO.updateScore(score,username);
        studentDAO.close();
        testResultDAO = new TestResultDAO(this);
        sharedPreferences = getSharedPreferences(Constants.CATEG_PREF,MODE_PRIVATE);
        categ = sharedPreferences.getString(Constants.GET_CATEG,null);
        sharedPreferences = getSharedPreferences(Constants.STUDENT_SETTINGS_PREF,MODE_PRIVATE);
        dificult = sharedPreferences.getString(Constants.DIFFICULTY_PREF,Constants.DIFFICULTY_EASY_TEST);
    }

}
