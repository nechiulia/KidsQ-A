package com.example.docta.myapplication.Classes.util;

public interface Constants {
    int ADD_STUDENT_REQUEST_CODE = 101;
    String ADD_STUDENT_KEY = "adaugareElev";
    String STATUT_KEY="statutKey";
    String CONT_STATUT_PREF="ProfesorSauElev";
    String USER_STATUT_PREF ="utilizator";
    String NAME_KEY ="numeAvatar";
    String USERNAME_PREF= "user_pref";
    String USERNAME_KEY = "key_username";
 //   String PAROLA_KEY ="PAROLA_KEY";

    int ADD_TASK_REQUEST_CODE =102;
    int UPDATE_TASK_REQUEST_CODE=105;
    String ADD_TASK_KEY ="adaugareSarcina";
    String UPDATE_TASK_KEY="modificareSarcina";
    String PASSWORD_PROF_PREF ="tineMinteParola";
    String PASSWORD_PREF ="ParolaVeche";
    String EMAIL_PREF="Email";

    String CATEG_PREF = "categPref";
    String GET_CATEG = "categGet";

    String STUDENT_SETTINGS_PREF = "setariPref";
    String DIFFICULTY_PREF = "dificultatePref";
    String SPINNER_POSITION ="SETARI_ELEV_POZITIE_SPINNER";
    String SPINNER_DIFICULTY= "Spiner_Dif";
    String DAILY_TEST ="TestulZilei";
 //   String VALIDARE_TESTE_PREF="validare";

    String DIFFICULTY_EASY_TEST ="Usor";
    String DIFFICULTY_MEDIUM_TEST ="Mediu";
    String DIFFICULTY_HARD_TEST ="Greu";

    String SCORE_KEY ="SCORE_KEY";
    String QUESTIONS_LIST_KEY ="Transmitere lista";
    String QUESTIONS_SET_KEY ="Set intrebari";
    String DAILY_QUESTION_KEY ="Intrebarea zilei";
    String DIFFERENT_IMAGE_KEY="Imagini diferite";
    String NO_CORECT_ANSWERS = "NR_Intrebari";
    String CATEGORY_MATH ="matematica";
    String CATEGORY_ANIMALS ="animale";
    String CATEGORY_LETTERS ="litere";
    String CATEGORY_FRUITS ="fructe si legume";
    String CATEGORY_LIFE ="viata-de-zi-cu-zi";
    String TEST_OF_THE_DAY ="Testul zilei";
    String QUESTION_OF_THE_DAY="Intrebarea zilei";

    String DOWNLOAD_DONE ="validar descarca json";
    String URL_JSON_TESTS ="https://api.myjson.com/bins/1dmmju";
    String URL_JSON_ACCOUNTS = "https://api.myjson.com/bins/b98ou";
    String URL_JSON_AVATARS="https://api.myjson.com/bins/6armc";


    String EMPTY_CHAR = " ";

    String AVATAR_UPLOAD_CHECK_PREF="checkIfWasInserted";
    String AVATAR_BOOL_CHECK_KEY="boolForCheck";
    int UPDATE_AVATAR_REQUEST_CODE = 103;
    String SET_NAME_KEY="name changed save";
    int UPLOAD_IMAGE_REQUEST_CODE= 104;
    String USER_AVATAR_KEY="user avatar's list";
    String CHANGE_NAME_KEY="change name key";


    String CHANGE_STUDENT_NAME_KEY ="change stud name key" ;
    int UPDATE_NAME_REQUEST_CODE = 104;
    String SET_STUDENT_NAME_KEY = "student name changed";
    String[] COLUMNS_NAME_CSV=new String[]{"NR_CRT","NAME","PUNCTAJ TOTAL","NR INTREBARI","TEST USOR","TEST MEDIU","TEST GREU"};


}
