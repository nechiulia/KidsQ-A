package com.example.docta.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.docta.myapplication.clase.Adaptor.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HelpActivity extends AppCompatActivity {


    private ExpandableListView listaExp;
    private ExpandableListAdapter listAdapter;
    private List<String> listaIntrebari;
    private HashMap<String,List<String>> listaRaspunsuri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(savedInstanceState==null){
            String titlu = getString(R.string.Titlu_Ajutor);
            this.setTitle(titlu);
        }
        initComponents();
        CreareLista();

        listAdapter = new ExpandableListAdapter(this, listaIntrebari, listaRaspunsuri);

        listaExp.setAdapter(listAdapter);


        listaExp.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        listaIntrebari.get(groupPosition)
                                + " : "
                                + listaRaspunsuri.get(
                                listaIntrebari.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .cancel();
                return false;
            }
        });

        listaExp.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listaIntrebari.get(groupPosition),
                        Toast.LENGTH_SHORT).cancel();
            }
        });

    }
    private void initComponents(){
        listaExp = findViewById(R.id.ajutor_elv_listaintrebari);

    }

    private void CreareLista(){
        listaIntrebari = new ArrayList<String>();
        listaRaspunsuri = new HashMap<String, List<String>>();

        listaIntrebari.add(getString(R.string.ajutor_tv_primaIntrebare));
        listaIntrebari.add(getString(R.string.ajutor_tv_aDouaIntrebare));

        List<String> raspuns1 = new ArrayList<String>();
        raspuns1.add(getString(R.string.ajutor_tv_alDoileaRaspuns));

        List<String> raspuns2 = new ArrayList<String>();
        raspuns2.add(getString(R.string.ajutor_tv_alDoilea_raspuns));


        listaRaspunsuri.put(listaIntrebari.get(0),raspuns1);
        listaRaspunsuri.put(listaIntrebari.get(1),raspuns2);

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
