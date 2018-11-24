package com.example.docta.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.docta.myapplication.clase.OptiuniIntrebare;
import com.example.docta.myapplication.clase.Raspuns;
import com.example.docta.myapplication.clase.SetIntrebari;
import com.example.docta.myapplication.clase.SetIntrebariParser;
import com.example.docta.myapplication.util.Constante;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class IntrebariActivitate extends AppCompatActivity {
    private TextView tvNrIntrebare;
    private int nrCurent=1;
    private RadioGroup rg_raspunsuri;

    private RadioButton rb_raspuns1;
    private RadioButton rb_raspuns2;
    private RadioButton rb_raspuns3;
    private Button btn_confirm;
    private ImageView imgV_imagine;
    private TextView tvIntrebare;
    private static final String URL = "https://api.myjson.com/bins/1avdxy";

    private SetIntrebari set;
    private List<Intrebare> listaIntrebariUsoare;
    private List<Intrebare> listaIntrebariMedii;
    private List<Intrebare> listaIntrebariGrele;


//    List<String> intrebarile = new ArrayList<>();
//    TreeMap<String,Boolean> raspunsuri = new TreeMap<>();

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_intrebari);
        sharedPreferences = getSharedPreferences(Constante.SETARI_ELEV_PREF,MODE_PRIVATE);
        initializarePrimaIntrebare();
        initComponents();

    }

    private View.OnClickListener confirmRaspuns(){
        return new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (isValid()) {
                    String dificultate = sharedPreferences.getString(Constante.DIFICULTATE_PREF,null);

                    nrCurent++;
                    if(nrCurent==11){
                        Intent intent= new Intent(getApplicationContext(), RezultatActivitate.class);
                        startActivity(intent);
                        finish();
                    }
                    else {

                        

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

    private void initComponents(){
        tvNrIntrebare=findViewById(R.id.intrebari_tv_nr_intrebare);
        rg_raspunsuri = findViewById(R.id.intrebari_rg_raspunsuri);
        rb_raspuns1 = findViewById(R.id.intrebari_rb_raspuns1);
        rb_raspuns2 = findViewById(R.id.intrebari_rb_raspuns2);
        rb_raspuns3 = findViewById(R.id.intrebari_rb_raspuns3);
        btn_confirm = findViewById(R.id.intrebari_btn_confirm);
        imgV_imagine = findViewById(R.id.intrebari_iv_imagine);
        tvIntrebare = findViewById(R.id.intrebari_tv_intrebare);
        btn_confirm.setOnClickListener(confirmRaspuns());
        nrCurent=1;
        tvNrIntrebare.setText(nrCurent+getString(R.string.intrebari_tv_nr_intrebari));
    }

    private void initializarePrimaIntrebare(){
        @SuppressLint("StaticFieldLeak") HttpManager manager = new HttpManager(){
            @Override
            protected void onPostExecute(String s){
                try {

                    set = SetIntrebariParser.fromJson(s);

                    String dificultate = sharedPreferences.getString(Constante.DIFICULTATE_PREF,null);
                    if(dificultate.compareTo(Constante.USOR_DIFICULTATE_TEST)==0){
                        listaIntrebariUsoare = set.getUsor();
                        tvIntrebare.setText(listaIntrebariUsoare.get(0).getTextIntrebare());
                        rb_raspuns1.setText(listaIntrebariUsoare.get(0).getRaspunsuri().get(0).getTextRaspuns());
                        rb_raspuns2.setText(listaIntrebariUsoare.get(0).getRaspunsuri().get(1).getTextRaspuns());
                        rb_raspuns3.setText(listaIntrebariUsoare.get(0).getRaspunsuri().get(2).getTextRaspuns());
                    }
                    else if(dificultate.compareTo(Constante.MEDIU_DIFICULTATE_TEST)==0){
                        listaIntrebariMedii = set.getMediu();
                        tvIntrebare.setText(listaIntrebariMedii.get(0).getTextIntrebare());
                        rb_raspuns1.setText(listaIntrebariMedii.get(0).getRaspunsuri().get(0).getTextRaspuns());
                        rb_raspuns2.setText(listaIntrebariMedii.get(0).getRaspunsuri().get(1).getTextRaspuns());
                        rb_raspuns3.setText(listaIntrebariMedii.get(0).getRaspunsuri().get(2).getTextRaspuns());
                    }
                    else if(dificultate.compareTo(Constante.GREU_DIFICULTATE_TEST)==0){
                        listaIntrebariGrele = set.getGreu();
                        tvIntrebare.setText(listaIntrebariGrele.get(0).getTextIntrebare());
                        rb_raspuns1.setText(listaIntrebariGrele.get(0).getRaspunsuri().get(0).getTextRaspuns());
                        rb_raspuns2.setText(listaIntrebariGrele.get(0).getRaspunsuri().get(1).getTextRaspuns());
                        rb_raspuns3.setText(listaIntrebariGrele.get(0).getRaspunsuri().get(2).getTextRaspuns());
                    }

//                    JSONObject object = new JSONObject(s);
//                    JSONArray arrayI = object.getJSONArray("usor");
//                    for (int i = 0; i < arrayI.length(); i++) {
//                            JSONObject obiectArray = arrayI.getJSONObject(i);
//                            String numeIntrebare = obiectArray.getString("intrebare");
//                            List<Raspuns> listaRaspunsuri = new ArrayList<>();
//
//                        JSONArray raspunsuriArray = obiectArray.getJSONArray("raspunsuri");
//                        for (int j = 0; j < raspunsuriArray.length(); j++) {
//                            String numeRaspuns = raspunsuriArray.getJSONObject(j).getString("raspuns");
//                            Boolean corect = raspunsuriArray.getJSONObject(j).getBoolean("corect");
//                            listaRaspunsuri.add(new Raspuns(numeRaspuns,corect));
//                        }
//                        JSONObject optiune = obiectArray.getJSONObject("optiuni");
//                        String categorie = optiune.getString("categorie");
//                        String imagine = optiune.getString("imagine");
//                        Double punctaj = optiune.getDouble("punctaj");
//                        OptiuniIntrebare optiuniIntrebare = new OptiuniIntrebare(categorie,imagine,punctaj);
//                        Intrebare intrebare = new Intrebare(numeIntrebare,optiuniIntrebare,listaRaspunsuri);
//                        listaIntrebari.add(intrebare);
//                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        manager.execute(URL);
    }


}
