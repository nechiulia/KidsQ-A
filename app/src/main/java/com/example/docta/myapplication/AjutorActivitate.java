package com.example.docta.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docta.myapplication.clase.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AjutorActivitate extends AppCompatActivity {


    private ExpandableListView listaExp;
    private ExpandableListAdapter listAdapter;
    private List<String> listaIntrebari;
    private HashMap<String,List<String>> listaRaspunsuri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajutor_activitate);
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
                        .show();
                return false;
            }
        });

        listaExp.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listaIntrebari.get(groupPosition),
                        Toast.LENGTH_SHORT).show();
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

}
