package com.example.docta.myapplication.Classes.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import com.example.docta.myapplication.Classes.util.Tasks;

import java.util.ArrayList;
import java.util.Objects;

public class TasksDAO implements DatabaseConstants{

    private DatabaseController controller;
    private SQLiteDatabase database;


    public TasksDAO(Context context){
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

    public long insertTasksInDatabase(String date, String info, String username){
        long insertedTaskId = -1;
        database.beginTransaction();
        try{
            //SQLiteStatement insert=database.compileStatement(INSERT_TASKS);
            /*for(int i=0;i<list.size();i++){
                insert.bindString(1,list.get(i).getDate());
                insert.bindString(2,list.get(i).getInfo());
                insert.bindString(3,list.get(i).getUsername());

                insert.executeInsert();
            }*/
            ContentValues values = new ContentValues();
            values.put(TASKS_COLUMN_DATE,date);
            values.put(TASKS_COLUMN_INFO,info);
            values.put(TASKS_COLUMN_ID_STUDENT,username);

            insertedTaskId = database.insert(TASKS_TABLE_NAME,null,values);
            database.setTransactionSuccessful();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            database.endTransaction();
        }

        return insertedTaskId;
    }

    public Cursor selectTasksUser(String username){
        Cursor c = database.rawQuery(QUERY_TASKS, new String[] {username});
        return c;
    }

    public int deleteTask(String date, String info,String username){
        int index=database.delete(TASKS_TABLE_NAME,TASKS_COLUMN_DATE+" =? AND "+TASKS_COLUMN_INFO+" =? AND "+ TASKS_COLUMN_ID_STUDENT+" =? ", new String[]{date,info,username});
        return index;
    }

    public int deleteTaskById(Long id){
        int index = database.delete(TASKS_TABLE_NAME,TASKS_COLUMN_ID_TASK+" =? ", new String[]{ id.toString()});
        return index;
    }

    public int deleteAllTasks(String username){
        int index = database.delete(TASKS_TABLE_NAME, TASKS_COLUMN_ID_STUDENT+" =? ",new String[]{username});
        return index;
    }

    public long update(Long id,String date, String info){
        ContentValues values=new ContentValues();
        values.put(TASKS_COLUMN_DATE,date);
        values.put(TASKS_COLUMN_INFO,info);

        return database.update(TASKS_TABLE_NAME, values,TASKS_COLUMN_ID_TASK
                +" =? ",new String[]{id.toString()});
    }

}
