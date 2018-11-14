package com.example.docta.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class InvatamCategorieActivitate extends AppCompatActivity {
    private ImageButton imgBtnBack;
    private ImageButton imgBtnNext;
    private TextView tvNr;
    private ImageView imagineInfo;
    private TextView tvInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_invatam_categorie);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initComponents();
    }
   private void initComponents(){
       imgBtnBack=findViewById(R.id.invatam_categ_imgBtn_inapoi);
       imgBtnNext=findViewById(R.id.invatam_categ_imgBtn_inainte);
       tvNr=findViewById(R.id.invatam_categ_tv_nr_curent);
       tvInfo=findViewById(R.id.invatam_categ_tv_informatie);
       imagineInfo=findViewById(R.id.invatam_categ_imgBtn);
       if(tvNr.getText().toString().equals("1/10")){
           imgBtnBack.setVisibility(View.INVISIBLE);
       }
       else if (tvNr.getText().toString().equals("10/10")){
           imgBtnBack.setVisibility(View.VISIBLE);
           Toast.makeText(getApplicationContext(),getString(R.string.invatam_categorie_toast_felicitari), Toast.LENGTH_LONG);
       }
       else {
           imgBtnBack.setVisibility(View.VISIBLE);
       }
       imgBtnNext.setOnClickListener(nextEvent());
       imgBtnBack.setOnClickListener(backEvent());
   }
   private View.OnClickListener nextEvent() {
       return new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //String nrCurent = tvNr.getText().toString().substring(0, 1);
               tvNr.setText(getString(R.string.test_nr_poza));
               //imagineInfo.setImageResource(R.drawable.imagine_de_informare2);
               tvInfo.setText(getString(R.string.test_informare_next) );
               if(tvNr.getText().toString().equals("1/10")){
                   imgBtnBack.setVisibility(View.INVISIBLE);
               }
               else if (tvNr.getText().toString().equals("10/10")){
                   imgBtnBack.setVisibility(View.VISIBLE);
                   finish();

               }
               else {
                   imgBtnBack.setVisibility(View.VISIBLE);
               }
           }
       };

   }
    private View.OnClickListener backEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvNr.setText(getString(R.string.test_nr_poza_back));
                if(tvNr.getText().toString().equals("1/10")){
                    imgBtnBack.setVisibility(View.INVISIBLE);
                }
                else if (tvNr.getText().toString().equals("10/10")){
                    imgBtnBack.setVisibility(View.VISIBLE);
                    finish();

                }
                else {
                    imgBtnBack.setVisibility(View.VISIBLE);
                }

                //imagineInfo.setImageDrawable(Drawable.createFromPath("imagine_de_informare2"));
                tvInfo.setText(getString(R.string.test_informare_back) );

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
