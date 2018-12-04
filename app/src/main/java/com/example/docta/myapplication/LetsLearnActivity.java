package com.example.docta.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class LetsLearnActivity extends AppCompatActivity {
    private Button btn_animals;
    private Button btn_fruits;
    private Button btn_vegetables;
    private Button btn_vehicles;
    private Button btn_time;
    private Button btn_colors;
    private Button btn_numbers;
    private Button btn_letters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lets_learn);
        if(savedInstanceState==null){
            String title = getString(R.string.Titlu_SaInvatam);
            this.setTitle(title);
        }
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initComponents();
    }
private  void initComponents(){
        btn_animals =findViewById(R.id.letslearn_btn_animals);
        btn_numbers =findViewById(R.id.letslearn_btn_digits);
        btn_colors =findViewById(R.id.letslearn_btn_colors);
        btn_fruits =findViewById(R.id.letslearn_btn_fruits);
        btn_vegetables =findViewById(R.id.letslearn_btn_vegetables);
        btn_letters =findViewById(R.id.letslearn_btn_letters);
        btn_time =findViewById(R.id.letslearn_btn_time);
        btn_vehicles =findViewById(R.id.letslearn_btn_transport);

        btn_animals.setOnClickListener(openCategory());
        btn_colors.setOnClickListener(openCategory());
        btn_vehicles.setOnClickListener(openCategory());
        btn_time.setOnClickListener(openCategory());
        btn_numbers.setOnClickListener(openCategory());
        btn_fruits.setOnClickListener(openCategory());
        btn_vegetables.setOnClickListener(openCategory());
        btn_letters.setOnClickListener(openCategory());
}

private View.OnClickListener openCategory(){
    return new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), LearningCategoryActivity.class);
            startActivity(intent);
        }
    };
}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id= item.getItemId();
        if(id==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
