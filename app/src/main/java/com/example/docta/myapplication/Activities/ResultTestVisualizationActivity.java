package com.example.docta.myapplication.Activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.docta.myapplication.Classes.Adaptor.ExpandableListTestResultAdapter;
import com.example.docta.myapplication.Classes.Database.StudentDAO;
import com.example.docta.myapplication.Classes.Database.TestResultDAO;
import com.example.docta.myapplication.Classes.util.Constants;
import com.example.docta.myapplication.Classes.util.Student;
import com.example.docta.myapplication.Classes.util.TestResult;
import com.example.docta.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ResultTestVisualizationActivity extends AppCompatActivity {
    private ExpandableListView exp_list_Stud;
    private ArrayList<Student> students;
    private HashMap<String,ArrayList<TestResult>> testsResults;
    private StudentDAO studentDAO;
    private TestResultDAO testResultDAO;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_test_visualization);
        initComponents();

        ExpandableListTestResultAdapter adapter = new ExpandableListTestResultAdapter(this,students,testsResults);

        exp_list_Stud.setAdapter(adapter);

//        exp_list_Stud.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
////            @Override
////            public void onGroupExpand(int groupPosition) {
////                exp_list_Stud.expandGroup(groupPosition);
////            }
////        });

        exp_list_Stud
                .setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                        exp_list_Stud.expandGroup(groupPosition);
                         return false;
                    }
                });


        exp_list_Stud.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });
    }

    private void initComponents(){
        sharedPreferences = getSharedPreferences(Constants.PASSWORD_PROF_PREF,MODE_PRIVATE);
        String email = sharedPreferences.getString(Constants.EMAIL_PREF,null);
        exp_list_Stud = findViewById(R.id.exp_lv_result_test_stud);
        studentDAO = new StudentDAO(this);
        testResultDAO = new TestResultDAO(this);
        studentDAO.open();
        students = studentDAO.findAllMyStudents(email);
        studentDAO.close();


        testResultDAO.open();
        testsResults = testResultDAO.findMyStudentTests(students);
        testResultDAO.close();
    }


}
