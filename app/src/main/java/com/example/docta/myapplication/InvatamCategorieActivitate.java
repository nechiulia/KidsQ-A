package com.example.docta.myapplication;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class InvatamCategorieActivitate extends AppCompatActivity {
    private ImageButton imgBtnNext;
    private TextView tvNr;
    private ImageView imagineInfo;
    private TextView tvInfo;
    private Button btnInapoi;
    private static int nrCurent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_invatam_categorie);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initComponents();
    }

   private void initComponents(){
       imgBtnNext=findViewById(R.id.invatam_categ_imgBtn_inainte);
       tvNr=findViewById(R.id.invatam_categ_tv_nr_curent);
       tvInfo=findViewById(R.id.invatam_categ_tv_informatie);
       imagineInfo=findViewById(R.id.invatam_categ_imgBtn);
       btnInapoi=findViewById(R.id.invatam_categ_btn_inapoi);
       nrCurent=1;
       imgBtnNext.setOnClickListener(nextEvent());
       btnInapoi.setOnClickListener(backEvent());
   }

    @TargetApi(21)
   private View.OnClickListener nextEvent() {
       return new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               nrCurent++;
               if (nrCurent == 11) {
                   Toast.makeText(getApplicationContext(), getString(R.string.invatam_categorie_toast_felicitari), Toast.LENGTH_LONG).show();
                   finish();
               } else {
                   tvNr.setText(nrCurent + getString(R.string.test_nr_poza));
                   imagineInfo.setImageDrawable(getDrawable(R.drawable.imagine_de_informare2));
                   tvInfo.setText(getString(R.string.test_informare_next));
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
