package com.example.docta.myapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.docta.myapplication.Classes.Database.TestResultDAO;
import com.example.docta.myapplication.Classes.util.Constants;
import com.example.docta.myapplication.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class TestsGraph extends AppCompatActivity {
    private PieChart pieChart;
    private TestResultDAO testResultDAO;
    private SharedPreferences sharedPreferences;
    String user;
    HashMap<String,Float> no_tests= new HashMap<>();
    public ArrayList<String> categories= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests_graph);
        initComponents();
    }
    private void initComponents(){

        pieChart=findViewById(R.id.testsgraph_piechart);
        Description desc= new Description();
        desc.setText("Grafic categorii teste");
        pieChart.setDescription(desc);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Categorii");
        pieChart.setCenterTextSize(20);

        testResultDAO=new TestResultDAO(getApplicationContext());
        sharedPreferences= getSharedPreferences(Constants.USERNAME_PREF,MODE_PRIVATE);
        user= sharedPreferences.getString(Constants.USERNAME_KEY,"user");
        testResultDAO.open();
        no_tests= testResultDAO.findAllTestCategory(user);
        testResultDAO.close();
        categories.add(Constants.CATEGORY_ANIMALS);
        categories.add(Constants.CATEGORY_FRUITS);
        categories.add(Constants.CATEGORY_LETTERS);
        categories.add(Constants.CATEGORY_LIFE);
        categories.add(Constants.CATEGORY_MATH);
        addDataSet();
        pieChart.setOnChartValueSelectedListener(OnChartValueSelected());
    }

    private OnChartValueSelectedListener OnChartValueSelected() {
        return  new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
            int pos1= e.toString().indexOf("y: ");
            int pos2= h.toString().indexOf("x: ");
               String categ= h.toString().substring(pos2+3);
               String no= e.toString().substring(pos1 +3);
                categ= categ.substring(0,1);
               for(int i=0; i< categories.size();i++){
                    if(no_tests.get(categories.get(i))==Float.parseFloat(no)){
                        pos1=i;
                        break;
                    }
               }
               pos2= Integer.parseInt(categ) ;
               String categSelected= categories.get(pos2);
               Toast.makeText(getApplicationContext(),"Categorie: "+ categSelected+ " Numar teste: "+ no_tests.get(categSelected), Toast.LENGTH_LONG).show();

               }

            @Override
            public void onNothingSelected() {

            }
        };
    }

    private void addDataSet() {
        ArrayList<PieEntry>  yEntrys=new ArrayList<>();
        ArrayList<String> xEntrys= new ArrayList<>();


        Random r= new Random();
        ArrayList<Integer> colors= new ArrayList<>();
        for(int i=0; i<no_tests.size();i++){
            int color= Color.argb(230,1+r.nextInt(254), 1+r.nextInt(254),1+r.nextInt(254));
            yEntrys.add(new PieEntry(no_tests.get(categories.get(i)),i));
            xEntrys.add(categories.get(i));
            colors.add(color);
        }

        PieDataSet pieDataSet= new PieDataSet(yEntrys,"Categorii teste");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(25);
        pieDataSet.setColors(colors);

        Legend legend= pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        PieData pieData= new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}
