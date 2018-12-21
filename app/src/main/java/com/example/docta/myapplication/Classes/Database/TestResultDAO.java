package com.example.docta.myapplication.Classes.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.widget.ArrayAdapter;

import com.example.docta.myapplication.Classes.util.Constants;
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

//    public HashMap<Student,ArrayList<TestResult>> findMyStudentTests(ArrayList<Student> students){
//        HashMap<Student,ArrayList<TestResult>> resultStud = new HashMap<>();
//        String QUERYY_FIND = "SELECT * FROM "+ TESTRESULTS_TABLE_NAME + " WHERE " + TESTRESULTS_COLUMN_USERNAMESTUD + " =?";
//        for(int i = 0 ;i < students.size();i++) {
//            ArrayList<TestResult> tests = new ArrayList<>();
//
//            Cursor c = database.rawQuery(QUERYY_FIND, new String[]{students.get(i).getUsername()});
//
//                while (c.moveToNext()){
//                    String category = c.getString(c.getColumnIndex(TESTRESULTS_COLUMN_CATEGORY));
//                    String dificulty = c.getString(c.getColumnIndex(TESTRESULTS_COLUMN_DIFFICULTY));
//                    int noCorrectAnswers = c.getInt(c.getColumnIndex(TESTRESULTS_COLUMN_CORRECTANSWERS));
//                    double score = c.getDouble(c.getColumnIndex(TESTRESULTS_COLUMN_SCORE));
//                    TestResult testResult = new TestResult(dificulty, category, noCorrectAnswers, score);
//                    tests.add(testResult);
//                    Student student = new Student(tests.get(i).getUsername());
//                    resultStud.put(student, tests);
//                }
//          }
//        return resultStud;
//    }


    public HashMap<Student,TestResult> FindThemAll(ArrayList<Student> students){
        String QUERY_SUM_SCORE = "SELECT SUM( "+ TESTRESULTS_COLUMN_SCORE + " ) as sumx , SUM( "+ TESTRESULTS_COLUMN_CORRECTANSWERS +" ) as sumy FROM "
                + TESTRESULTS_TABLE_NAME + " WHERE " +
                TESTRESULTS_COLUMN_USERNAMESTUD +" =?";

        String QUERY_COUNT_TESTDIF= "SELECT COUNT( "+TESTRESULTS_COLUMN_DIFFICULTY + " ) as count FROM "+ TESTRESULTS_TABLE_NAME+
                " WHERE "+ TESTRESULTS_COLUMN_USERNAMESTUD + " =? AND "+
                TESTRESULTS_COLUMN_DIFFICULTY + " =?" +" GROUP BY "+TESTRESULTS_COLUMN_DIFFICULTY;

        HashMap<Student,TestResult> resultHashMap = new HashMap<>();
        for(int i = 0 ; i < students.size(); i++){
            TestResult result = new TestResult();
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



    @SuppressLint("Recycle")
    public ArrayList<String> studentTestResult(String username,String[] dificulties,String category){
        ArrayList<String> result = new ArrayList<>();
        String QUERY_1 = "SELECT SUM( "+ TESTRESULTS_COLUMN_SCORE + " ) as sumx , SUM( "+ TESTRESULTS_COLUMN_CORRECTANSWERS +" ) as sumy FROM "
                + TESTRESULTS_TABLE_NAME + " WHERE " +
                TESTRESULTS_COLUMN_USERNAMESTUD +" =? AND "+ TESTRESULTS_COLUMN_DIFFICULTY + " =? AND "+ TESTRESULTS_COLUMN_CATEGORY + "=?";

        String QUERY_2 = "SELECT COUNT( "+TESTRESULTS_COLUMN_DIFFICULTY + " ) as count FROM "+ TESTRESULTS_TABLE_NAME+
                " WHERE "+ TESTRESULTS_COLUMN_USERNAMESTUD + " =? AND "+
                TESTRESULTS_COLUMN_DIFFICULTY + " =? AND "+ TESTRESULTS_COLUMN_CATEGORY+" =?" +" GROUP BY "+TESTRESULTS_COLUMN_DIFFICULTY;

        String QUERY_3 = "SELECT SUM( "+ TESTRESULTS_COLUMN_SCORE + " ) as sumx , SUM( "+ TESTRESULTS_COLUMN_CORRECTANSWERS +" ) as sumy FROM "
                + TESTRESULTS_TABLE_NAME + " WHERE " +
                TESTRESULTS_COLUMN_USERNAMESTUD +" =? AND "+ TESTRESULTS_COLUMN_DIFFICULTY + " =?";

        String QUERY_4 = "SELECT COUNT( "+TESTRESULTS_COLUMN_DIFFICULTY + " ) as count FROM "+ TESTRESULTS_TABLE_NAME+
                " WHERE "+ TESTRESULTS_COLUMN_USERNAMESTUD + " =? AND "+
                TESTRESULTS_COLUMN_DIFFICULTY + " =?" +" GROUP BY "+TESTRESULTS_COLUMN_DIFFICULTY;
        //answers
        double no_correct=0;
        double no_easy_corrects=0;
        double no_medium_corrects=0;
        double no_hard_corrects=0;
        //tests
        double resolved_Tests = 0;
        double no_easy_tests = 0;
        double no_medium_tests = 0;
        double no_hard_tests = 0;
        Cursor c;
        if(!category.equals("total")) {
            if (dificulties[0] != null) {
                c = database.rawQuery(QUERY_1, new String[]{username, dificulties[0], category});
                if (c.moveToFirst()) {
                    no_correct = c.getInt(c.getColumnIndex("sumy"));
                    no_easy_corrects = no_correct;
                }
            }
            if (dificulties[1] != null) {
                c = database.rawQuery(QUERY_1, new String[]{username, dificulties[1], category});
                if (c.moveToFirst()) {
                    no_correct += c.getInt(c.getColumnIndex("sumy"));
                    no_medium_corrects = c.getInt(c.getColumnIndex("sumy"));
                }
            }
            if (dificulties[2] != null) {
                c = database.rawQuery(QUERY_1, new String[]{username, dificulties[2], category});
                if (c.moveToFirst()) {
                    no_correct += c.getInt(c.getColumnIndex("sumy"));
                    no_hard_corrects = c.getInt(c.getColumnIndex("sumy"));

                }
            }


            if (dificulties[0] != null) {
                c = database.rawQuery(QUERY_2, new String[]{username, dificulties[0], category});
                if (c.moveToFirst()) {
                    resolved_Tests = c.getInt(c.getColumnIndex("count"));
                    no_easy_tests = resolved_Tests;
                }
            }

            if (dificulties[1] != null) {
                c = database.rawQuery(QUERY_2, new String[]{username, dificulties[1], category});
                if (c.moveToFirst()) {
                    resolved_Tests += c.getInt(c.getColumnIndex("count"));
                    no_medium_tests = c.getInt(c.getColumnIndex("count"));
                }
            }
            if (dificulties[2] != null) {
                c = database.rawQuery(QUERY_2, new String[]{username, dificulties[2], category});
                if (c.moveToFirst()) {
                    resolved_Tests += c.getInt(c.getColumnIndex("count"));
                    no_hard_tests = c.getInt(c.getColumnIndex("count"));
                }
            }
        }
        else{
            if (dificulties[0] != null) {
                c = database.rawQuery(QUERY_3, new String[]{username, dificulties[0]});
                if (c.moveToFirst()) {
                    no_correct = c.getInt(c.getColumnIndex("sumy"));
                    no_easy_corrects = no_correct;
                }
            }
            if (dificulties[1] != null) {
                c = database.rawQuery(QUERY_3, new String[]{username, dificulties[1]});
                if (c.moveToFirst()) {
                    no_correct += c.getInt(c.getColumnIndex("sumy"));
                    no_medium_corrects = c.getInt(c.getColumnIndex("sumy"));
                }
            }
            if (dificulties[2] != null) {
                c = database.rawQuery(QUERY_3, new String[]{username, dificulties[2]});
                if (c.moveToFirst()) {
                    no_correct += c.getInt(c.getColumnIndex("sumy"));
                    no_hard_corrects = c.getInt(c.getColumnIndex("sumy"));

                }
            }


            if (dificulties[0] != null) {
                c = database.rawQuery(QUERY_4, new String[]{username, dificulties[0]});
                if (c.moveToFirst()) {
                    resolved_Tests = c.getInt(c.getColumnIndex("count"));
                    no_easy_tests = resolved_Tests;
                }
            }

            if (dificulties[1] != null) {
                c = database.rawQuery(QUERY_4, new String[]{username, dificulties[1]});
                if (c.moveToFirst()) {
                    resolved_Tests += c.getInt(c.getColumnIndex("count"));
                    no_medium_tests = c.getInt(c.getColumnIndex("count"));
                }
            }
            if (dificulties[2] != null) {
                c = database.rawQuery(QUERY_4, new String[]{username, dificulties[2]});
                if (c.moveToFirst()) {
                    resolved_Tests += c.getInt(c.getColumnIndex("count"));
                    no_hard_tests = c.getInt(c.getColumnIndex("count"));
                }
            }
        }

        int average_Efficiency=0;
        if(no_easy_tests!=0 && no_medium_tests !=0 && no_hard_tests!=0) {
            double x1 = no_easy_corrects /((double) 5 * no_easy_tests);
            double x2 = no_medium_corrects /((double) 5 * no_medium_tests);
            double x3 = no_hard_corrects /((double) 5 * no_hard_tests);
            average_Efficiency = (int)(((x1+x2+x3)/3)*(double)100);

        }else if(no_easy_tests!=0 && no_medium_tests !=0 ||no_easy_tests !=0 && no_hard_tests!=0 || no_medium_tests!=0 && no_hard_tests!=0 ){
            double x1 = no_easy_corrects /((double) 5 * no_easy_tests);
            double x2 = no_medium_corrects /((double) 5 * no_medium_tests);
            double x3 = no_hard_corrects /((double) 5 * no_hard_tests);
            if(Double.isNaN(x1)){
                x1=0;
            }
            if(Double.isNaN(x2)){
                x2=0;
            }
            if(Double.isNaN(x3)){
                x3=0;
            }
            average_Efficiency = (int)(((x1+x2+x3)/2)*(double)100);
        }else if(no_easy_tests != 0 || no_medium_tests != 0 || no_hard_tests!=0){
            double x1 = no_easy_corrects /((double) 5 * no_easy_tests);
            double x2 = no_medium_corrects /((double) 5 * no_medium_tests);
            double x3 = no_hard_corrects /((double) 5 * no_hard_tests);
            if(Double.isNaN(x1)){
                x1=0;
            }
            if(Double.isNaN(x2)){
                x2=0;
            }
            if(Double.isNaN(x3)){
                x3=0;
            }
            average_Efficiency = (int)(((x1+x2+x3))*(double)100);
        }

        //+ no_medium_corrects / 5 * no_medium_tests + no_hard_corrects / 5 * no_hard_tests
        result.add(String.valueOf((int)no_correct));
        result.add(String.valueOf((int) resolved_Tests));
        result.add(String.valueOf(average_Efficiency));

        return result;
    }

//    private void NaNnumber(double x1, double x2, double x3){
//        if(Double.isNaN(x1)){
//            x1=0;
//        }
//        if(Double.isNaN(x2)){
//            x2=0;
//        }
//        if(Double.isNaN(x3)){
//            x3=0;
//        }
//
//    }
}
