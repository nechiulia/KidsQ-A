package com.example.docta.myapplication.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.docta.myapplication.Classes.util.Tasks;
import com.example.docta.myapplication.R;
import com.example.docta.myapplication.Classes.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class TasksActivity extends AppCompatActivity {

    private ListView lv_tasks;
    private FloatingActionButton fab;
    private List<Tasks> tasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        if(savedInstanceState==null){
            String title = getString(R.string.Titlu_Sarcini);
            this.setTitle(title);
        }
        lv_tasks =findViewById(R.id.tasks_lv_task);
        fab=findViewById(R.id.tasks_fab_add);

        ArrayAdapter<Tasks> adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1, tasks);
        lv_tasks.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AddTaskActivity.class);
                startActivityForResult(intent, Constants.ADD_TASK_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==Constants.ADD_TASK_REQUEST_CODE && resultCode==RESULT_OK  && data!=null)
        {
            Tasks tasks =data.getParcelableExtra(Constants.ADD_TASK_KEY);

            if (tasks !=null)
            {
                this.tasks.add(tasks);
                ArrayAdapter<Tasks> adapter=(ArrayAdapter<Tasks>) lv_tasks.getAdapter();
                adapter.notifyDataSetChanged();
            }

            else
            {
                Toast.makeText(getApplicationContext(),getString(R.string.sa_txt_eroare), Toast.LENGTH_LONG).show();
            }

        }

    }
}
