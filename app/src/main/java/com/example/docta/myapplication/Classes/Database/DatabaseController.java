package com.example.docta.myapplication.Classes.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

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
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TEACHER);
        db.execSQL(CREATE_TABLE_STUDENT);
        db.execSQL(CREATE_TABLE_TASKS);
        db.execSQL(CREATE_TABLE_TESTRESULTS);
        db.execSQL(CREATE_TABLE_AVATAR);
        db.execSQL(CREATE_TABLE_ASSOCIATIVE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_TEACHER);
        db.execSQL(DROP_TABLE_STUDENT);
        db.execSQL(DROP_TABLE_TASKS);
        db.execSQL(DROP_TABLE_TESTRESULTS);
        db.execSQL(DROP_TABLE_AVATAR);
        db.execSQL(DROP_TABLE_ASSOCIATIVE);
        onCreate(db);
    }
}
