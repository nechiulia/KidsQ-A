package com.example.docta.myapplication.Classes.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
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

public void updateUsername(String newName, String username){
    String QUERRY_UPDATE_NAME=TESTRESULTS_COLUMN_USERNAMESTUD+" =? ";
    String QUERRY_SELECT_USERNAME="SELECT "+ TESTRESULTS_COLUMN_USERNAMESTUD+ " FROM " +TESTRESULTS_TABLE_NAME+ " WHERE " + TESTRESULTS_COLUMN_USERNAMESTUD +" =?";

    Cursor c=database.rawQuery(QUERRY_SELECT_USERNAME,new String[]{username});
    while(c.moveToNext()) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TESTRESULTS_COLUMN_USERNAMESTUD, newName);
        database.update(TESTRESULTS_TABLE_NAME,contentValues,QUERRY_UPDATE_NAME,new String[]{username});
    }
}


    public void deleteUsername(String username){
        String QUERRY_UPDATE_NAME=TESTRESULTS_COLUMN_USERNAMESTUD+" =? ";
        String QUERRY_SELECT_USERNAME="SELECT "+ TESTRESULTS_COLUMN_USERNAMESTUD+ " FROM " + TESTRESULTS_TABLE_NAME + " WHERE " + TESTRESULTS_COLUMN_USERNAMESTUD+" =?";

        Cursor c=database.rawQuery(QUERRY_SELECT_USERNAME,new String[]{username});
        while(c.moveToNext()) {
            database.delete(TESTRESULTS_TABLE_NAME,QUERRY_UPDATE_NAME,new String[]{username});
        }
    }


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
            Cursor c1 = database.rawQuery(QUERY_COUNT_TESTDIF,new String[]{students.get(i).getUsername(),Constants.DIFFICULTY_EASY_TEST});
            if(c1.moveToFirst()){
                no_tests.add(c1.getInt(c1.getColumnIndex(Constants.COUNT)));
            }else{
                no_tests.add(0);
            }

            c1 = database.rawQuery(QUERY_COUNT_TESTDIF,new String[]{students.get(i).getUsername(),Constants.DIFFICULTY_MEDIUM_TEST});
            if(c1.moveToFirst()){
                no_tests.add(c1.getInt(c1.getColumnIndex(Constants.COUNT)));
            }else{
                no_tests.add(0);
            }

            c1 = database.rawQuery(QUERY_COUNT_TESTDIF,new String[]{students.get(i).getUsername(),Constants.DIFFICULTY_HARD_TEST});
            if(c1.moveToFirst()){
                no_tests.add(c1.getInt(c1.getColumnIndex(Constants.COUNT)));
            }else{
                no_tests.add(0);
            }

            Cursor c = database.rawQuery(QUERY_SUM_SCORE,new String[]{students.get(i).getUsername()});
            if (c.moveToFirst()){
                int noCorrectAnswers = c.getInt(c.getColumnIndex(Constants.SUMY));
                double score = c.getDouble(c.getColumnIndex(Constants.SUMX));
                result.setNoCorrectAnswers(noCorrectAnswers);
                result.setScore(score);
                result.setDif_tests(no_tests);
                }
            resultHashMap.put(students.get(i),result);
        }
        return resultHashMap;
    }

    public void updateStudentNameTest(String newName, String username) {
        String QUERRY_UPDATE_NAME=TESTRESULTS_COLUMN_USERNAMESTUD+" =? ";
        String QUERRY_SELECT_USERNAME="SELECT "+ TESTRESULTS_COLUMN_USERNAMESTUD+ " FROM " + TESTRESULTS_TABLE_NAME + " WHERE " + TESTRESULTS_COLUMN_USERNAMESTUD +" =?";

        Cursor c=database.rawQuery(QUERRY_SELECT_USERNAME,new String[]{username});
        while(c.moveToNext()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TESTRESULTS_COLUMN_USERNAMESTUD, newName);
            database.update(TESTRESULTS_TABLE_NAME,contentValues,QUERRY_UPDATE_NAME,new String[]{username});
        }
    }



    @SuppressLint("Recycle")
    public ArrayList<String> studentTestResult(String username,String[] dificulties,String category){
        ArrayList<String> result = new ArrayList<>();
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
        for(int i =0;i<dificulties.length;i++) {
            if (!category.equals(Constants.CATEG_TOTAL)) {
                if (dificulties[i] != null) {
                    c = database.rawQuery(QUERY_1, new String[]{username, dificulties[i],category});
                    if (c.moveToFirst()) {
                        no_correct += c.getDouble(c.getColumnIndex(Constants.SUMY));
                        if(dificulties[i].equals(Constants.DIFFICULTY_EASY_TEST)){
                            no_easy_corrects = c.getInt(c.getColumnIndex(Constants.SUMY));
                        }
                        if(dificulties[i].equals(Constants.DIFFICULTY_MEDIUM_TEST)){
                            no_medium_corrects = c.getInt(c.getColumnIndex(Constants.SUMY));
                        }
                        if(dificulties[i].equals(Constants.DIFFICULTY_HARD_TEST)){
                            no_hard_corrects = c.getInt(c.getColumnIndex(Constants.SUMY));
                        }
                    }

                    c = database.rawQuery(QUERY_2, new String[]{username, dificulties[i],category});
                    if (c.moveToFirst()) {
                        resolved_Tests += c.getDouble(c.getColumnIndex(Constants.COUNT));
                        if(dificulties[i].equals(Constants.DIFFICULTY_EASY_TEST)){
                            no_easy_tests = c.getInt(c.getColumnIndex(Constants.COUNT));
                        }
                        if(dificulties[i].equals(Constants.DIFFICULTY_MEDIUM_TEST)){
                            no_medium_tests = c.getInt(c.getColumnIndex(Constants.COUNT));
                        }
                        if(dificulties[i].equals(Constants.DIFFICULTY_HARD_TEST)){
                            no_hard_tests = c.getInt(c.getColumnIndex(Constants.COUNT));
                        }
                    }
                }
            }
            //cand e total
            else {
                if (dificulties[i] != null) {
                    c = database.rawQuery(QUERY_3, new String[]{username, dificulties[i]});
                    if (c.moveToFirst()) {
                            if(dificulties[i].equals(Constants.DIFFICULTY_EASY_TEST) ){
                                no_correct += c.getDouble(c.getColumnIndex(Constants.SUMY));
                                no_easy_corrects = c.getInt(c.getColumnIndex(Constants.SUMY));
                            }
                            if(dificulties[i].equals(Constants.DIFFICULTY_MEDIUM_TEST)){
                                no_correct += c.getDouble(c.getColumnIndex(Constants.SUMY));
                                no_medium_corrects = c.getInt(c.getColumnIndex(Constants.SUMY));
                            }
                            if(dificulties[i].equals(Constants.DIFFICULTY_HARD_TEST)){
                                no_correct += c.getDouble(c.getColumnIndex(Constants.SUMY));
                                no_hard_corrects = c.getInt(c.getColumnIndex(Constants.SUMY));
                            }
                    }

                    c = database.rawQuery(QUERY_4, new String[]{username, dificulties[i]});
                    if (c.moveToFirst()) {
                            if(dificulties[i].equals(Constants.DIFFICULTY_EASY_TEST)){
                                resolved_Tests += c.getDouble(c.getColumnIndex(Constants.COUNT));
                                no_easy_tests = c.getInt(c.getColumnIndex(Constants.COUNT));
                            }
                            if(dificulties[i].equals(Constants.DIFFICULTY_MEDIUM_TEST)){
                                resolved_Tests += c.getDouble(c.getColumnIndex(Constants.COUNT));
                                no_medium_tests = c.getInt(c.getColumnIndex(Constants.COUNT));
                            }
                            if(dificulties[i].equals(Constants.DIFFICULTY_HARD_TEST)){
                                resolved_Tests += c.getDouble(c.getColumnIndex(Constants.COUNT));
                                no_hard_tests = c.getInt(c.getColumnIndex(Constants.COUNT));
                            }
                    }
                }
            }
        }

        int average_Efficiency=0;
        double x1, x2 ,x3 ;
        if(no_easy_tests!=0 && no_medium_tests !=0 && no_hard_tests!=0) {
             x1 = no_easy_corrects /((double) 5 * no_easy_tests);
             x2 = no_medium_corrects /((double) 5 * no_medium_tests);
             x3 = no_hard_corrects /((double) 5 * no_hard_tests);
            average_Efficiency = (int)(((x1+x2+x3)/3)*(double)100);

        }else if(no_easy_tests!=0 && no_medium_tests !=0 ||no_easy_tests !=0 && no_hard_tests!=0 || no_medium_tests!=0 && no_hard_tests!=0 ){
             x1 = no_easy_corrects /((double) 5 * no_easy_tests);
             x2 = no_medium_corrects /((double) 5 * no_medium_tests);
             x3 = no_hard_corrects /((double) 5 * no_hard_tests);
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
             x1 = no_easy_corrects /((double) 5 * no_easy_tests);
             x2 = no_medium_corrects /((double) 5 * no_medium_tests);
             x3 = no_hard_corrects /((double) 5 * no_hard_tests);
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
        result.add(String.valueOf((int)no_correct));
        result.add(String.valueOf((int)resolved_Tests));
        result.add(String.valueOf(average_Efficiency));

        return result;
    }
}
