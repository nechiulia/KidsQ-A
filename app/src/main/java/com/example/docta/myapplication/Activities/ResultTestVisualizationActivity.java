package com.example.docta.myapplication.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.docta.myapplication.Classes.Adaptor.ExpandableListTestResultAdapter;
import com.example.docta.myapplication.Classes.Database.StudentDAO;
import com.example.docta.myapplication.Classes.Database.TestResultDAO;
import com.example.docta.myapplication.Classes.util.Constants;
import com.example.docta.myapplication.Classes.util.Student;
import com.example.docta.myapplication.Classes.util.TestResult;
import com.example.docta.myapplication.R;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;



public class ResultTestVisualizationActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private ExpandableListView exp_list_Stud;

    private ArrayList<Student> students;
    //private HashMap<Student,ArrayList<TestResult>> testsResults;
    private HashMap<Student,TestResult> testsResults1;
    private HashMap<Integer,String[]> hashMapCsv = new HashMap<>();

    private String[] strings;
    private String[] COLUMNS_NAME_CSV=new String[]{"NR_CRT","NAME","PUNCTAJ TOTAL","NR INTREBARI","TEST USOR","TEST MEDIU","TEST GREU"};
    private StudentDAO studentDAO;
    private TestResultDAO testResultDAO;
    private SharedPreferences sharedPreferences;

    private ImageButton img_saveCsvOrFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_test_visualization);


        verifyStoragePermissions(ResultTestVisualizationActivity.this);
        initComponents();
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()) {
                    case R.id.menu_setari:
                        intent = new Intent(getApplicationContext(),SettingsActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.menu_clasament:
                        intent = new Intent(getApplicationContext(),ListStudentsActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.menu_profil:
                        startActivity(new Intent(getApplicationContext(),HomePageActivity.class));
                        finish();
                        break;
                }
                return false;
            }
        });


        ExpandableListTestResultAdapter adapter = new ExpandableListTestResultAdapter(this,students,testsResults1);
        exp_list_Stud.setAdapter(adapter);

        exp_list_Stud.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition, long id) {
                        return false;
                    }});

        img_saveCsvOrFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ResultTestVisualizationActivity.this);
                builder.setMessage("Doriti sa salvati rezultatele elevilor intr-un format extern?").
                        setTitle("Salvare rezultate");
                final EditText input = new EditText(getApplicationContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setHint("Nume Fisier");
                input.setTextSize(15);

                builder.setIcon(R.drawable.ic_save_black_24dp).setView(input).
                        setPositiveButton("CSV File", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String baseDirectory = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
                                String fileName = null;
                                if(input.getText().toString().contains(".csv") && !input.getText().toString().contains(" ")
                                        && input.getText().toString() !=null) {
                                    fileName = input.getText().toString();
                                }
                                else if (!input.getText().toString().contains(" ") && input.getText().toString() !=null){
                                    fileName = input.getText().toString()+".csv";
                                }
                                else if (!input.getText().toString().contains(".csv")){
                                    input.setError("Extensie incompatibila\n Exemplu: fisier.csv sau fisier");
                                }
                                String filePath = baseDirectory + File.separator + fileName;
                                File file = new File(filePath);
                                CSVWriter writer = null;
                                if(file.exists() && !file.isDirectory()){
                                    try {
                                        FileWriter fileWriter = new FileWriter(filePath,true);
                                        writer = new CSVWriter(fileWriter);
                                        writer.writeNext(COLUMNS_NAME_CSV);
                                        writer.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else{
                                    try {
                                        writer = new CSVWriter(new FileWriter(filePath));
                                        //FileWriter fileWriter = new FileWriter(filePath,true);
                                        //writer = new CSVWriter(fileWriter);
                                        writer.writeNext(COLUMNS_NAME_CSV);
                                        writer.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                Toast.makeText(getApplicationContext(),"Fisierul "+fileName+" salvat cu succes!",Toast.LENGTH_LONG).show();


                                //strings = initilizedForFile(students,testsResults1);
                                //String[] array = (String[]) strings.toArray();
//                                assert writer != null;
//                                for(int i = 0; i < students.size();i++){
//                                    writer.writeNext(hashMapCsv.get(i));
//                                }

                            }
                        }).
                        setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).
                        setNegativeButton("Txt File", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.create().show();
            }
        });


    }

    private void initComponents(){
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        sharedPreferences = getSharedPreferences(Constants.PASSWORD_PROF_PREF,MODE_PRIVATE);
        String email = sharedPreferences.getString(Constants.EMAIL_PREF,null);
        exp_list_Stud = findViewById(R.id.exp_lv_result_test_stud);
        img_saveCsvOrFile = findViewById(R.id.img_result_visualization_save);
        studentDAO = new StudentDAO(this);
        testResultDAO = new TestResultDAO(this);
        studentDAO.open();
        students = studentDAO.findAllMyStudents(email);
        studentDAO.close();

        testResultDAO.open();
       // testsResults = testResultDAO.findMyStudentTests(students);
        testsResults1 = testResultDAO.FindThemAll(students);
        testResultDAO.close();

        hashMapCsv = initilizedForFile(students,testsResults1);
        //strings = initilizedForFile(students,testsResults1);
    }

//    private ArrayList<String> initilizedForFile(ArrayList<Student> students, HashMap<Student,TestResult> testResultHashMap){
//
//        for (int i = 0 ;i<students.size();i++){
//            strings.add(String.valueOf(i));
//            strings.add(students.get(i).getUsername());
//            strings.add(String.valueOf(testResultHashMap.get(students.get(i)).getScore()));
//            strings.add(String.valueOf(testResultHashMap.get(students.get(i)).getNoCorrectAnswers()));
//            strings.add(String.valueOf(testResultHashMap.get(students.get(i)).getDif_tests().get(0)));
//            strings.add(String.valueOf(testResultHashMap.get(students.get(i)).getDif_tests().get(1)));
//            strings.add(String.valueOf(testResultHashMap.get(students.get(i)).getDif_tests().get(2)));
//        }
//        return strings;
//    }

    private HashMap<Integer,String[]> initilizedForFile(ArrayList<Student> students, HashMap<Student,TestResult> testResultHashMap){

        for (int i = 0 ;i<students.size();i++){
            strings = new String[]{String.valueOf(i+1),
                    students.get(i).getUsername(),
                    String.valueOf(testResultHashMap.get(students.get(i)).getScore()),
                    String.valueOf(testResultHashMap.get(students.get(i)).getNoCorrectAnswers()),
                    String.valueOf(testResultHashMap.get(students.get(i)).getDif_tests().get(0)),
                    String.valueOf(testResultHashMap.get(students.get(i)).getDif_tests().get(1)),
                    String.valueOf(testResultHashMap.get(students.get(i)).getDif_tests().get(2))};
//            strings.add(String.valueOf(i+1));
//            strings.add(students.get(i).getUsername());
//            strings.add(String.valueOf(testResultHashMap.get(students.get(i)).getScore()));
//            strings.add(String.valueOf(testResultHashMap.get(students.get(i)).getNoCorrectAnswers()));
//            strings.add(String.valueOf(testResultHashMap.get(students.get(i)).getDif_tests().get(0)));
//            strings.add(String.valueOf(testResultHashMap.get(students.get(i)).getDif_tests().get(1)));
//            strings.add(String.valueOf(testResultHashMap.get(students.get(i)).getDif_tests().get(2)));
            hashMapCsv.put(i+1,strings);
        }
        return hashMapCsv;
    }


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }


}
