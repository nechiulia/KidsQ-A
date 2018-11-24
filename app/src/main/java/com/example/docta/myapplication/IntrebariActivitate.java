package com.example.docta.myapplication;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.TextView;

import com.example.docta.myapplication.clase.HttpManager;
import com.example.docta.myapplication.clase.Intrebare;
import com.example.docta.myapplication.clase.SetIntrebari;
import com.example.docta.myapplication.clase.SetIntrebariParser;
import com.example.docta.myapplication.util.Constante;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class IntrebariActivitate extends AppCompatActivity {
    private TextView tvNrIntrebare;
    private int nrCurent=1;
    private RadioGroup rg_raspunsuri;
    private RadioButton rb_raspuns1;
    private RadioButton rb_raspuns2;
    private RadioButton rb_raspuns3;
    private Button btn_confirm;
    private TextView tvTextIntrebare;
    private ImageView imgIntrebare;
    private double punctaj=0;
    private int nrIntrebariCorecte=0;


    private static final String URL = "https://api.myjson.com/bins/mgoge";
    private SetIntrebari setIntrebari;
    private List<Intrebare> intrebariUsoare;
@TargetApi(21)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_intrebari);
        @SuppressLint("StaticFieldLeak") HttpManager manager = new HttpManager(){
            @Override
            protected void onPostExecute(String s) {

                try {
                    setIntrebari=SetIntrebariParser.fromJson(s);
                    intrebariUsoare=setIntrebari.getUsor();//=setIntrebari.getUsor();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),getString(R.string.jucam_parsare_eroare), Toast.LENGTH_LONG).show();
                }
             tvTextIntrebare.setText(intrebariUsoare.get(0).getTextIntrebare());
                rb_raspuns1.setText(intrebariUsoare.get(0).getRaspunsuri().get(0).getTextRaspuns());
       // rb_raspuns1.setChecked(intrebariUsoare.get(0).getRaspunsuri().get(0).isCorect());
        rb_raspuns2.setText(intrebariUsoare.get(0).getRaspunsuri().get(1).getTextRaspuns());
        //rb_raspuns2.setChecked(intrebariUsoare.get(0).getRaspunsuri().get(1).isCorect());
        rb_raspuns3.setText(intrebariUsoare.get(0).getRaspunsuri().get(2).getTextRaspuns());
        //rb_raspuns3.setChecked(intrebariUsoare.get(0).getRaspunsuri().get(2).isCorect());

        imgIntrebare.setImageDrawable(getDrawable(R.drawable.intrebaremere));
          //      Toast.makeText(getApplicationContext(),intrebariUsoare.toString(),Toast.LENGTH_LONG).show();

            }
        };
        manager.execute(URL);
        initComponents();
    }


    private View.OnClickListener confirmRaspuns(){
        return new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (isValid()) {
                    RadioButton rbCorect;
                    for(int i=0;i<3;i++){
                        if(intrebariUsoare.get(nrCurent-1).getRaspunsuri().get(i).isCorect()) {
                            rbCorect=(RadioButton)rg_raspunsuri.getChildAt(i);
                            if(rbCorect.getId()==rg_raspunsuri.getCheckedRadioButtonId()){
                                Toast.makeText(getApplicationContext(),"Ai raspuns corect!", Toast.LENGTH_LONG).show();
                                punctaj+=intrebariUsoare.get(nrCurent-1).getOptiuni().getPunctaj();
                                nrIntrebariCorecte++;
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Ai raspuns gresit!", Toast.LENGTH_LONG).show();

                            }
                           // rbCorect.setTextColor(Color.GREEN);
                        }
                    }
                    /*if(intrebariUsoare.get(nrCurent-1).getRaspunsuri().get(0).isCorect())rb_raspuns1.setTextColor(Color.GREEN);
                    if(intrebariUsoare.get(nrCurent-1).getRaspunsuri().get(1).isCorect())rb_raspuns2.setTextColor(Color.GREEN);
                    if(intrebariUsoare.get(nrCurent-1).getRaspunsuri().get(2).isCorect())rb_raspuns3.setTextColor(Color.GREEN);*/
                   // rb_raspuns.setTextColor(Color.GREEN);

                    nrCurent++;
                    if(nrCurent==4){
                        Intent intent= new Intent(getApplicationContext(), RezultatActivitate.class);
                        intent.putExtra(Constante.PUNCTAJ_KEY, punctaj);
                        intent.putExtra(Constante.NR_INTREBARI_CORECTE, nrIntrebariCorecte);
                        startActivity(intent);
                    }
                    else {
                        tvTextIntrebare.setText(intrebariUsoare.get(nrCurent-1).getTextIntrebare());
                        rb_raspuns1.setText(intrebariUsoare.get(nrCurent-1).getRaspunsuri().get(0).getTextRaspuns());
                        rb_raspuns2.setText(intrebariUsoare.get(nrCurent-1).getRaspunsuri().get(1).getTextRaspuns());
                        rb_raspuns3.setText(intrebariUsoare.get(nrCurent-1).getRaspunsuri().get(2).getTextRaspuns());
                        tvNrIntrebare.setText(nrCurent + getString(R.string.intrebari_tv_nr_intrebari));
                        rg_raspunsuri.clearCheck();
                    }
                }
            }
        };

    }

    public boolean isValid(){
        if(rg_raspunsuri.getCheckedRadioButtonId()==-1){
            rb_raspuns3.setError(getString(R.string.intrebarea_zilei_raspuns_eroare));
            Toast.makeText(getApplicationContext(),getString(R.string.intrebarea_zilei_raspuns_eroare),Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            rb_raspuns3.setError(null);
            return true;
        }

    }
@TargetApi(21)
    private void initComponents(){
        tvNrIntrebare=findViewById(R.id.intrebari_tv_nr_intrebare);
        rg_raspunsuri = findViewById(R.id.intrebari_rg_raspunsuri);
        rb_raspuns1 = findViewById(R.id.intrebari_rb_raspuns1);
        rb_raspuns2 = findViewById(R.id.intrebari_rb_raspuns2);
        rb_raspuns3 = findViewById(R.id.intrebari_rb_raspuns3);
        btn_confirm = findViewById(R.id.intrebari_btn_confirm);
        btn_confirm.setOnClickListener(confirmRaspuns());
        tvTextIntrebare=findViewById(R.id.intrebari_tv_intrebare);
        imgIntrebare=findViewById(R.id.intrebari_iv_imagine);
        nrCurent=1;


        tvNrIntrebare.setText(nrCurent+getString(R.string.intrebari_tv_nr_intrebari));



    }


}
