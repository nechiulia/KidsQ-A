package com.example.docta.myapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.docta.myapplication.Classes.Adaptor.ClasamentAdapter;
import com.example.docta.myapplication.Classes.Adaptor.StudentAdapter;
import com.example.docta.myapplication.Classes.Firebase.FirebaseController;
import com.example.docta.myapplication.Classes.util.Student;
import com.example.docta.myapplication.R;
import com.example.docta.myapplication.Classes.util.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public  class RankingActivity extends AppCompatActivity {

    private FirebaseController firebaseController;
    private ArrayList<Student> listCls;
    private ListView lv_classament;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        if(savedInstanceState==null){
            String title = getString(R.string.Titlu_Clasament);
            this.setTitle(title);
        }
        initComp();


    }
    private void initComp(){
        //firebaseController = FirebaseController.getInstance();
        lv_classament = findViewById(R.id.ranking_lv_studentsranking);
       // listCls = firebaseController.findStudentClasament();

        reference =FirebaseDatabase.getInstance().getReference("clasament");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listCls = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Student std = data.getValue(Student.class);
                    if (std != null) {
                        listCls.add(std);
                    }
                }
                Collections.sort(listCls);
                Collections.reverse(listCls);
                initAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //initAdapter();
    }
    private ValueEventListener selectEventListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("RankingActivity", "Data is not available");
            }
        };
    }

    private void initAdapter() {
        ClasamentAdapter adapter = new ClasamentAdapter(getApplicationContext(), R.layout.lv_clasament_students, listCls, getLayoutInflater());
        // adapter.notifyDataSetChanged();
        lv_classament.setAdapter(adapter);
    }
}
