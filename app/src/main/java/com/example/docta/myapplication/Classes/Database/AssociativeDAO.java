package com.example.docta.myapplication.Classes.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.docta.myapplication.Classes.util.Avatar;
import com.example.docta.myapplication.Classes.util.Student;

import java.util.ArrayList;

public class AssociativeDAO implements DatabaseConstants {
    private SQLiteDatabase database;
    private DatabaseController controller;

    public AssociativeDAO(Context context){
        controller = DatabaseController.getInstance(context);
    }

    public void open(){
        try{
            database = controller.getWritableDatabase();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public Long insertAssociativeAvatar(String username, long id_avatar){
        Long id=Long.parseLong("-1");

        database.beginTransaction();
        try{
            SQLiteStatement insert = database.compileStatement(DatabaseConstants.INSERT_ASSOCIATIVE_AVATAR);
                insert.bindLong(1,id_avatar);
                insert.bindString(2, username);

                id= insert.executeInsert();

            database.setTransactionSuccessful();
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            database.endTransaction();
        }
        return id;
    }
    public ArrayList<Long> findAllAvatarsId(String username){
        ArrayList<Long> results = new ArrayList<>();
        Cursor c = database.rawQuery(QUERRY_ASSOCIATIVE_AVATAR,new String[]{username});
        while (c.moveToNext()){
            Long id= c.getLong(c.getColumnIndex(ASSOCIATIVE_COLUMN_ID_AVATAR));
            results.add(id);
        }
        c.close();
        return results;
    }
    public int deleteAvatarById(Long id){
        if(id==null){
            return -1;
        }
        return  database.delete(ASSOCIATIVE_TABLE_NAME, ASSOCIATIVE_COLUMN_ID_AVATAR +"=?", new String[]{id.toString()} );
    }
    public void close(){
        try{
            database.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


}
