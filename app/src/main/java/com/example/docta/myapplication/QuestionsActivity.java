package com.example.docta.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.TextView;

import com.example.docta.myapplication.Classes.Question;
import com.example.docta.myapplication.Classes.QuestionsSet;
import com.example.docta.myapplication.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionsActivity extends AppCompatActivity {
    private TextView tv_no_question;
    private int current_no;
    private RadioGroup rg_answers;

    private RadioButton rb_answer1;
    private RadioButton rb_answer2;
    private RadioButton rb_answer3;
    private Button btn_confirm;
    private ImageView iv_image;
    private TextView tv_question;

    private double score =0;
    private int no_correct_answers =0;
    String daily_test;

    private QuestionsSet set;
    private ArrayList<Question> questionsList = new ArrayList<>();

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        sharedPreferences = getSharedPreferences(Constants.STUDENT_SETTINGS_PREF,MODE_PRIVATE);

        questionsList = (ArrayList<Question>) getIntent().getSerializableExtra(Constants.QUESTIONS_LIST_KEY);
        Collections.shuffle(questionsList);
        initComponents();
        initFirstQuestion();
    }



    private View.OnClickListener confirmAnswer() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()) {
                    int rb_correct;
                    for (int i = 0; i < 3; i++) {
                        if (questionsList.get(current_no - 1).getAnswers().get(i).isCorrect()) {
                            rb_correct = rg_answers.getChildAt(i).getId();
                            if (rb_correct == rg_answers.getCheckedRadioButtonId()) {
                                Toast toastCorrect = Toast.makeText(getApplicationContext(), getString(R.string.toast_raspuns_corect), Toast.LENGTH_SHORT);
                                View view1 = toastCorrect.getView();
                                int colorGreen = ResourcesCompat.getColor(getResources(), R.color.LimeGreen, null);
                                view1.getBackground().setColorFilter(colorGreen, PorterDuff.Mode.SRC_IN);
                                toastCorrect.show();
                                score += questionsList.get(current_no - 1).getSettings().getScore();
                                no_correct_answers++;
                            } else {
                                Toast toastWrong = Toast.makeText(getApplicationContext(), getString(R.string.toast_raspuns_gresit), Toast.LENGTH_SHORT);
                                View view2 = toastWrong.getView();
                                int colorRed = ResourcesCompat.getColor(getResources(), R.color.fireBrick, null);
                                view2.getBackground().setColorFilter(colorRed, PorterDuff.Mode.SRC_IN);
                                toastWrong.show();
                            }

                        }
                    }

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, 2000);

                    current_no++;
                    if (daily_test.equals(getString(R.string.Valoare_TestulZilei))) {
                        if (current_no == 11) {
                            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                            intent.putExtra(Constants.SCORE_KEY, score);
                            intent.putExtra(Constants.NO_CORECT_ANSWERS, no_correct_answers);
                            startActivity(intent);
                        } else {
                            tv_no_question.setText(current_no + getString(R.string.intrebari_tv_nr_intrebariTestulZilei));
                            initControllersText(current_no);
                        }
                    }else{
                        if (current_no == 6) {
                            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                            intent.putExtra(Constants.SCORE_KEY, score);
                            intent.putExtra(Constants.NO_CORECT_ANSWERS, no_correct_answers);
                            startActivity(intent);
                        } else {
                          initControllersText(current_no);
                            tv_no_question.setText(current_no + getString(R.string.intrebari_tv_nr_intrebari));
                        }
                    }

                }
            }
        };
    }
    private void initControllersText(int nrCurent){
        tv_question.setText(questionsList.get(nrCurent - 1).getQuestionText());
        rb_answer1.setText(questionsList.get(nrCurent - 1).getAnswers().get(0).getAnswerText());
        rb_answer2.setText(questionsList.get(nrCurent - 1).getAnswers().get(1).getAnswerText());
        rb_answer3.setText(questionsList.get(nrCurent - 1).getAnswers().get(2).getAnswerText());
        loadImageFromJson(questionsList.get(nrCurent - 1).getSettings().getImage());
        rg_answers.clearCheck();
    }
    public boolean isValid(){
        if(rg_answers.getCheckedRadioButtonId()==-1){
            rb_answer3.setError(getString(R.string.intrebarea_zilei_raspuns_eroare));
            Toast.makeText(getApplicationContext(),getString(R.string.intrebarea_zilei_raspuns_eroare),Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            rb_answer3.setError(null);
            return true;
        }
    }
    private void initComponents(){
        daily_test = getIntent().getStringExtra(Constants.DAILY_TEST) !=null ? getIntent().getStringExtra(Constants.DAILY_TEST):getString(R.string.intrebari_testul_zilei_diferit);
        tv_no_question =findViewById(R.id.intrebari_tv_nr_intrebare);
        rg_answers = findViewById(R.id.intrebari_rg_raspunsuri);
        rb_answer1 = findViewById(R.id.intrebari_rb_raspuns1);
        rb_answer2 = findViewById(R.id.intrebari_rb_raspuns2);
        rb_answer3 = findViewById(R.id.intrebari_rb_raspuns3);
        btn_confirm = findViewById(R.id.intrebari_btn_confirm);
        iv_image = findViewById(R.id.intrebari_iv_imagine);
        tv_question = findViewById(R.id.intrebari_tv_intrebare);
        btn_confirm.setOnClickListener(confirmAnswer());
        current_no =1;
        if(daily_test.equals(getString(R.string.Valoare_TestulZilei))) {
            tv_no_question.setText(current_no + getString(R.string.intrebari_tv_nr_intrebariTestulZilei));
        }else{
            tv_no_question.setText(current_no + getString(R.string.intrebari_tv_nr_intrebari));
        }
    }


    private void initFirstQuestion(){
        if(daily_test.equals(getString(R.string.Valoare_TestulZilei))) {
            String title =  getString(R.string.Valoare_TestulZilei);
            this.setTitle(title);
        }else{
            String title = getString(R.string.Titlu_TestIntrebari) + questionsList.get(0).getSettings().getCategory();
            this.setTitle(title);
        }
        initControllersText(1);
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
}



