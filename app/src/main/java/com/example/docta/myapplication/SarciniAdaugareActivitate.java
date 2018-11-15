package com.example.docta.myapplication;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.docta.myapplication.clase.Sarcini;
import com.example.docta.myapplication.util.Constante;

public class SarciniAdaugareActivitate extends AppCompatActivity {

    Intent intent;
    private Button btn_adaugare;
    private CalendarView cv_data;
    private TextInputEditText tid_info;
    private Long date;
    private String datafinal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_adaugare_sarcini);

        intent=getIntent();
        btn_adaugare=findViewById(R.id.as_btn_adaugare);
        cv_data=findViewById(R.id.as_cv_data);
        tid_info=findViewById(R.id.as_tid_info);


        cv_data.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                date = cv_data.getDate();
                Toast.makeText(view.getContext(), "Year=" + year + " Month=" + (month+1) + " Day=" + dayOfMonth, Toast.LENGTH_LONG).show();
                datafinal=dayOfMonth+"/"+(month+1)+"/"+year;


            }
        });

        btn_adaugare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String info=tid_info.getText().toString();
                Sarcini sarcini= new Sarcini(datafinal,info);

                intent.putExtra(Constante.ADAUGA_SAA_KEY,sarcini);
                setResult(RESULT_OK,intent);
                finish();

            }
        });




    }
}
