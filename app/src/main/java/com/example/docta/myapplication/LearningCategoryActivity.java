package com.example.docta.myapplication;

import android.annotation.TargetApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LearningCategoryActivity extends AppCompatActivity {
    private ImageButton imgBtnNext;
    private TextView tv_no_question;
    private ImageView iv_info;
    private TextView tv_info;
    private Button btn_back;
    private static int current_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_category);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(savedInstanceState==null){
            String title = getString(R.string.Titlu_InvatamCategorie);
            this.setTitle(title);
        }
        initComponents();
    }

   private void initComponents(){
       imgBtnNext=findViewById(R.id.learningcategory_imgBtn_next);
       tv_no_question =findViewById(R.id.learningcategory_tv_currentnumber);
       tv_info =findViewById(R.id.learningcategory_tv_info);
       iv_info =findViewById(R.id.learningcategory_imgBtn_image);
       btn_back =findViewById(R.id.learningcategory_btn_back);
       current_no =1;
       imgBtnNext.setOnClickListener(nextEvent());
       btn_back.setOnClickListener(backEvent());
   }

    @TargetApi(21)
   private View.OnClickListener nextEvent() {
       return new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               current_no++;
               if (current_no == 11) {
                   Toast.makeText(getApplicationContext(), getString(R.string.invatam_categorie_toast_felicitari), Toast.LENGTH_LONG).show();
                   finish();
               } else {
                   tv_no_question.setText(current_no + getString(R.string.test_nr_poza));
                   tv_info.setText(getString(R.string.test_informare_next));
               }
           }
       };

   }

    private View.OnClickListener backEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            finish();
        }
    };}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id= item.getItemId();
        if(id==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
