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
import android.widget.Button;
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
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class ResultTestVisualizationActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private ExpandableListView exp_list_Stud;

    private ArrayList<Student> students;
    private HashMap<Student,TestResult> testsResults1;
    private HashMap<Integer,String[]> hashMapCsv = new HashMap<>();

    private String[] strings;
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

                builder.setIcon(R.drawable.ic_save_black_24dp).
                        setView(input).
                        setPositiveButton("CSV File", null).
                        setNeutralButton("Cancel", null).
                        setNegativeButton("Txt File", null);
               // builder.create();

                final AlertDialog alert = builder.create();
                alert.setCancelable(false);
                alert.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button positiveBtn = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                        positiveBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                writeInCSVfile(input,alert);
                            }
                        });

                        Button negativeBtn = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
                        negativeBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                writeInTxtFile(input,alert);
                            }
                        });

                        Button neutralBtn = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_NEUTRAL);
                        neutralBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alert.dismiss();
                            }
                        });
                    }
                });
                alert.show();
            }
        });


    }

    //txt file
    private void writeInTxtFile(EditText input,AlertDialog dialog){
        String baseDirectory = Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileName;
        if(input.getText().toString().contains(".txt") && !input.getText().toString().contains(" ")) {
            fileName = input.getText().toString();
            txtWriter(dialog, baseDirectory, fileName);
        }
        else if (!input.getText().toString().trim().isEmpty() && !input.getText().toString().matches("") && !input.getText().toString().contains(" ")){
            fileName = input.getText().toString()+".txt";
            txtWriter(dialog, baseDirectory, fileName);
        }
        else if (!input.getText().toString().contains(".txt") || input.getText().toString().matches(" ") || input.getText().toString().matches("")){
            input.setError("Extensie incompatibila\nExemplu: fisier.txt sau fisier");
        }
    }

    private void txtWriter(AlertDialog dialog, String baseDirectory, String fileName) {
        String filePath = baseDirectory + File.separator + fileName;
        File file = new File(filePath);
        FileOutputStream fos;
        OutputStreamWriter osw;
        if(file.exists()){
            Toast.makeText(getApplicationContext(),"Fisierul exista!",Toast.LENGTH_LONG).show();
        }
        else{
            try {
                fos = new FileOutputStream(file);
                osw = new OutputStreamWriter(fos);
                for(int i = 0; i<Constants.COLUMNS_NAME_CSV.length; i++){
                    osw.append(Constants.COLUMNS_NAME_CSV[i]).append(" | ");
                }
                for(int i = 0; i< students.size();i++){
                    osw.append("\n");
                    for(int j = 0; j < Objects.requireNonNull(hashMapCsv.get(i)).length; j++) {
                        osw.append(Objects.requireNonNull(hashMapCsv.get(i))[j]).append("    |     ");
                    }
                }
                osw.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(),"Fisierul "+fileName+" salvat cu succes!",Toast.LENGTH_LONG).show();
        }
        dialog.dismiss();
    }


    //csv File
    private void writeInCSVfile(EditText input,AlertDialog dialog) {
        String baseDirectory = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
        String fileName;
        if(input.getText().toString().contains(".txt") && !input.getText().toString().contains(" ")) {
            fileName = input.getText().toString();
            csvWriter(dialog, baseDirectory, fileName);
        }
        else if (!input.getText().toString().trim().isEmpty() && !input.getText().toString().matches("") && !input.getText().toString().contains(" ")){

            fileName = input.getText().toString()+".txt";
            csvWriter(dialog, baseDirectory, fileName);
        }
        else if (!input.getText().toString().contains(".txt") || input.getText().toString().matches(" ") || input.getText().toString().matches("")){
            input.setError("Extensie incompatibila\nExemplu: fisier.txt sau fisier");
        }
    }

    private void csvWriter(AlertDialog dialog,String baseDirectory, String fileName) {
        String filePath = baseDirectory + File.separator + fileName;
        File file = new File(filePath);
        CSVWriter writer;
        if(file.exists()){
            Toast.makeText(getApplicationContext(),"Fisierul exista!",Toast.LENGTH_LONG).show();
        }
        else{
            try {
                writer = new CSVWriter(new FileWriter(filePath));
                writer.writeNext(Constants.COLUMNS_NAME_CSV);
                for(int i =0 ;i <students.size();i++){
                    writer.writeNext(hashMapCsv.get(i));
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(),"Fisierul "+fileName+" salvat cu succes!",Toast.LENGTH_LONG).show();
        }
        dialog.dismiss();

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
        testsResults1 = testResultDAO.FindThemAll(students);
        testResultDAO.close();

        hashMapCsv = initilizedForFile(students,testsResults1);
    }


    private HashMap<Integer,String[]> initilizedForFile(ArrayList<Student> students, HashMap<Student,TestResult> testResultHashMap){

        for (int i = 0 ;i<students.size();i++){
            strings = new String[]{String.valueOf(i+1),
                    students.get(i).getUsername(),
                    String.valueOf(Objects.requireNonNull(testResultHashMap.get(students.get(i))).getScore()),
                    String.valueOf(Objects.requireNonNull(testResultHashMap.get(students.get(i))).getNoCorrectAnswers()),
                    String.valueOf(Objects.requireNonNull(testResultHashMap.get(students.get(i))).getDif_tests().get(0)),
                    String.valueOf(Objects.requireNonNull(testResultHashMap.get(students.get(i))).getDif_tests().get(1)),
                    String.valueOf(Objects.requireNonNull(testResultHashMap.get(students.get(i))).getDif_tests().get(2))};
            hashMapCsv.put(i,strings);
        }
        return hashMapCsv;
    }


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
          //  Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
