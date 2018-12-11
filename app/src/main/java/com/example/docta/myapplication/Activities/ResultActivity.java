package com.example.docta.myapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.docta.myapplication.Classes.Database.StudentDAO;
import com.example.docta.myapplication.R;
import com.example.docta.myapplication.Classes.util.Constants;

public class ResultActivity extends AppCompatActivity {

    private Button btn_back;
    private TextView tv_score;
    private TextView tv_no_correct_answers;
    private SharedPreferences sharedPreferences;
    private StudentDAO studentDAO;

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
                startActivity(intent);
                finish();
            }
        });
    }

    private void initComponents() {
        sharedPreferences = getSharedPreferences(Constants.USERNAME_PREF,MODE_PRIVATE);
        String username = sharedPreferences.getString(Constants.USERNAME_KEY,null);
        double score = getIntent().getDoubleExtra(Constants.SCORE_KEY, 0);
        int no_correct_answers = getIntent().getIntExtra(Constants.NO_CORECT_ANSWERS, 0);
        btn_back = findViewById(R.id.result_btn_back);
        tv_score = findViewById(R.id.result_tv_points);
        tv_no_correct_answers = findViewById(R.id.result_tv_correctanswear);
        tv_score.setText(String.valueOf(score));
        tv_no_correct_answers.setText(String.valueOf(no_correct_answers));
        studentDAO = new StudentDAO(this);
        studentDAO.open();
        studentDAO.updateScore(score,username);
        studentDAO.close();

    }
}
