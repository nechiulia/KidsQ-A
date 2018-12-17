package com.example.docta.myapplication.Classes.Database;

import android.content.ContentValues;
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
    public int deleteAvatarById(Long id, String user){
        if(id==null){
            return -1;
        }
        return  database.delete(ASSOCIATIVE_TABLE_NAME, ASSOCIATIVE_COLUMN_ID_AVATAR +"=? AND "+ ASSOCIATIVE_COLUMN_USERNAME+"=?", new String[]{id.toString(),user} );
    }

    public void updateUsername(String newName, String username){
        String QUERRY_UPDATE_NAME=ASSOCIATIVE_COLUMN_USERNAME+" =? ";
        String QUERRY_SELECT_USERNAME="SELECT "+ ASSOCIATIVE_COLUMN_USERNAME+ " FROM " + ASSOCIATIVE_TABLE_NAME + " WHERE " + ASSOCIATIVE_COLUMN_USERNAME +" =?";

        Cursor c=database.rawQuery(QUERRY_SELECT_USERNAME,new String[]{username});
        while(c.moveToNext()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ASSOCIATIVE_COLUMN_USERNAME, newName);
            database.update(ASSOCIATIVE_TABLE_NAME,contentValues,QUERRY_UPDATE_NAME,new String[]{username});
        }
    }

    public void deleteUsername(String username){
        String QUERRY_UPDATE_NAME=ASSOCIATIVE_COLUMN_USERNAME+" =? ";
        String QUERRY_SELECT_USERNAME="SELECT "+ ASSOCIATIVE_COLUMN_USERNAME+ " FROM " + ASSOCIATIVE_TABLE_NAME + " WHERE " + ASSOCIATIVE_COLUMN_USERNAME +" =?";

        Cursor c=database.rawQuery(QUERRY_SELECT_USERNAME,new String[]{username});
        while(c.moveToNext()) {
            database.delete(ASSOCIATIVE_TABLE_NAME,QUERRY_UPDATE_NAME,new String[]{username});
        }
    }

    public void close(){
        try{
            database.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


}
