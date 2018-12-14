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

    public HashMap<String,ArrayList<TestResult>> findMyStudentTests(ArrayList<Student> students){
        HashMap<String,ArrayList<TestResult>> resultStud = new HashMap<>();
        String QUERYY_FIND = "SELECT * FROM "+ TESTRESULTS_TABLE_NAME + " WHERE " + TESTRESULTS_COLUMN_USERNAMESTUD + " =?";
        for(int i = 0 ;i < students.size();i++) {
            ArrayList<TestResult> tests = new ArrayList<>();

            Cursor c = database.rawQuery(QUERYY_FIND, new String[]{students.get(i).getUsername()});
            if (c.getCount() > 0){
                while (c.moveToNext()) {
                    String category = c.getString(c.getColumnIndex(TESTRESULTS_COLUMN_CATEGORY));
                    String dificulty = c.getString(c.getColumnIndex(TESTRESULTS_COLUMN_DIFFICULTY));
                    int noCorrectAnswers = c.getInt(c.getColumnIndex(TESTRESULTS_COLUMN_CORRECTANSWERS));
                    double score = c.getDouble(c.getColumnIndex(TESTRESULTS_COLUMN_SCORE));
                    TestResult testResult = new TestResult(dificulty, category, noCorrectAnswers, score);
                    tests.add(testResult);
                }
            resultStud.put(tests.get(i).getUsername(), tests);
          }else{
                return null;
            }
        }
        return resultStud;
    }
}
