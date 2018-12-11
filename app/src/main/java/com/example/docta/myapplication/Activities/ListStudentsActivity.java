package com.example.docta.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.docta.myapplication.Classes.Student;
import com.example.docta.myapplication.R;
import com.example.docta.myapplication.util.Constants;
import com.example.docta.myapplication.Classes.Adaptor.StudentAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListStudentsActivity extends AppCompatActivity {

    private Button btn_add_student;
     List<Student> students = new ArrayList<Student>();
    private ListView lv_students;
    BottomNavigationView bottomNavigationView;
    private Button btn_back_to_teacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_students);
        if(savedInstanceState==null){
            String title = getString(R.string.Titlu_ListaElevi);
            this.setTitle(title);
        }
        initComponents();

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()) {
                    case R.id.menu_setari:
                        intent = new Intent(getApplicationContext(),SettingsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_clasament:
                        intent = new Intent(getApplicationContext(),RankingActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_profil:
                        startActivity(new Intent(getApplicationContext(),HomePageActivity.class));
                        break;
                }
                return false;
            }
        });

        btn_add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), SignUpStudentActivity.class);
                startActivityForResult(intent, Constants.ADD_STUDENT_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.ADD_STUDENT_REQUEST_CODE
                && resultCode == RESULT_OK && data != null ) {

            Student student = data.getParcelableExtra(Constants.ADD_STUDENT_KEY);
            if(student !=null){
                students.add(student);
                StudentAdapter adapter = (StudentAdapter) lv_students.getAdapter();
                adapter.notifyDataSetChanged();
            }

        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.lea_transfer_mesaj), Toast.LENGTH_LONG).show();
        }

    }

    private void initComponents()
    { bottomNavigationView = findViewById(R.id.bottomNavigationView);
        btn_add_student =findViewById(R.id.liststudents_btn_addstudent);
        btn_back_to_teacher =findViewById(R.id.home_btn_backtoteacher);
        lv_students = findViewById(R.id.liststudents_lv_liststudents);

        StudentAdapter adapter = new StudentAdapter(getApplicationContext(), R.layout.lv_students_row, students, getLayoutInflater());
        lv_students.setAdapter(adapter);
    }
}
