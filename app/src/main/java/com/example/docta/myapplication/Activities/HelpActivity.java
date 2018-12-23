package com.example.docta.myapplication.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.docta.myapplication.Classes.Adaptor.ExpandableListAdapter;
import com.example.docta.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HelpActivity extends AppCompatActivity {

    private ExpandableListView listaExp;
    private ExpandableListAdapter listAdapter;
    private List<String> questionsList;
    private HashMap<String,List<String>> answersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(savedInstanceState==null){
            String title = getString(R.string.Titlu_Ajutor);
            this.setTitle(title);
        }
        initComponents();
        CreareLista();

        listAdapter = new ExpandableListAdapter(this, questionsList, answersList);

        listaExp.setAdapter(listAdapter);


        listaExp.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(),
                        questionsList.get(groupPosition)
                                + " : "
                                + answersList.get(
                                questionsList.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .cancel();
                return false;
            }
        });

        listaExp.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        questionsList.get(groupPosition),
                        Toast.LENGTH_SHORT).cancel();
            }
        });

    }
    private void initComponents(){
        listaExp = findViewById(R.id.help_elv_questionlist);
    }

    private void CreareLista(){
        questionsList = new ArrayList<String>();
        answersList = new HashMap<String, List<String>>();
        ArrayList<List<String>> answers= new ArrayList<>();

        questionsList.add(getString(R.string.ajutor_tv_Intrebarea1));
        questionsList.add(getString(R.string.ajutor_tv_Intrebarea2));
        questionsList.add(getString(R.string.ajutor_tv_Intrebarea3));
        questionsList.add(getString(R.string.ajutor_tv_Intrebarea4));
        questionsList.add(getString(R.string.ajutor_tv_Intrebarea5));
        questionsList.add(getString(R.string.ajutor_tv_Intrebarea6));
        List<String> answer1 = new ArrayList<String>();
        answer1.add(getString(R.string.ajutor_tv_Raspuns1));
        answers.add(answer1);
        List<String> answer2 = new ArrayList<String>();
        answer2.add(getString(R.string.ajutor_tv_Raspuns2));
        answers.add(answer2);
        List<String> answer3 = new ArrayList<String>();
        answer3.add(getString(R.string.ajutor_tv_Raspuns3_1));
        answer3.add(getString(R.string.ajutor_tv_Raspuns3_2));
        answer3.add(getString(R.string.ajutor_tv_Raspuns3_3));
        answers.add(answer3);
        List<String> answer4 = new ArrayList<String>();
        answer4.add(getString(R.string.ajutor_tv_Raspuns4));
        answers.add(answer4);
        List<String> answer5 = new ArrayList<String>();
        answer5.add(getString(R.string.ajutor_tv_Raspuns5));
        answers.add(answer5);
       List<String> answer6 = new ArrayList<String>();
        answer6.add(getString(R.string.ajutor_tv_Raspuns6));
        answers.add(answer6);
        for(int i=0;i< questionsList.size();i++) {
            answersList.put(questionsList.get(i), answers.get(i));
        }
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
