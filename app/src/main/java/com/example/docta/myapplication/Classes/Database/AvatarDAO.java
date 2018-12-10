package com.example.docta.myapplication.Classes.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.docta.myapplication.Classes.Avatar;
import com.example.docta.myapplication.Classes.Teacher;

import java.util.ArrayList;

public class AvatarDAO implements DatabaseConstants {
    private DatabaseController controller;
    private SQLiteDatabase database;

    public AvatarDAO(Context context){
        controller = DatabaseController.getInstance(context);
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
    public void insertAvatarsInDatabase(ArrayList<Avatar> list) {
        database.beginTransaction();
        try{
            SQLiteStatement insert = database.compileStatement(INSERT_AVATAR);
            for (int i = 0; i < list.size(); i++) {
                insert.bindString(1,list.get(i).getName());
                insert.bindDouble(2,list.get(i).getPrice());
                insert.bindBlob(3,list.get(i).getImage());
                insert.bindString(4,String.valueOf(list.get(i).getAppAvatar()));

                insert.executeInsert();
            }
            database.setTransactionSuccessful();
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            database.endTransaction();
        }
    }
    public boolean LoginTeacherFromDatabase(String email, String password){
        Cursor c = database.rawQuery(QUERRY_FOR_LOGIN,new String[]{email,password});
        if(c.getCount()>0){
            return true;
        }else{
            return false;
        }
    }
    public ArrayList<Avatar> findAllAvatarsFromApp() {

        ArrayList<Avatar> results = new ArrayList<>();

        Cursor cursor = database.query(AVATAR_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()) {

            Long id = cursor.getLong(cursor.getColumnIndex(AVATAR_COLUMN_ID_AVATAR));

            String name = cursor.getString(cursor
                    .getColumnIndex(AVATAR_COLUMN_NAME));
            Double price = cursor.getDouble(cursor
                    .getColumnIndex(AVATAR_COLUMN_PRICE));

            byte[] image = cursor.getBlob(cursor.getColumnIndex(AVATAR_COLUMN_IMAGE));
            Boolean app_avatar= cursor.getString(cursor.getColumnIndex(AVATAR_COLUMN_APP_AVATAR)).equals("true") ? true:false;

            results.add(new Avatar(id, name, price, image,app_avatar));
        }
        cursor.close();

        return results;
    }

}
