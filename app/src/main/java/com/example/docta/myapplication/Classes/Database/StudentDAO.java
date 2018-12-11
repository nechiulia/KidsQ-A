package com.example.docta.myapplication.Classes.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.docta.myapplication.Classes.util.Student;

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
//        database.beginTransaction();
//        try{
//            SQLiteStatement insert = database.compileStatement(INSERT_STUDENT);
//            insert.bindString(0,student.getUsername());
//            insert.bindBlob(1,student.getAvatar());
//            insert.bindDouble(2,student.getGender());
//
//        }catch (SQLException e){
//            e.printStackTrace();
//        }finally {
//            database.endTransaction();
//        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(STUDENT_COLUMN_USERNAME, student.getUsername());
        contentValues.put(STUDENT_COLUMN_CURRENT_AVATAR,student.getAvatar());
        contentValues.put(STUDENT_COLUMN_GENDER,student.getGender());
        contentValues.put(STUDENT_COLUMN_AGE, student.getAge());
        contentValues.put(STUDENT_COLUMN_SCORE,student.getScore());
        contentValues.putNull(STUDENT_COLUMN_ID_TEACHER);
        database.insert(STUDENT_TABLE_NAME,null,contentValues);
    }

}
