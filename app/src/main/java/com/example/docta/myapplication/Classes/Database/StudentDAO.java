package com.example.docta.myapplication.Classes.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.docta.myapplication.Classes.util.Student;

import java.util.ArrayList;

public class StudentDAO implements DatabaseConstants {
    private SQLiteDatabase database;
    private DatabaseController controller;

    public StudentDAO(Context context) {
        controller = DatabaseController.getInstance(context);
    }

    public void open(){
        try{
            database = controller.getWritableDatabase();
        }catch(SQLException e){
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

    public void insertStudentByStudent(Student student){
        database.beginTransaction();
        try{
            SQLiteStatement insert = database.compileStatement(INSERT_STUDENT);
            insert.bindString(1,student.getUsername());
            insert.bindBlob(2,student.getAvatar());
            insert.bindDouble(3,student.getGender());
            insert.bindDouble(4,student.getAge());
            insert.bindDouble(5,student.getScore());
            insert.bindNull(6);
            insert.executeInsert();
            database.setTransactionSuccessful();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            database.endTransaction();
        }
    }
    public void insertStudentByTeacher(Student student){
        database.beginTransaction();
        try{
            SQLiteStatement insert = database.compileStatement(INSERT_STUDENT);
            insert.bindString(1,student.getUsername());
            insert.bindBlob(2,student.getAvatar());
            insert.bindDouble(3,student.getGender());
            insert.bindDouble(4,student.getAge());
            insert.bindDouble(5,student.getScore());
            insert.bindString(6,student.getEmail_teacher());
            insert.executeInsert();
            database.setTransactionSuccessful();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            database.endTransaction();
        }
    }

    public boolean loginStudent(String username){
        Cursor c =  database.rawQuery(QUERRY_LOGIN_STUDENT, new String[]{username});
        if(c.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<Student> findAllMyStudents(String email){
        ArrayList<Student> listStud = new ArrayList<>();

        Cursor c = database.rawQuery(QUERRY_STUDENT_LIST,new String[]{email});
        while (c.moveToNext()){
            String username = c.getString(c.getColumnIndex(STUDENT_COLUMN_USERNAME));
            byte[] avatar = c.getBlob(c.getColumnIndex(STUDENT_COLUMN_CURRENT_AVATAR));
            int gender = c.getInt(c.getColumnIndex(STUDENT_COLUMN_GENDER));
            int age = c.getInt(c.getColumnIndex(STUDENT_COLUMN_AGE));
            double score = c.getDouble(c.getColumnIndex(STUDENT_COLUMN_SCORE));
            String email_teacher = c.getString(c.getColumnIndex(STUDENT_COLUMN_EMAIL_TEACHER));
            listStud.add(new Student(username,avatar,age,gender,score,email_teacher));
        }
        c.close();
        return  listStud;
    }

    public void updateScore(double score,String username){
        String QUERRY_UPDATE_SCORE = STUDENT_COLUMN_USERNAME + " =?";
        ContentValues contentValues = new ContentValues();
        contentValues.put(STUDENT_COLUMN_SCORE,score);
        database.update(STUDENT_TABLE_NAME,contentValues,QUERRY_UPDATE_SCORE, new String[]{username});
    }



}
