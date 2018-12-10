package com.example.docta.myapplication.Classes.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.docta.myapplication.Classes.Student;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO implements DatabaseConstants{

    private DatabaseController controller;
    private SQLiteDatabase database;

    public StudentDAO(Context context){
        controller=DatabaseController.getInstance(context);
    }

    public void open(){
        try{
            database=controller.getWritableDatabase();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public long insertStudentInDatabase(Student student){
        if(student!=null){
            ContentValues content = StudentContentValues(student);
            return database.insert(STUDENT_TABLE_NAME,null,content);
        }else {
            return -1;
        }
    }

    private ContentValues StudentContentValues(Student student) {
        ContentValues content = new ContentValues();
        content.put(STUDENT_COLUMN_USERNAME,student.getUsername());
        content.put(STUDENT_COLUMN_CURRENT_AVATAR,student.getAvatar());
        content.put(STUDENT_COLUMN_AGE,student.getAge());
        content.put(STUDENT_COLUMN_GENDER,student.getGender());
        if(student.getTeacher_email()==null){
            content.putNull(STUDENT_COLUMN_PROFESSOR_EMAIL);
        }else{
            content.put(STUDENT_COLUMN_PROFESSOR_EMAIL,student.getTeacher_email());
        }
        return content;
    }


    public List<Student> findAllStudents(){
        List<Student> results=new ArrayList<>();
        Cursor cursor=database.query(STUDENT_TABLE_NAME,
                new String[]{STUDENT_COLUMN_USERNAME, STUDENT_COLUMN_SCORE},
                null,
                null,
                null,
                null,
                STUDENT_COLUMN_SCORE+"DESC");
        while(cursor.moveToNext()){
            String username=cursor.getString(cursor.getColumnIndex(STUDENT_COLUMN_USERNAME));
            Double score=cursor.getDouble(cursor.getColumnIndex(STUDENT_COLUMN_SCORE));
            int gender=cursor.getInt(cursor.getColumnIndex(STUDENT_COLUMN_GENDER));
            int age=cursor.getInt(cursor.getColumnIndex(STUDENT_COLUMN_AGE));
            String teacher=cursor.getString(cursor.getColumnIndex(STUDENT_COLUMN_PROFESSOR_EMAIL));
            byte[] avatar=cursor.getBlob(cursor.getColumnIndex(STUDENT_COLUMN_CURRENT_AVATAR));

            results.add(new Student(username, avatar, age, gender, score, teacher));
        }cursor.close();

        return results;
    }

    public void close(){
        try{
            database.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
