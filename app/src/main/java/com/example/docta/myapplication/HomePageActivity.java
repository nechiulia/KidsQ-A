package com.example.docta.myapplication;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docta.myapplication.Classes.Question;
import com.example.docta.myapplication.Classes.Network.HttpManager;
import com.example.docta.myapplication.Classes.QuestionsSet;
import com.example.docta.myapplication.Classes.QuestionsSetParser;
import com.example.docta.myapplication.util.Constants;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;


public class HomePageActivity extends AppCompatActivity {

    private Button btn_play;
    private Button btn_learn;
    private Button btn_ranking;
    private Button btn_settings;
    private Button btn_my_avatars;
    private Button btn_daily_test;
    private Button btn_daily_question;
    private ImageButton img_btn_feedback;
    private TextView tv_avatar_name;
    private Button btn_back_teacher;
    private Button btn_tasks;
    private SharedPreferences sharedPreferences;
    private ImageButton img_btn_help;
    private static final String URL = Constants.URL_JSON_TESTS;
    private QuestionsSet questionsSet;
    private ArrayList<Question> dailyTestQuestions;
    private ArrayList<Question> dailyQuestion;
    private ProgressDialog progressDialog;
    Intent intent;
    private Boolean isChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        isChecked = getIntent().getBooleanExtra(Constants.DOWNLOAD_DONE,false);
        if(savedInstanceState==null){
            String title = getString(R.string.Titlu_PaginaPrincipalaJoc);
            this.setTitle(title);
        }
                @SuppressLint("StaticFieldLeak") HttpManager manager = new HttpManager() {
                    @Override
                    protected void onPostExecute(String s) {
                        try {
                            questionsSet = QuestionsSetParser.fromJson(s);
                            if(!isChecked) {
                                getInfos();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), getString(R.string.jucam_parsare_eroare), Toast.LENGTH_LONG).show();
                        }
                    }

                };
        manager.execute(URL);
        initComponents();
    }

    private void getInfos(){
            progressDialog = new ProgressDialog(HomePageActivity.this);
            progressDialog.setMax(100);
            progressDialog.setMessage(getString(R.string.ppj_progress_incarcare));
            progressDialog.setTitle(getString(R.string.ppj_progress_descarcare));
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
            progressDialog.getButton(ProgressDialog.BUTTON_NEUTRAL).setVisibility(View.INVISIBLE);

            @SuppressLint("StaticFieldLeak") Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    progressDialog.incrementProgressBy(1);
                }
            };

            new Thread(() -> {
                while (progressDialog.getProgress() <= progressDialog.getMax()) {
                    try {
                        Thread.sleep(30);
                        handler.sendMessage(handler.obtainMessage());
                        if (progressDialog.getProgress() == progressDialog.getMax()) {
                            progressDialog.dismiss();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

    }

    private void initComponents(){
        btn_play =findViewById(R.id.ppj_btn_jucam);
        btn_learn =findViewById(R.id.ppj_btn_invatam);
        btn_my_avatars =findViewById(R.id.ppj_btn_avatareleMele);
        btn_ranking =findViewById(R.id.ppj_btn_clasament);
        btn_daily_question =findViewById(R.id.ppj_btn_intrebareaZilei);
        btn_daily_test =findViewById(R.id.ppj_btn_testulZilei);
        btn_settings =findViewById(R.id.ppj_btn_setari);
        img_btn_feedback =findViewById(R.id.ppj_imgBtn_star);
        tv_avatar_name =findViewById(R.id.ppj_tv_bunVenit);
        btn_back_teacher =findViewById(R.id.ppj_btn_inapoi_la_profesor);
        img_btn_help =findViewById(R.id.ppj_imgBtn_intrebare);
        btn_tasks =findViewById(R.id.ppj_btn_sarcini);

        sharedPreferences= getSharedPreferences(Constants.CONT_STATUT_PREF, MODE_PRIVATE);
        String utilizator= sharedPreferences.getString(Constants.USER_PREF, getString(R.string.ppj_utilizator_default_pref));

        if(utilizator.compareTo(getString(R.string.principala_utilizator_profesor_pref_message))==0){
            btn_back_teacher.setVisibility(View.VISIBLE);
        }
        else {
            btn_back_teacher.setVisibility(View.INVISIBLE);
        }
        String nume= getIntent().getStringExtra(Constants.NAME_KEY);
        if (nume==null){
            tv_avatar_name.setText(getString(R.string.ppj_tv_bine_ai_venit_null));
        }
        else {
        tv_avatar_name.setText(getString(R.string.ppj_tv_bineAiVenit)+ nume);
        }
        btn_learn.setOnClickListener(startToLearn());
        btn_play.setOnClickListener(startToPlay());
        btn_daily_question.setOnClickListener(openDailyQuestion());
        btn_daily_test.setOnClickListener(openDailyTest());
        btn_my_avatars.setOnClickListener(openAvatars());
        btn_ranking.setOnClickListener(openRanking());
        btn_settings.setOnClickListener(openSettings());
        img_btn_feedback.setOnClickListener(openFeedback());
        btn_back_teacher.setOnClickListener(backToTeacher());
        img_btn_help.setOnClickListener(openHelp());
        btn_tasks.setOnClickListener(openTasks());
   }

   private View.OnClickListener openTasks()
   {
       return new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),TasksActivity.class);
            startActivity(intent);
           }
       };
   }
   private View.OnClickListener backToTeacher(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            finish();
        }};
   }
   private View.OnClickListener startToLearn(){
       return new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(getApplicationContext(), LetsLearnActivity.class);
               startActivity(intent);
           }
       };
    }
    private View.OnClickListener startToPlay(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LetsPlayActivity.class);
                intent.putExtra(Constants.QUESTIONS_SET_KEY, questionsSet);
                startActivity(intent);
            }
        };
    }
    private View.OnClickListener openDailyQuestion(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dailyQuestion = questionsSet.getDailyQuestion();
                Collections.shuffle(dailyQuestion);
                Intent intent = new Intent(getApplicationContext(), DailyQuestionActivity.class);
                intent.putExtra(Constants.DAILY_QUESTION_KEY, dailyQuestion.get(0));
                startActivity(intent);
            }
        };
    }
    private View.OnClickListener openDailyTest(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailyTestQuestions = questionsSet.getHard();
                Intent intent = new Intent(getApplicationContext(), QuestionsActivity.class);
                intent.putExtra(Constants.DAILY_TEST,getString(R.string.Valoare_TestulZilei));
                intent.putExtra(Constants.QUESTIONS_LIST_KEY, dailyTestQuestions);
                startActivity(intent);
                finish();
            }

        };

    }
    private View.OnClickListener openAvatars(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyAvatarsActivity.class);
                startActivity(intent);
            }
        };

    }

    private View.OnClickListener openRanking(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RankingActivity.class);
                startActivity(intent);
            }
        };

    }

    private View.OnClickListener openSettings(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StudentSettingsActivity.class);
                startActivity(intent);
            }

        };

    }

    private View.OnClickListener openFeedback(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FeedbackActivity.class);
                startActivity(intent);
            }

        };

    }
    private View.OnClickListener openHelp(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(intent);
            }
        };
    }



}