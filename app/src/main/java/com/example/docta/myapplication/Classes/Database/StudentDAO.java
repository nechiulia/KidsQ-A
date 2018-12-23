package com.example.docta.myapplication.Classes.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.docta.myapplication.Classes.util.Student;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

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
//        String QUERRY_IF_STUD_EXISTS = "SELECT " + STUDENT_COLUMN_USERNAME + " WHERE " + STUDENT_COLUMN_USERNAME +" =?";
//        //Cursor c = database.rawQuery(QUERRY_IF_STUD_EXISTS,new String[]{});

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

    public boolean verifyStudentsName(String username){
        int s=0;
        Cursor c =  database.rawQuery(QUERRY_STUDENT_NAMES, new String[]{username});
        while (c.moveToNext()){
             s=c.getCount();
        }
        if(s>0){
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
        String QUERRY_SELECT_CURRENT_SCORE = "SELECT "+ STUDENT_COLUMN_SCORE+ " FROM " + STUDENT_TABLE_NAME + " WHERE " + STUDENT_COLUMN_USERNAME +" =?";
        Cursor c = database.rawQuery(QUERRY_SELECT_CURRENT_SCORE,new String[]{username});
        double Current_Score=0;
        while (c.moveToNext()){
            Current_Score = c.getDouble(c.getColumnIndex(STUDENT_COLUMN_SCORE));
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(STUDENT_COLUMN_SCORE,score+Current_Score);
        database.update(STUDENT_TABLE_NAME,contentValues,QUERRY_UPDATE_SCORE, new String[]{username});
    }

    public void updateStudentName(String newName, String user) {
        String QUERRY_UPDATE_NAME=STUDENT_COLUMN_USERNAME+" =? ";
        ContentValues contentValues = new ContentValues();
        contentValues.put(STUDENT_COLUMN_USERNAME, newName);

        database.update(STUDENT_TABLE_NAME,contentValues,QUERRY_UPDATE_NAME,new String[]{user});
    }

    public int updateAvatar(byte[] avatar, String username)
    {
        String QUERRY_UPDATE_AVATAR = STUDENT_COLUMN_USERNAME + " =?";
        String QUERRY_SELECT_CURRENT_AVATAR = "SELECT "+ STUDENT_COLUMN_CURRENT_AVATAR+ " FROM " + STUDENT_TABLE_NAME + " WHERE " + STUDENT_COLUMN_USERNAME +" =?";
        Cursor c=database.rawQuery(QUERRY_SELECT_CURRENT_AVATAR,new String[]{username});

        ContentValues contentValues=new ContentValues();
        contentValues.put(STUDENT_COLUMN_CURRENT_AVATAR, avatar);
        return database.update(STUDENT_TABLE_NAME,contentValues,QUERRY_UPDATE_AVATAR,new String[]{username});


    }

    public byte[] findMyAvatar(String username){
        String QUERRY_SELECT_CURRENT_AVATAR = "SELECT "+ STUDENT_COLUMN_CURRENT_AVATAR+ " FROM " + STUDENT_TABLE_NAME + " WHERE " + STUDENT_COLUMN_USERNAME +" =?";
        Cursor c=database.rawQuery(QUERRY_SELECT_CURRENT_AVATAR, new String[] {username});
        byte[] avatar=null;
        while (c.moveToNext()) {
             avatar = c.getBlob(c.getColumnIndex(STUDENT_COLUMN_CURRENT_AVATAR));
        }
        return avatar;
    }

    public  int deleteStudent(String username){
        String QUERRY_DELETE_STUD=STUDENT_COLUMN_USERNAME+" =?";
        if(username==null ){
            return -1;
        }
        return  database.delete(STUDENT_TABLE_NAME, QUERRY_DELETE_STUD, new String[]{username} );
    }
    public Double findScoreByUser(String username){
        String QUERRY_SELECT_SCORE = "SELECT "+ STUDENT_COLUMN_SCORE+ " FROM " + STUDENT_TABLE_NAME + " WHERE " + STUDENT_COLUMN_USERNAME +" =?";
        Cursor c=database.rawQuery(QUERRY_SELECT_SCORE, new String[] {username});
        Double score=0.0;
        while (c.moveToNext()) {
            score = c.getDouble(c.getColumnIndex(STUDENT_COLUMN_SCORE));
        }
        return score;
    }




//    public void updataStudentAfterDeleteTeacher( String email){
//        ContentValues contentValues = new ContentValues();
//
//        List<String> stringList = new ArrayList<>();
//        String QUERRY_SELECT_EMAIL = "SELECT " + STUDENT_COLUMN_EMAIL_TEACHER+" FROM " + STUDENT_TABLE_NAME + " WHERE " + STUDENT_COLUMN_EMAIL_TEACHER +" =?";
//        Cursor c = database.rawQuery(QUERRY_SELECT_EMAIL,new String[]{email});
//        while (c.moveToNext()){ contentValues.putNull(STUDENT_COLUMN_EMAIL_TEACHER);
//           Student student = new Student();
//           student.setUsername(c.getString(c.getColumnIndex(STUDENT_COLUMN_USERNAME)));
//           // stud.add(student);
//            stringList.add(student.getUsername());
//        }
//        c.close();
//        String[] stringArray = stringList.toArray(new String[stringList.size()]);
//        database.update(STUDENT_TABLE_NAME,contentValues,STUDENT_COLUMN_USERNAME + " =?",stringArray);
//
//    }



}
