package com.example.docta.myapplication.Activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docta.myapplication.Classes.Database.TestResultDAO;
import com.example.docta.myapplication.Classes.util.Constants;
import com.example.docta.myapplication.R;

import java.util.ArrayList;

public class StudentStatistics extends AppCompatActivity {
        private TextView tv_title;
        private Spinner spn_categ;
        private CheckBox cb_Easy_Test;
        private CheckBox cb_Medium_Test;
        private CheckBox cb_Hard_Test;
        private Button btn_back;
        private TextView tv_category_test;
        private TextView tv_correct_answers;
        private TextView tv_tests_resolved;
        private TextView tv_avarage_efficiency;
        private ProgressBar pb_average;
        private Button btn_calculation;
        private ArrayList<String> list;
        private String[] array_dificulty = new String[3];
        private String user;
        private TestResultDAO testResultDAO;
        private String chosen_Category;
        private SharedPreferences sharedPreferences;
        //final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha_button);
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_statistics);
        initComp();

            btn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            cb_Easy_Test.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(cb_Easy_Test.isChecked()){
                        array_dificulty[0] = Constants.DIFFICULTY_EASY_TEST;
                    }else {
                        array_dificulty[0] = null;
                    }
                }
            });

            cb_Medium_Test.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(cb_Medium_Test.isChecked()){
                        array_dificulty[1] = Constants.DIFFICULTY_MEDIUM_TEST;
                    }else {
                        array_dificulty[1] = null;
                    }
                }
            });
            cb_Hard_Test.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        array_dificulty[2] = Constants.DIFFICULTY_HARD_TEST;
                    }else {
                        array_dificulty[2] = null;
                    }
                }
            });

            btn_calculation.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @SuppressLint({"SetTextI18n" })
                @Override
                public void onClick(View v) {
                    if(!cb_Easy_Test.isChecked() && !cb_Medium_Test.isChecked() && !cb_Hard_Test.isChecked()) {
                        Toast.makeText(getApplicationContext(),getString(R.string.student_statistics_neselectat),Toast.LENGTH_LONG).show();
                        pb_average.setProgress(0);
                        tv_avarage_efficiency.setText("%");
                        tv_correct_answers.setText(getString(R.string.student_statistics_raspCorecte) );
                        tv_tests_resolved.setText(getString(R.string.student_statistics_tRezolvate) );
                    }else {
                        chosen_Category = spn_categ.getSelectedItem().toString().toLowerCase();
                        testResultDAO.open();
                        list = testResultDAO.studentTestResult(user, array_dificulty, chosen_Category);
                        testResultDAO.close();
                        tv_correct_answers.setText(getString(R.string.student_sts_rCorecte) + list.get(0));
                        tv_tests_resolved.setText(getString(R.string.student_sts_tRez) + list.get(1));
                        pb_average.setProgress(Integer.parseInt(list.get(2)), true);
                        tv_avarage_efficiency.setText(list.get(2) + "%");

                    }
                }
            });
        }
    private void initComp(){
        btn_back = findViewById(R.id.std_statistics_btn_back);
        tv_title = findViewById(R.id.std_statistics_tv_title);
        spn_categ = findViewById(R.id.std_statistics_spn_Categ);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.student_statistics_categories,R.layout.support_simple_spinner_dropdown_item);
        spn_categ.setAdapter(adapter);
        cb_Easy_Test = findViewById(R.id.std_Statistics_cb_Easy);
        cb_Medium_Test = findViewById(R.id.std_Statistics_cb_Medium);
        cb_Hard_Test = findViewById(R.id.std_Statistics_cb_Hard);
        tv_category_test = findViewById(R.id.std_statistics_tv_categ);
        tv_correct_answers = findViewById(R.id.std_statistics_tv_Raspunsuri);
        tv_tests_resolved = findViewById(R.id.std_statistics_tv_Teste);
        tv_avarage_efficiency = findViewById(R.id.std_statistics_tv_Medie);
        pb_average = findViewById(R.id.std_Statistics_progressB_medie);
        pb_average.getProgressDrawable().setColorFilter(Color.WHITE,PorterDuff.Mode.SRC_IN);
        btn_calculation = findViewById(R.id.std_Statistics_btn_Calculeaza);
        sharedPreferences = getSharedPreferences(Constants.USERNAME_PREF,MODE_PRIVATE);
        user = sharedPreferences.getString(Constants.USERNAME_KEY,null);
        testResultDAO = new TestResultDAO(this);
    }
}
