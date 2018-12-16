package com.example.docta.myapplication.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.docta.myapplication.Classes.Database.TasksDAO;
import com.example.docta.myapplication.Classes.util.Tasks;
import com.example.docta.myapplication.R;
import com.example.docta.myapplication.Classes.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class   TasksActivity extends AppCompatActivity {

    private ListView lv_tasks;
    private FloatingActionButton fab;
    private List<Tasks> tasks = new ArrayList<>();
    private TasksDAO tasksDAO;
    private   ArrayAdapter<Tasks> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        if (savedInstanceState == null) {
            String title = getString(R.string.Titlu_Sarcini);
            this.setTitle(title);
        }
        initComponents();

        String nume = getIntent().getStringExtra(Constants.NAME_KEY);

        Cursor c = tasksDAO.selectTasksUser(nume);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String date = c.getString(c.getColumnIndex("date_task"));
            String info = c.getString(c.getColumnIndex("info_task"));
            //String username = c.getString(c.getColumnIndex("username"));
            tasks.add(new Tasks(date, info));
        }

        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, tasks);
        lv_tasks.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
                //String nume= getIntent().getStringExtra(Constants.NAME_KEY);
                intent.putExtra(Constants.NAME_KEY, nume);
                startActivityForResult(intent, Constants.ADD_TASK_REQUEST_CODE);
            }
        });
       /* new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                    ;
            }
        }).attachToRecyclerView(recyclerView);*/

    }

    private void initComponents() {
        lv_tasks = findViewById(R.id.tasks_lv_task);
        fab = findViewById(R.id.tasks_fab_add);

        tasksDAO = new TasksDAO(getApplicationContext());
        tasksDAO.open();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.ADD_TASK_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Tasks tasks = data.getParcelableExtra(Constants.ADD_TASK_KEY);
            this.tasks.add(tasks);
            ArrayAdapter<Tasks> adapter = (ArrayAdapter<Tasks>) lv_tasks.getAdapter();
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_delete_tasks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        tasksDAO.open();
        String nume= getIntent().getStringExtra(Constants.NAME_KEY);
        switch (item.getItemId()) {
            case R.id.menu_deletetasks:
                tasksDAO.deleteAllTasks(nume);
                tasksDAO.close();
                tasks.removeAll(tasks);
                adapter.notifyDataSetChanged();
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }
}
