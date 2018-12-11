package com.example.docta.myapplication.Classes.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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

    public void close(){
        try{
            database.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


}
