package com.example.docta.myapplication.Classes.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseRepository implements DatabaseConstants{

    private SQLiteDatabase database;
    private DatabaseController controller;

    public DatabaseRepository(Context context){
        controller=DatabaseController.getInstance(context);
    }

    public void open(){
        try{
            database=controller.getWritableDatabase();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void close(){
        try{
            database.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


//    public long insertAccountStudent(Student student){
//        if(student!=null){
//            ContentValues contentV = StudentContentValues(student);
//            return database.insert(STUDENT_TABLE_NAME,null,contentV);
//        }else{
//            return -1;
//        }
//    }
//
//    private ContentValues StudentContentValues(Student student) {
//        ContentValues contentV = new ContentValues();
//        contentV.put(STUDENT_COLUMN_USERNAME,student.getUsername());
//        contentV.put(STUDENT_COLUMN_CURRENT_AVATAR,student.getAvatar());
//        contentV.put(STUDENT_COLUMN_AGE,student.getAge());
//        contentV.put(STUDENT_COLUMN_GENDER,student.getGender());
//        if(student.getTeacher_email()==null){
//            contentV.putNull(STUDENT_COLUMN_PROFESSOR_EMAIL);
//        }else{
//            contentV.put(STUDENT_COLUMN_PROFESSOR_EMAIL,student.getTeacher_email());
//        }
//        return contentV;
//    }
//
//    public int LoginStudent(String username){
//        int poz=0;
//        Cursor cursor = database.rawQuery("select * from login_table where username=" + "\""+ username.trim() + "\"", null);
//        cursor.moveToFirst();
//        poz = cursor.getCount();
//        cursor.close();
//        return poz;
//    }

}
