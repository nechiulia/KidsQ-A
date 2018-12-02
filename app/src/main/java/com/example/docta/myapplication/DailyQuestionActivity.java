package com.example.docta.myapplication;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.CountDownTimer;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docta.myapplication.Classes.Question;
import com.example.docta.myapplication.util.Constants;
import com.squareup.picasso.Picasso;

public class DailyQuestionActivity extends AppCompatActivity {

    private Button btn_result;
    private TextView countdown_text;
    private Button btn_countdown;
    private TextView tv_daily_question;
    private CountDownTimer countDownTimer;
    private long timeLeftInMiliseconds=600000; //10 min
    private boolean timerunning;
    private RadioButton rb_answer1;
    private RadioButton rb_answer2;
    private RadioButton rb_answer3;
    private RadioGroup rg_options;
    private Question dailyQuestion;
    private ImageView iv_image;

    private double score;
    private int noCorectAnswers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_question);
        if(savedInstanceState==null){
            String title = getString(R.string.Titlu_IntrebareaZilei);
            this.setTitle(title);
        }
        initComponents();


    }

    private void initComponents(){
        score = 0;
        noCorectAnswers = 0;
        btn_result =findViewById(R.id.intrebarea_zilei_btn_confirm);
        countdown_text=findViewById(R.id.intrebarea_zilei_tv_countdown);
        btn_countdown=findViewById(R.id.intrebarea_zilei_btn_incepe_test);
        btn_result.setVisibility(View.INVISIBLE);
        rg_options = findViewById(R.id.intrebarea_zilei_rg_raspunsuri);
        rb_answer1 = findViewById(R.id.intrebarea_zilei_rb_raspuns1);
        rb_answer2 = findViewById(R.id.intrebarea_zilei_rb_raspuns2);
        rb_answer3 = findViewById(R.id.intrebarea_zilei_rb_raspuns3);
        tv_daily_question =findViewById(R.id.intrebarea_zilei_tv_intrebare);
        iv_image =findViewById(R.id.intrebarea_zilei_iv_original);
        dailyQuestion = (Question)getIntent().getSerializableExtra(Constants.DAILY_QUESTION_KEY);

        btn_result.setOnClickListener(clickResult());
        iv_image.setOnClickListener(openShowImage());
        btn_countdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_result.setVisibility(View.VISIBLE);
                startStop();
            }
        });
        btn_countdown.performClick();
        initControllersText();
    }
    private View.OnClickListener clickResult(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValid()) {
                    int rb_correct;
                    for (int i = 0; i < 3; i++) {
                        if (dailyQuestion.getAnswers().get(i).isCorrect()) {
                            rb_correct = rg_options.getChildAt(i).getId();
                            if (rb_correct == rg_options.getCheckedRadioButtonId()) {
                                Toast toastCorrect = Toast.makeText(getApplicationContext(), getString(R.string.toast_raspuns_corect), Toast.LENGTH_SHORT);
                                View view1 = toastCorrect.getView();
                                int colorGreen = ResourcesCompat.getColor(getResources(), R.color.LimeGreen, null);
                                view1.getBackground().setColorFilter(colorGreen, PorterDuff.Mode.SRC_IN);
                                toastCorrect.show();
                                score += dailyQuestion.getSettings().getScore();
                                noCorectAnswers++;
                            } else {
                                Toast toastWrong = Toast.makeText(getApplicationContext(), getString(R.string.toast_raspuns_gresit), Toast.LENGTH_SHORT);
                                View view2 = toastWrong.getView();
                                int colorRed = ResourcesCompat.getColor(getResources(), R.color.fireBrick, null);
                                view2.getBackground().setColorFilter(colorRed, PorterDuff.Mode.SRC_IN);
                                toastWrong.show();
                            }
                        }
                    }
                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra(Constants.SCORE_KEY, score);
                    intent.putExtra(Constants.NO_CORECT_ANSWERS, noCorectAnswers);
                    startActivity(intent);
                }
            }
        };
    }
    private View.OnClickListener openShowImage(){
     return new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(getApplicationContext(),ShowImageActivity.class);
             intent.putExtra(Constants.DIFFERENT_IMAGE_KEY, dailyQuestion.getSettings().getImage());
             startActivity(intent);
         }
     } ;
    }
    private void initControllersText(){
        tv_daily_question.setText(dailyQuestion.getQuestionText());
        rb_answer1.setText(dailyQuestion.getAnswers().get(0).getAnswerText());
        rb_answer2.setText(dailyQuestion.getAnswers().get(1).getAnswerText());
        rb_answer3.setText(dailyQuestion.getAnswers().get(2).getAnswerText());
        loadImageFromJson(dailyQuestion.getSettings().getImage());
        rg_options.clearCheck();
    }
    private void loadImageFromJson(String urlJson){
        Picasso.with(getApplicationContext()).load(urlJson).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                .into(iv_image, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }
    private void startStop(){
        if(timerunning){
            //stopTimer();
        }else{
            startTimer();
        }

    }

    public boolean isValid(){
        if(rg_options.getCheckedRadioButtonId()==-1){
            rb_answer3.setError(getString(R.string.intrebarea_zilei_raspuns_eroare));
            Toast.makeText(getApplicationContext(),getString(R.string.intrebarea_zilei_raspuns_eroare),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void startTimer(){
        countDownTimer=new CountDownTimer(timeLeftInMiliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btn_countdown.setVisibility(View.INVISIBLE);
                timeLeftInMiliseconds=millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();
        timerunning=true;
    }



    private void updateTimer(){
        int minutes=(int) timeLeftInMiliseconds/60000;
        int seconds=(int) timeLeftInMiliseconds%60000/1000;

        String timeLeftText;

        timeLeftText=""+minutes;
        timeLeftText+=":";
        if(seconds<10) timeLeftText+="0";
        timeLeftText+=seconds;

        countdown_text.setText(timeLeftText);
    }




}
