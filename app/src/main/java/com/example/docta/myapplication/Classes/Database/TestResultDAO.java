package com.example.docta.myapplication.Classes.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.docta.myapplication.Classes.util.Student;
import com.example.docta.myapplication.Classes.util.TestResult;

import java.util.ArrayList;
import java.util.HashMap;

public class TestResultDAO implements DatabaseConstants {
    private SQLiteDatabase database;
    private DatabaseController controller;

    public TestResultDAO(Context context){
        controller = DatabaseController.getInstance(context);
    }

    public void open(){
        try{
            database = controller.getWritableDatabase();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void close(){
        try{
            database.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void insertTestResult(TestResult testResult){
        String INSERT_TEST_RESULT = "INSERT INTO " + TESTRESULTS_TABLE_NAME + " ("+TESTRESULTS_COLUMN_ID_TEST +", "+
                TESTRESULTS_COLUMN_DIFFICULTY + ", " +
                TESTRESULTS_COLUMN_CATEGORY + ", " +
                TESTRESULTS_COLUMN_USERNAMESTUD + ", "+
                TESTRESULTS_COLUMN_CORRECTANSWERS+ ", " +
                TESTRESULTS_COLUMN_SCORE + " ) VALUES(?,?,?,?,?,?)";
        database.beginTransaction();
        try{
            SQLiteStatement insert = database.compileStatement(INSERT_TEST_RESULT);
            insert.bindString(2,testResult.getDifficulty());
            insert.bindString(3,testResult.getCategory());
            insert.bindString(4,testResult.getUsername());
            insert.bindDouble(5,testResult.getNoCorrectAnswers());
            insert.bindDouble(6,testResult.getScore());
            insert.executeInsert();
            database.setTransactionSuccessful();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            database.endTransaction();
        }
    }

    public HashMap<Student,ArrayList<TestResult>> findMyStudentTests(ArrayList<Student> students){
        HashMap<Student,ArrayList<TestResult>> resultStud = new HashMap<>();
        String QUERYY_FIND = "SELECT * FROM "+ TESTRESULTS_TABLE_NAME + " WHERE " + TESTRESULTS_COLUMN_USERNAMESTUD + " =?";
        for(int i = 0 ;i < students.size();i++) {
            ArrayList<TestResult> tests = new ArrayList<>();

            Cursor c = database.rawQuery(QUERYY_FIND, new String[]{students.get(i).getUsername()});

                while (c.moveToNext()){
                    String category = c.getString(c.getColumnIndex(TESTRESULTS_COLUMN_CATEGORY));
                    String dificulty = c.getString(c.getColumnIndex(TESTRESULTS_COLUMN_DIFFICULTY));
                    int noCorrectAnswers = c.getInt(c.getColumnIndex(TESTRESULTS_COLUMN_CORRECTANSWERS));
                    double score = c.getDouble(c.getColumnIndex(TESTRESULTS_COLUMN_SCORE));
                    TestResult testResult = new TestResult(dificulty, category, noCorrectAnswers, score);
                    tests.add(testResult);
                    Student student = new Student(tests.get(i).getUsername());
//                    tests.add(category);
//                    tests.add(dificulty);
//                    tests.add(String.valueOf(noCorrectAnswers));
//                    tests.add(String.valueOf(score));
                    resultStud.put(student, tests);
                }
          }
//          else{
//
//            }
        return resultStud;
    }


    public HashMap<Student,TestResult> FindThemAll(ArrayList<Student> students){
        String QUERY_SUM_SCORE = "SELECT SUM( "+ TESTRESULTS_COLUMN_SCORE + " ) as sumx , SUM( "+ TESTRESULTS_COLUMN_CORRECTANSWERS +" ) as sumy FROM "
                + TESTRESULTS_TABLE_NAME + " WHERE " +
                TESTRESULTS_COLUMN_USERNAMESTUD +" =?";

        String QUERY_COUNT_TESTDIF= "SELECT COUNT( "+TESTRESULTS_COLUMN_DIFFICULTY + " ) as count FROM "+ TESTRESULTS_TABLE_NAME+
                " WHERE "+ TESTRESULTS_COLUMN_USERNAMESTUD + " =? AND "+
                TESTRESULTS_COLUMN_DIFFICULTY + " =?" +" GROUP BY "+TESTRESULTS_COLUMN_DIFFICULTY;

        HashMap<Student,TestResult> resultHashMap = new HashMap<>();
        //SQLiteStatement score = database.compileStatement()
        for(int i = 0 ; i < students.size(); i++){
            TestResult result = new TestResult();
//            int dif_usor=0;
//            int dif_mediu=0;
//            int dif_greu=0;
            ArrayList<Integer> no_tests = new ArrayList<>();
            Cursor c1 = database.rawQuery(QUERY_COUNT_TESTDIF,new String[]{students.get(i).getUsername(),"Usor"});
            if(c1.moveToFirst()){
                no_tests.add(c1.getInt(c1.getColumnIndex("count")));
            }else{
                no_tests.add(0);
            }

            c1 = database.rawQuery(QUERY_COUNT_TESTDIF,new String[]{students.get(i).getUsername(),"Mediu"});
            if(c1.moveToFirst()){
                no_tests.add(c1.getInt(c1.getColumnIndex("count")));
            }else{
                no_tests.add(0);
            }

            c1 = database.rawQuery(QUERY_COUNT_TESTDIF,new String[]{students.get(i).getUsername(),"Greu"});
            if(c1.moveToFirst()){
                no_tests.add(c1.getInt(c1.getColumnIndex("count")));
            }else{
                no_tests.add(0);
            }

            Cursor c = database.rawQuery(QUERY_SUM_SCORE,new String[]{students.get(i).getUsername()});
            if (c.moveToFirst()){
                int noCorrectAnswers = c.getInt(c.getColumnIndex("sumy"));
                double score = c.getDouble(c.getColumnIndex("sumx"));
                result.setNoCorrectAnswers(noCorrectAnswers);
                result.setScore(score);
                result.setDif_tests(no_tests);
                }
            resultHashMap.put(students.get(i),result);
        }
        return resultHashMap;
    }
}
