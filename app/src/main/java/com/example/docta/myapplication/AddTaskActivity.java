package com.example.docta.myapplication;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import com.example.docta.myapplication.Classes.Tasks;
import com.example.docta.myapplication.util.Constants;

public class AddTaskActivity extends AppCompatActivity {

    Intent intent;
    private Button btn_add;
    private CalendarView cv_date;
    private TextInputEditText tid_info;
    private Long date;
    private String finalDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        if(savedInstanceState==null){
            String title = getString(R.string.Titlu_AdaugaActivitate);
            this.setTitle(title);
        }
        initComps();
        intent=getIntent();



        cv_date.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                date = cv_date.getDate();
                finalDate =dayOfMonth+"/"+(month+1)+"/"+year;
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()) {
                    String info = tid_info.getText().toString();
                    Tasks tasks = new Tasks(finalDate, info);

                    intent.putExtra(Constants.ADD_TASK_KEY, tasks);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });




    }
    private void initComps(){
        btn_add =findViewById(R.id.addtask_btn_add);
        cv_date =findViewById(R.id.addtask_cv_date);
        tid_info=findViewById(R.id.addtask_tid_info);
    }

    private boolean isValid(){
        if(tid_info.getText().toString().trim().isEmpty()){
            tid_info.setError(getString(R.string.sarcini_infoactivitate_eroare));
            return false;
        }
        return true;
    }

}
