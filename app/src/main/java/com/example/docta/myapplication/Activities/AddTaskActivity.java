package com.example.docta.myapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.docta.myapplication.Classes.Database.TasksDAO;
import com.example.docta.myapplication.Classes.util.Tasks;
import com.example.docta.myapplication.R;
import com.example.docta.myapplication.Classes.util.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.docta.myapplication.Classes.util.Constants.NAME_KEY;

public class AddTaskActivity extends AppCompatActivity {

    Intent intent;
    private Button btn_add;
    private CalendarView cv_date;
    private TextInputEditText tid_info;
    private Long date;
    private String finalDate;
    private TasksDAO tasksDAO;
    private String username;
    private SharedPreferences sharedPreferencesUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        if(savedInstanceState==null){
            String title = getString(R.string.Titlu_AdaugaActivitate);
            this.setTitle(title);
        }
        intent = getIntent();

        initComponents();

        cv_date.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                date = cv_date.getDate();
                finalDate = dayOfMonth+"/"+(month+1)+"/"+year;
            }
        });


    }
    private void initComponents(){
        btn_add = findViewById(R.id.addtask_btn_add);
        cv_date = findViewById(R.id.addtask_cv_date);
        tid_info = findViewById(R.id.addtask_tid_info);

        date = cv_date.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        finalDate = sdf.format(new Date(date));
        sharedPreferencesUser= getSharedPreferences(Constants.USERNAME_PREF,MODE_PRIVATE);
        username= sharedPreferencesUser.getString(Constants.USERNAME_KEY,getString(R.string.default_user_pref));
        if(intent.hasExtra(Constants.UPDATE_TASK_KEY)){
            Tasks task = intent.getParcelableExtra(Constants.UPDATE_TASK_KEY);
            if(task != null)
            {
                tid_info.setText(task.getInfo());

                String date = task.getDate();
                String parts[] = date.split("/");

                int day = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int year = Integer.parseInt(parts[2]);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, (month - 1));
                calendar.set(Calendar.DAY_OF_MONTH, day);

                long milliTime = calendar.getTimeInMillis();

                cv_date.setDate(milliTime, true, true);
                finalDate = day+"/"+month+"/"+year;
                btn_add.setText(getString(R.string.addtask_btn_save_hint));
            }
        }

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()) {
                    String info = tid_info.getText().toString();

                    if(intent.hasExtra(Constants.UPDATE_TASK_KEY)) {
                        Tasks taskToEdit = intent.getParcelableExtra(Constants.UPDATE_TASK_KEY);
                        if(taskToEdit != null)
                        {
                            long result = tasksDAO.update(taskToEdit.getId(),finalDate,info);

                            if (result == 1) {
                                Toast.makeText(AddTaskActivity.this,
                                        "Sarcina a fost actualizata !",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(AddTaskActivity.this,
                                        "Sarcina nu s-a actualizat !",
                                        Toast.LENGTH_LONG).show();
                            }

                            taskToEdit.setDate(finalDate);
                            taskToEdit.setInfo(info);

                            intent.putExtra(Constants.UPDATE_TASK_KEY, taskToEdit);
                        }

                    }
                    else {
                        long addedTaskId = tasksDAO.insertTasksInDatabase(finalDate, info, username);

                        Tasks newTask = new Tasks(addedTaskId, finalDate, info, username);

                        intent.putExtra(Constants.ADD_TASK_KEY, newTask);
                    }

                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        tasksDAO = new TasksDAO(getApplicationContext());
        tasksDAO.open();

    }

    private boolean isValid(){
        if(tid_info.getText().toString().trim().isEmpty()){
            tid_info.setError(getString(R.string.sarcini_infoactivitate_eroare));
            return false;
        }
        return true;
    }

}
