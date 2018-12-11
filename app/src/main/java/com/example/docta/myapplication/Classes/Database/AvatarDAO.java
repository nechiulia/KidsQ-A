package com.example.docta.myapplication.Classes.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.docta.myapplication.Classes.util.Avatar;

import java.util.ArrayList;

public class AvatarDAO implements DatabaseConstants {
    private  DatabaseController controller;
    private  SQLiteDatabase database;

    public AvatarDAO(Context context){
        controller = DatabaseController.getInstance(context);
    }
    public  void open(){
        try{
            database=controller.getWritableDatabase();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public  void close(){
        try{
            database.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public  void insertAvatarsInDatabase(ArrayList<Avatar> list) {
        database.beginTransaction();
        try{
            SQLiteStatement insert = database.compileStatement(INSERT_AVATAR);
            for (int i = 0; i < list.size(); i++) {
                insert.bindString(1,list.get(i).getName());
                insert.bindDouble(2,list.get(i).getPrice());
                insert.bindBlob(3,list.get(i).getImage());
                insert.bindLong(4,Long.valueOf(list.get(i).getAppAvatar()));

                insert.executeInsert();
            }
            database.setTransactionSuccessful();
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            database.endTransaction();
        }
    }
    public  long insertAvatarInDatabase(Avatar avatar) {
       long id=-2;
        if(avatar==null){
            id= -1;}
        else {
            database.beginTransaction();
            try {
                SQLiteStatement insert = database.compileStatement(INSERT_AVATAR);

                insert.bindString(1, avatar.getName());
                insert.bindDouble(2, avatar.getPrice());
                insert.bindBlob(3, avatar.getImage());
                insert.bindLong(4, Long.valueOf(avatar.getAppAvatar()));
                id= insert.executeInsert();
                database.setTransactionSuccessful();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                database.endTransaction();
            }

        }
        return id;
    }

    public  ArrayList<Avatar> findAllAvatarsFromApp() {

        ArrayList<Avatar> results = new ArrayList<>();

        String whereClause = AVATAR_COLUMN_APP_AVATAR+ " = ?";
        String[] whereArgs = new String[] {
                "1",
        };
        Cursor cursor = database.query(AVATAR_TABLE_NAME, null, whereClause, whereArgs,
                null, null, null);

        while (cursor.moveToNext()) {

            Long id = cursor.getLong(cursor.getColumnIndex(AVATAR_COLUMN_ID_AVATAR));

            String name = cursor.getString(cursor
                    .getColumnIndex(AVATAR_COLUMN_NAME));
            Double price = cursor.getDouble(cursor
                    .getColumnIndex(AVATAR_COLUMN_PRICE));

            byte[] image = cursor.getBlob(cursor.getColumnIndex(AVATAR_COLUMN_IMAGE));
            long app_avatar= cursor.getLong(cursor.getColumnIndex(AVATAR_COLUMN_APP_AVATAR));

            results.add(new Avatar(id, name, price, image,app_avatar));
        }
        cursor.close();

        return results;
    }
    public  ArrayList<Avatar> findAllAvatarsFromPhone() {

        ArrayList<Avatar> results = new ArrayList<>();

        String whereClause = AVATAR_COLUMN_APP_AVATAR+ " = ?";
        String[] whereArgs = new String[] {
                "0",
        };
        Cursor cursor = database.query(AVATAR_TABLE_NAME, null, whereClause, whereArgs,
                null, null, null);

        while (cursor.moveToNext()) {

            Long id = cursor.getLong(cursor.getColumnIndex(AVATAR_COLUMN_ID_AVATAR));

            String name = cursor.getString(cursor
                    .getColumnIndex(AVATAR_COLUMN_NAME));
            Double price = cursor.getDouble(cursor
                    .getColumnIndex(AVATAR_COLUMN_PRICE));

            byte[] image = cursor.getBlob(cursor.getColumnIndex(AVATAR_COLUMN_IMAGE));
            long app_avatar= cursor.getLong(cursor.getColumnIndex(AVATAR_COLUMN_APP_AVATAR));

            results.add(new Avatar(id, name, price, image,app_avatar));
        }
        cursor.close();

        return results;
    }
    public  long updatePhoneAvatar(String avatarName, Long id){


        ContentValues contentValues = new ContentValues();
        contentValues.put(AVATAR_COLUMN_NAME, avatarName);


        return  database.update(AVATAR_TABLE_NAME,contentValues,AVATAR_COLUMN_ID_AVATAR + " = ?",new String[]{id.toString()});
    }
    public  int deleteAvatarFromPhone(Avatar avatar){
        if(avatar==null || avatar.getId()==null){
            return -1;
        }
        return  database.delete(AVATAR_TABLE_NAME, AVATAR_COLUMN_ID_AVATAR +"=?", new String[]{avatar.getId().toString()} );
    }

}
