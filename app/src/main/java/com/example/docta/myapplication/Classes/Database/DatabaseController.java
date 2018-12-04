package com.example.docta.myapplication.Classes.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseController extends SQLiteOpenHelper implements DatabaseConstants{

    private static DatabaseController controller;

    public DatabaseController(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public static DatabaseController getInstance(Context context){
        if(controller == null){
            synchronized (DatabaseController.class){
                if(controller == null){
                    controller = new DatabaseController(context);
                }
            }
        }

        return controller;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STUDENT);
        db.execSQL(CREATE_TABLE_TESTRESULTS);
        db.execSQL(CREATE_TABLE_TEACHER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_STUDENT);
        db.execSQL(DROP_TABLE_TESTRESULTS);
        db.execSQL(DROP_TABLE_TEACHER);
        onCreate(db);
    }
}
