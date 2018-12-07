package com.example.docta.myapplication.Classes.Database;

public interface DatabaseConstants {

    String DATABASE_NAME="dosbrains.db";
    int DATABASE_VERSION=2;




    ////////////////TEACHER
    String TEACHER_TABLE_NAME = "Teacher";

    String TEACHER_COLUMN_EMAIL ="email_teacher";
    String TEACHER_COLUMN_PASSWORD = "password";

    String CREATE_TABLE_TEACHER = "CREATE TABLE " + TEACHER_TABLE_NAME
            + " ( " + TEACHER_COLUMN_EMAIL + " TEXT PRIMARY KEY, "+
                      TEACHER_COLUMN_PASSWORD + " TEXT );";

    String DROP_TABLE_TEACHER = "DROP TABLE IF EXISTS " + TEACHER_TABLE_NAME +";";

    String INSERT_TEACHER ="insert into "+ TEACHER_TABLE_NAME + " (" + TEACHER_COLUMN_EMAIL+", " + TEACHER_COLUMN_PASSWORD + ") values(?,?)";
    String QUERRY_FOR_LOGIN = "SELECT * FROM "+ TEACHER_TABLE_NAME+ " WHERE " + TEACHER_COLUMN_EMAIL + "=? AND "+ TEACHER_COLUMN_PASSWORD + "=?";

    /////////STUDENT
    String STUDENT_TABLE_NAME="Student";

    String STUDENT_COLUMN_USERNAME="username";
    String STUDENT_COLUMN_CURRENT_AVATAR="currentAvatar";
    String STUDENT_COLUMN_GENDER="gender";
    String STUDENT_COLUMN_AGE="age";
    String STUDENT_COLUMN_SCORE="score";
    String STUDENT_COLUMN_PROFESSOR_EMAIL="professorEmail";

    String CREATE_TABLE_STUDENT=
            "CREATE TABLE "+STUDENT_TABLE_NAME+
            " ( "+STUDENT_COLUMN_USERNAME+ " TEXT PRIMARY KEY, "+
            STUDENT_COLUMN_CURRENT_AVATAR+ " BLOB, "+
            STUDENT_COLUMN_GENDER+ " TEXT, "+
            STUDENT_COLUMN_AGE+ " INTEGER, "+
            STUDENT_COLUMN_SCORE+ " REAL, "+
            STUDENT_COLUMN_PROFESSOR_EMAIL+ "TEXT, " +
            " FOREIGN KEY ( " + STUDENT_COLUMN_PROFESSOR_EMAIL +
            " ) REFERENCES "+TEACHER_TABLE_NAME+" ( " + TEACHER_COLUMN_EMAIL + " ));";

    String DROP_TABLE_STUDENT = "DROP TABLE IF EXISTS "+STUDENT_TABLE_NAME+";";


    ////////////////TESTRESULTS
    String TESTRESULTS_TABLE_NAME="TestResults";

    String TESTRESULTS_COLUMN_ID_TEST="id_test";
    String TESTRESULTS_COLUMN_DIFFICULTY="difficulty";
    String TESTRESULTS_COLUMN_CATEGORY="category";
    String TESTRESULTS_COLUMN_USERNAMESTUD="username";
    String TESTRESULTS_COLUMN_CORRECTANSWERS="correct_answers";
    String TESTRESULTS_COLUMN_SCORE="score";

    String CREATE_TABLE_TESTRESULTS= "CREATE TABLE " + TESTRESULTS_TABLE_NAME
            + " ( " + TESTRESULTS_COLUMN_ID_TEST + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TESTRESULTS_COLUMN_DIFFICULTY + " TEXT, " +
            TESTRESULTS_COLUMN_CATEGORY + " TEXT, " +
            TESTRESULTS_COLUMN_CORRECTANSWERS + " INTEGER, " +
            TESTRESULTS_COLUMN_SCORE + " REAL, "+
            TESTRESULTS_COLUMN_USERNAMESTUD + " TEXT, "+
            " FOREIGN KEY ( "+TESTRESULTS_COLUMN_USERNAMESTUD+
            ") REFERENCES "+STUDENT_TABLE_NAME+" ( "+STUDENT_COLUMN_USERNAME+" ));";

    String DROP_TABLE_TESTRESULTS ="DROP TABLE IF EXISTS " + TESTRESULTS_TABLE_NAME +";";


    ////////////TASKS

    String TASKS_TABLE_NAME="Tasks";

    String TASKS_COLUMN_ID_TASK="id_task";
    String TASKS_COLUMN_DATE="date_task";
    String TASKS_COLUMN_INFO="info_task";
    String TASKS_COLUMN_ID_STUDENT="username";

    String CREATE_TABLE_TASKS=" CREATE TABLE "+TASKS_TABLE_NAME+" ( "+
            TASKS_COLUMN_ID_TASK+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            TASKS_COLUMN_DATE+" TEXT, "+
            TASKS_COLUMN_INFO+" TEXT, "+
            TASKS_COLUMN_ID_STUDENT+" TEXT );";

    String DROP_TABLE_TASKS=" DROP TABLE IF EXISTS "+TASKS_TABLE_NAME+" ; ";

    ////////////////AVATARS
    String AVATAR_TABLE_NAME="Avatar";

    String AVATAR_COLUMN_ID_AVATAR="id_avatar";
    String AVATAR_COLUMN_NAME="name_avatar";
    String AVATAR_COLUMN_PRICE="price_avatar";
    String AVATAR_COLUMN_IMAGE="image_avatar";
    String AVATAR_COLUMN_APP_AVATAR="app_avatar";


    String CREATE_TABLE_AVATAR= "CREATE TABLE "+ AVATAR_TABLE_NAME
            +" ( "+ AVATAR_COLUMN_ID_AVATAR + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AVATAR_COLUMN_NAME+ " TEXT, "
            + AVATAR_COLUMN_PRICE+ " REAL, "
            + AVATAR_COLUMN_IMAGE+ " BLOB, "
            + AVATAR_COLUMN_APP_AVATAR + " TEXT );";

    String DROP_TABLE_AVATAR= " DROP TABLE IF EXISTS "+ AVATAR_TABLE_NAME+" ; ";
    String INSERT_AVATAR ="insert into "+ AVATAR_TABLE_NAME
            + " (" + AVATAR_COLUMN_NAME+", " + AVATAR_COLUMN_PRICE
            + ", "+ AVATAR_COLUMN_IMAGE+", "+ AVATAR_COLUMN_APP_AVATAR
            + " ) values(?,?,?,?)";
    String QUERY_FOR_AVATARS = "SELECT * FROM "+ AVATAR_TABLE_NAME+ " WHERE " + AVATAR_COLUMN_APP_AVATAR + "=? ;";





}
