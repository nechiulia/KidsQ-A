package com.example.docta.myapplication.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docta.myapplication.Classes.Database.AssociativeDAO;
import com.example.docta.myapplication.Classes.Database.StudentDAO;
import com.example.docta.myapplication.R;
import com.example.docta.myapplication.Classes.util.Constants;

public class StudentSettingsActivity extends AppCompatActivity {

    private Button btn_back;
    private Button btn_design;
    private Button btn_logout;
    private Button btn_changename;
    Intent intent;
    private Spinner spn_difficulty;
    private ImageView avatar;
    private StudentDAO studentDao;
    private AssociativeDAO associativeDAO;
    private String user;
   private TextView nume_student;
   private Button btn_stergere;

    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferencesUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_students);
        initComps();
        String nume = getIntent().getStringExtra(Constants.NAME_KEY);
        nume_student.setText(nume);
        if(savedInstanceState==null){
            String title = getString(R.string.Titlu_SetariCont);
            this.setTitle(title);
        }


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.DIFFICULTY_PREF, spn_difficulty.getSelectedItem().toString());
                int selectedPosition = spn_difficulty.getSelectedItemPosition();
                editor.putInt(Constants.SPINNER_POSITION, selectedPosition);
                editor.apply();
                intent=new Intent(getApplicationContext(), HomePageActivity.class);
                intent.putExtra(Constants.DOWNLOAD_DONE,true);

                String nume = getIntent().getStringExtra(Constants.NAME_KEY);
                if(nume.equals(nume_student.getText().toString())) {
                    intent.putExtra(Constants.NAME_KEY, nume);
                }else{
                    intent.putExtra(Constants.NAME_KEY, nume_student.getText().toString());
                    editor.putString(Constants.USERNAME_KEY,nume_student.getText().toString());
                    editor.apply();
                }
                startActivity(intent);
                finish();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(), LoginPageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_design.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(), SetDesignActivity.class);
                startActivity(intent);
                finish();
            }
        });




    }

    private void initComps(){
        spn_difficulty =findViewById(R.id.settings_spn_difficulty);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getApplicationContext(), R.array.setari_spn_dificultati, R.layout.support_simple_spinner_dropdown_item);
        spn_difficulty.setAdapter(adapter);

        btn_back=findViewById(R.id.settings_btn_back);
        btn_design=findViewById(R.id.settings_btn_design);
        btn_logout=findViewById(R.id.settings_btn_logout);
        btn_stergere=findViewById(R.id.settings_student_btn_stergere);
        btn_changename=findViewById(R.id.settings_btn_changename);
        avatar=findViewById(R.id.settings_img_avatar);

        sharedPreferences= getSharedPreferences(Constants.USERNAME_PREF,MODE_PRIVATE);
        user= sharedPreferences.getString(Constants.USERNAME_KEY, getString(R.string.myavatars_default_user_name));
        studentDao=new StudentDAO(this);
        associativeDAO=new AssociativeDAO(this);
        studentDao.open();
        user = getIntent().getStringExtra(Constants.NAME_KEY);
        Bitmap btm=null;
        btm=BitmapFactory.decodeByteArray(studentDao.findMyAvatar(user),0,studentDao.findMyAvatar(user).length);
        avatar.setImageBitmap(Bitmap.createBitmap(btm));
        studentDao.close();

        nume_student=findViewById(R.id.settings_tv_name);

        sharedPreferences = getSharedPreferences(Constants.STUDENT_SETTINGS_PREF,MODE_PRIVATE);
        restoreDifficulty();



        btn_changename.setOnClickListener(changeName());
        btn_stergere.setOnClickListener(deleteStudent());

    }

    private View.OnClickListener deleteStudent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder= new AlertDialog.Builder(StudentSettingsActivity.this);
                builder.setTitle(getString(R.string.settings_student_stergere_title_))
                        .setMessage(getString(R.string.settings_student_alert_message))
                        .setPositiveButton(getString(R.string.settings_student_alert_yes), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                studentDao.open();
                                studentDao.deleteStudent(user);
                                studentDao.close();
                                associativeDAO.open();
                                associativeDAO.deleteUsername(user);
                                associativeDAO.close();
                                Toast.makeText(StudentSettingsActivity.this, getString(R.string.settings_student_delete_success), Toast.LENGTH_LONG).show();
                                intent=new Intent(getApplicationContext(), LoginPageActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton(getString(R.string.settings_student_alert_no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(StudentSettingsActivity.this,getString(R.string.settings_student_delete_err), Toast.LENGTH_LONG).show();
                            }
                        });
                builder.create().show();



            }

        };
    }

    private View.OnClickListener changeName() {
        return new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), UpdateStudentNameActivity.class);
            intent.putExtra(Constants.CHANGE_STUDENT_NAME_KEY, nume_student.toString());
            startActivityForResult(intent, Constants.UPDATE_NAME_REQUEST_CODE);
        }
        };
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Constants.UPDATE_NAME_REQUEST_CODE && data != null) {
            String newName = data.getStringExtra(Constants.SET_STUDENT_NAME_KEY);
            nume_student.setText(newName);
            studentDao.open();
            studentDao.updateStudentName(newName, user);
            studentDao.close();

            associativeDAO.open();
            associativeDAO.updateUsername(newName, user);
            associativeDAO.close();

        }


    }


    private void restoreDifficulty(){
        spn_difficulty.setSelection(sharedPreferences.getInt(Constants.SPINNER_POSITION,0));
    }
}
