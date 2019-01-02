package com.example.docta.myapplication.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docta.myapplication.Classes.Database.TestResultDAO;
import com.example.docta.myapplication.Classes.util.Constants;
import com.example.docta.myapplication.R;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Objects;

public class StudentStatistics extends AppCompatActivity {
        private TextView tv_title;
        private Spinner spn_categ;
        private CheckBox cb_Easy_Test;
        private CheckBox cb_Medium_Test;
        private CheckBox cb_Hard_Test;
        private Button btn_back;
        private TextView tv_category_test;
        private TextView tv_correct_answers;
        private TextView tv_tests_resolved;
        private TextView tv_avarage_efficiency;
        private ProgressBar pb_average;
        private Button btn_calculation;
        private ArrayList<String> list;
        private String[] array_dificulty = new String[5];
        private String user;
        private TestResultDAO testResultDAO;
        private SharedPreferences sharedPreferences;
        private String chosen_Category;
        private ImageButton img_files;
        private FloatingActionButton fabGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_statistics);
            if(savedInstanceState==null){
                String title = getString(R.string.student_sts_eficienta);
                this.setTitle(title);
            }
        initComp();

            btn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            cb_Easy_Test.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        array_dificulty[0] = Constants.DIFFICULTY_EASY_TEST;
                    }else {
                        array_dificulty[0] = null;
                    }
                }
            });

            cb_Medium_Test.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        array_dificulty[1] = Constants.DIFFICULTY_MEDIUM_TEST;
                    }else {
                        array_dificulty[1] = null;
                    }
                }
            });
            cb_Hard_Test.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        array_dificulty[2] = Constants.DIFFICULTY_HARD_TEST;
                    }else {
                        array_dificulty[2] = null;
                    }
                }
            });

            btn_calculation.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @SuppressLint({"SetTextI18n" })
                @Override
                public void onClick(View v) {
                    if(!cb_Easy_Test.isChecked() && !cb_Medium_Test.isChecked() && !cb_Hard_Test.isChecked()) {
                        Toast.makeText(getApplicationContext(),getString(R.string.student_statistics_neselectat),Toast.LENGTH_LONG).show();
                        pb_average.setProgress(0);
                        tv_avarage_efficiency.setText("%");
                        tv_correct_answers.setText(getString(R.string.student_statistics_raspCorecte) );
                        tv_tests_resolved.setText(getString(R.string.student_statistics_tRezolvate) );
                    }else {
                        chosen_Category = spn_categ.getSelectedItem().toString().toLowerCase();
                        if(chosen_Category.equals(Constants.CATEG_TOTAL)){
                            array_dificulty[3]=Constants.CATEG_SPEC;
                            array_dificulty[4]=Constants.CATEG_SPEC;
                        }
                        else{
                            array_dificulty[3]=null;
                            array_dificulty[4]=null;
                        }
                        testResultDAO.open();
                        list = testResultDAO.studentTestResult(user, array_dificulty, chosen_Category);
                        testResultDAO.close();
                        tv_correct_answers.setText(getString(R.string.student_sts_rCorecte) + list.get(0));
                        tv_tests_resolved.setText(getString(R.string.student_sts_tRez) + list.get(1));
                        pb_average.setProgress(Integer.parseInt(list.get(2)), true);
                        tv_avarage_efficiency.setText(list.get(2) + "%");

                    }
                }
            });


            img_files.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(StudentStatistics.this);
                    builder.setMessage(R.string.resultTestV_formatExtern2).
                            setTitle(getText(R.string.alert_dialog_files_title));
                    final EditText input = new EditText(getApplicationContext());
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    input.setHint(getString(R.string.save_files_filename));
                    input.setTextSize(15);

                    builder.setIcon(R.drawable.ic_save_black_24dp).
                            setView(input).
                            setPositiveButton(getString(R.string.alert_dialog_csvBtn), null).
                            setNeutralButton(getString(R.string.alert_dialog_cancel), null).
                            setNegativeButton(getString(R.string.alert_dialog_text_file), null);
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
                    verifyStoragePermissions(StudentStatistics.this);
                }
            });

        }
    private void initComp(){
        btn_back = findViewById(R.id.std_statistics_btn_back);
        tv_title = findViewById(R.id.std_statistics_tv_title);
        spn_categ = findViewById(R.id.std_statistics_spn_Categ);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.student_statistics_categories,R.layout.support_simple_spinner_dropdown_item);
        spn_categ.setAdapter(adapter);
        cb_Easy_Test = findViewById(R.id.std_Statistics_cb_Easy);
        cb_Medium_Test = findViewById(R.id.std_Statistics_cb_Medium);
        cb_Hard_Test = findViewById(R.id.std_Statistics_cb_Hard);
        tv_category_test = findViewById(R.id.std_statistics_tv_categ);
        tv_correct_answers = findViewById(R.id.std_statistics_tv_Raspunsuri);
        tv_tests_resolved = findViewById(R.id.std_statistics_tv_Teste);
        tv_avarage_efficiency = findViewById(R.id.std_statistics_tv_Medie);
        pb_average = findViewById(R.id.std_Statistics_progressB_medie);
        pb_average.getProgressDrawable().setColorFilter(Color.WHITE,PorterDuff.Mode.SRC_IN);
        btn_calculation = findViewById(R.id.std_Statistics_btn_Calculeaza);
        sharedPreferences = getSharedPreferences(Constants.USERNAME_PREF,MODE_PRIVATE);
        img_files = findViewById(R.id.img_result_visualization_save_std);
        user = sharedPreferences.getString(Constants.USERNAME_KEY,null);
        testResultDAO = new TestResultDAO(this);
        fabGraph= findViewById(R.id.student_statistics_fab);
        fabGraph.setOnClickListener(graph());
    }

    private View.OnClickListener graph() {
        return  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), TestsGraph.class);
                startActivity(intent);
            }
        };
    }


    private void writeInTxtFile(EditText input, AlertDialog dialog){
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
            input.setError(getString(R.string.save_file_txt_edittext_error));
        }
    }

    private void txtWriter(AlertDialog dialog, String baseDirectory, String fileName) {
        String filePath = baseDirectory + File.separator + fileName;
        File file = new File(filePath);
        FileOutputStream fos;
        OutputStreamWriter osw;
        if(file.exists()){
            Toast.makeText(getApplicationContext(),getString(R.string.save_file_txt_exists),Toast.LENGTH_LONG).show();
        }
        else{
            try {
                fos = new FileOutputStream(file);
                osw = new OutputStreamWriter(fos);
                for(int i = 0; i<Constants.COLUMNS_NAME_RAPORT2.length; i++){
                    osw.append(Constants.COLUMNS_NAME_RAPORT2[i]).append(" | ");
                }
                osw.append("\n");
                if(list!=null) {
                    Writer append = osw.append(spn_categ.getSelectedItem().toString()).append("          ");
                    osw.append(list.get(0)).append("                ");
                    osw.append(list.get(1)).append("                ");
                    osw.append(list.get(2)).append("                ");
                    Toast.makeText(getApplicationContext(),getString(R.string.save_file_name_file_saved,fileName),Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),getString(R.string.std_statistics_alert_null_list),Toast.LENGTH_LONG).show();
                }
                osw.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dialog.dismiss();
    }


    //csv File
    private void writeInCSVfile(EditText input,AlertDialog dialog) {
        String baseDirectory = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
        String fileName;
        if(input.getText().toString().contains(".csv") && !input.getText().toString().contains(" ")) {
            fileName = input.getText().toString();
            csvWriter(dialog, baseDirectory, fileName);
        }
        else if (!input.getText().toString().trim().isEmpty() && !input.getText().toString().matches("") && !input.getText().toString().contains(" ")){

            fileName = input.getText().toString()+".csv";
            csvWriter(dialog, baseDirectory, fileName);
        }
        else if (!input.getText().toString().contains(".csv") || input.getText().toString().matches(" ") || input.getText().toString().matches("")){
            input.setError(getString(R.string.save_file_csv_edittext_error));
        }
    }

    private void csvWriter(AlertDialog dialog,String baseDirectory, String fileName) {
        String filePath = baseDirectory + File.separator + fileName;
        File file = new File(filePath);
        CSVWriter writer;
        if(file.exists()){
            Toast.makeText(getApplicationContext(),getString(R.string.save_file_txt_exists),Toast.LENGTH_LONG).show();
        }
        else{
            try {
                writer = new CSVWriter(new FileWriter(filePath));
                writer.writeNext(Constants.COLUMNS_NAME_RAPORT3);
                if(list !=null) {
                    writer.writeNext(list.toArray(new String[0]));
                    Toast.makeText(getApplicationContext(),getString(R.string.save_file_name_file_saved,fileName),Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),getString(R.string.std_statistics_alert_null_list),Toast.LENGTH_LONG).show();
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dialog.dismiss();

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
