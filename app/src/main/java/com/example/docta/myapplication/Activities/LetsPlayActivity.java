package com.example.docta.myapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.docta.myapplication.Classes.util.QuestionsSet;
import com.example.docta.myapplication.Classes.util.Question;
import com.example.docta.myapplication.R;
import com.example.docta.myapplication.Classes.util.Constants;

import java.util.ArrayList;

public class LetsPlayActivity extends AppCompatActivity {
    private Button btn_math;
    private Button btn_life;
    private Button btn_animals;
    private Button btn_letters;
    private Button btn_fruits;
    private static final String URL = Constants.URL_JSON_TESTS;


    private QuestionsSet questionsSet;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private ArrayList<Question> difficultyQuestionsList;
    private ArrayList<Question> testQuestionsList;
    String difficulty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lets_play);
        if(savedInstanceState==null){
            String title = getString(R.string.Titlu_SaNeJucam);
            this.setTitle(title);
        }
        questionsSet = (QuestionsSet) getIntent().getSerializableExtra(Constants.QUESTIONS_SET_KEY);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = getSharedPreferences(Constants.STUDENT_SETTINGS_PREF,MODE_PRIVATE);
        initComponents();
    }
    private void initComponents(){

        btn_math = findViewById(R.id.letsplay_btn_math);
        btn_letters =findViewById(R.id.letsplay_btn_letters);
        btn_fruits =findViewById(R.id.letsplay_btn_fruitsandvegetables);
        btn_life =findViewById(R.id.letsplay_btn_everydaylife);
        btn_animals =findViewById(R.id.letsplay_btn_animals);
        testQuestionsList =new ArrayList<>();

        btn_math.setOnClickListener(openMathTests());
        btn_fruits.setOnClickListener(openFruitsTest());
        btn_letters.setOnClickListener(openLettersTest());
        btn_animals.setOnClickListener(openAnimalsTest());
        btn_life.setOnClickListener(openLifeTests());
        difficulty = sharedPreferences.getString(Constants.DIFFICULTY_PREF,Constants.DIFFICULTY_EASY_TEST);

        initDificultyList();
    }
    private void initDificultyList(){
        difficultyQuestionsList =new ArrayList<>();
        if(questionsSet !=null){
            if(difficulty.equals(Constants.DIFFICULTY_EASY_TEST)){
                difficultyQuestionsList = questionsSet.getEasy();
            }
            else if(difficulty.equals(Constants.DIFFICULTY_MEDIUM_TEST)){
                difficultyQuestionsList = questionsSet.getMedium();
            }
            else if(difficulty.equals(Constants.DIFFICULTY_HARD_TEST)){
                difficultyQuestionsList = questionsSet.getHard();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), getString(R.string.jucam_toast_nu_exista_intrebari_dificultate), Toast.LENGTH_LONG).show();
        }
    }
    private View.OnClickListener openAnimalsTest(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(difficultyQuestionsList.size()!=0) {
                    for (int i = 0; i < difficultyQuestionsList.size(); i++) {
                        if (difficultyQuestionsList.get(i).getSettings().getCategory().equals(Constants.CATEGORY_ANIMALS)) {
                            testQuestionsList.add(difficultyQuestionsList.get(i));
                        }
                    }
                    sharedPreferences = getSharedPreferences(Constants.CATEG_PREF,MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString(Constants.GET_CATEG,Constants.CATEGORY_ANIMALS);
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), QuestionsActivity.class);
                    intent.putExtra(Constants.QUESTIONS_LIST_KEY, testQuestionsList);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),getString(R.string.jucam_toast_nu_exista_intrebari_dificultate), Toast.LENGTH_LONG).show();
                }

        }

        };
    }
    private View.OnClickListener openLettersTest(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(difficultyQuestionsList.size()!=0) {
                    for (int i = 0; i < difficultyQuestionsList.size(); i++) {
                        if (difficultyQuestionsList.get(i).getSettings().getCategory().equals(Constants.CATEGORY_LETTERS)) {
                            testQuestionsList.add(difficultyQuestionsList.get(i));
                        }
                    }
                    sharedPreferences = getSharedPreferences(Constants.CATEG_PREF,MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString(Constants.GET_CATEG,Constants.CATEGORY_LETTERS);
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), QuestionsActivity.class);
                    intent.putExtra(Constants.QUESTIONS_LIST_KEY, testQuestionsList);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),getString(R.string.jucam_toast_nu_exista_intrebari_dificultate), Toast.LENGTH_LONG).show();
                }
            }
        };

    }
    private View.OnClickListener openFruitsTest(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(difficultyQuestionsList.size()!=0) {
                    for (int i = 0; i < difficultyQuestionsList.size(); i++) {
                        if (difficultyQuestionsList.get(i).getSettings().getCategory().equals(Constants.CATEGORY_FRUITS)) {
                            testQuestionsList.add(difficultyQuestionsList.get(i));
                        }
                    }
                    sharedPreferences = getSharedPreferences(Constants.CATEG_PREF,MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString(Constants.GET_CATEG,Constants.CATEGORY_FRUITS);
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), QuestionsActivity.class);
                    intent.putExtra(Constants.QUESTIONS_LIST_KEY, testQuestionsList);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),getString(R.string.jucam_toast_nu_exista_intrebari_dificultate), Toast.LENGTH_LONG).show();
                }
            }
        };
    }
    private View.OnClickListener openLifeTests(){

        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(difficultyQuestionsList.size()!=0) {
                    for (int i = 0; i < difficultyQuestionsList.size(); i++) {
                        if (difficultyQuestionsList.get(i).getSettings().getCategory().equals(Constants.CATEGORY_LIFE)) {
                            testQuestionsList.add(difficultyQuestionsList.get(i));
                        }
                    }
                    sharedPreferences = getSharedPreferences(Constants.CATEG_PREF,MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString(Constants.GET_CATEG,Constants.CATEGORY_LIFE);
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), QuestionsActivity.class);
                    intent.putExtra(Constants.QUESTIONS_LIST_KEY, testQuestionsList);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),getString(R.string.jucam_toast_nu_exista_intrebari_dificultate), Toast.LENGTH_LONG).show();
                }
            }
        };
    }
    private View.OnClickListener openMathTests(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(difficultyQuestionsList.size()!=0) {
                    for (int i = 0; i < difficultyQuestionsList.size(); i++) {
                        if (difficultyQuestionsList.get(i).getSettings().getCategory().equals(Constants.CATEGORY_MATH)) {
                            testQuestionsList.add(difficultyQuestionsList.get(i));
                        }
                    }
                    sharedPreferences = getSharedPreferences(Constants.CATEG_PREF,MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString(Constants.GET_CATEG,Constants.CATEGORY_MATH);
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), QuestionsActivity.class);
                    intent.putExtra(Constants.QUESTIONS_LIST_KEY, testQuestionsList);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),getString(R.string.jucam_toast_nu_exista_intrebari_dificultate), Toast.LENGTH_LONG).show();
                }
            }
        };
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id= item.getItemId();
        if(id==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
