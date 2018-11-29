package com.example.docta.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class FeedbackActivity extends AppCompatActivity {
    private EditText et_parere;
    private ProgressBar pb_medie;
    private RatingBar rb_review;
    private Button btn_trimite;
    private TextView tv_medie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(savedInstanceState==null){
            String titlu = getString(R.string.Titlu_Pareri);
            this.setTitle(titlu);
        }
        initComps();
    }

    private void initComps(){
        et_parere = findViewById(R.id.pareri_et_parere);
        pb_medie = findViewById(R.id.pareri_progressB);
        rb_review = findViewById(R.id.pareri_ratingB);
        btn_trimite = findViewById(R.id.pareri_btn_salveaza);
        tv_medie = findViewById(R.id.pareri_tv_medie);
        tv_medie.setText(null);
        btn_trimite.setOnClickListener(salveaza());
    }

    private View.OnClickListener salveaza(){
        return new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(isValid() && rb_review.getProgress() > 0){

                    Toast.makeText(getApplicationContext(),R.string.parere_multimum,Toast.LENGTH_LONG).show();
                    pb_medie.setProgress((int)rb_review.getRating());
                    tv_medie.setText(String.valueOf(pb_medie.getProgress()));
                }else{
                    Toast.makeText(getApplicationContext(),R.string.parere_ratingbar_eroare,Toast.LENGTH_LONG).show();
                    pb_medie.setProgress((int)rb_review.getRating());
                    tv_medie.setText(null);
                }
            }
        };
    }

    private boolean isValid(){
        if(rb_review.getProgress() == 0) {
            Toast.makeText(getApplicationContext(), R.string.parere_ratingbar_eroare, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
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
