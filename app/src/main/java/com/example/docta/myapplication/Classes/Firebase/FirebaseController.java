package com.example.docta.myapplication.Classes.Firebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.docta.myapplication.Classes.util.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class FirebaseController implements FConstants{
    private DatabaseReference db;
    private static FirebaseController firebaseController;
    private FirebaseDatabase dbontroller;

    private FirebaseController(){
        dbontroller = FirebaseDatabase.getInstance();
    }
    private void open(){
        db = dbontroller.getReference(CLASAMENT_TABLE_NAME);
    }

    public static FirebaseController getInstance(){
        if(firebaseController == null){
            synchronized (FirebaseController.class){
                if(firebaseController==null){
                    firebaseController = new FirebaseController();
                }
            }
        }
        return firebaseController;
    }

    public String upsertAccountInClasament(Student student){
        if(student == null){
            return null;
        }
        open();
        if(student.getGlobalID() == null || student.getGlobalID().trim().isEmpty()){
            student.setGlobalID(db.push().getKey());
        }

        Student std = new Student(student.getUsername(),student.getAge(),student.getGender(),student.getNoCorrect(),student.getScore());
        db.child(student.getGlobalID()).setValue(std);
        return student.getGlobalID();
    }

    public void updateScore(double newScore,int newNoCorect,String user){
        open();
        db.getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Student std;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                      std = postSnapshot.getValue(Student.class);
                    assert std != null;
                    if(std.getUsername().equals(user)){
                        double actualScore = std.getScore();
                        int actualNoCorrects = std.getNoCorrect();
                        std.setScore(actualScore+newScore);
                        std.setNoCorrect(actualNoCorrects + newNoCorect);
                        //std.setGlobalID(postSnapshot.getKey());
                        dataSnapshot.getRef().child(Objects.requireNonNull(postSnapshot.getKey())).setValue(std);
                      }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    public void updateUsername(String oldName,String newName){
        open();
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Student std;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    std = postSnapshot.getValue(Student.class);
                    assert std != null;
                    if(std.getUsername().equals(oldName)) {
                        std.setUsername(newName);
                        dataSnapshot.getRef().child(Objects.requireNonNull(postSnapshot.getKey())).setValue(std);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    public ArrayList<Student> findStudentClasament(){
//        if(eventListener==null){
//            return null;
//        }
        final ArrayList<Student> students = new ArrayList<>();
//        open();
//        db.addValueEventListener(eventListener);
//
//        return students;
        open();
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Student std;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    std = postSnapshot.getValue(Student.class);
                    students.add(std);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return students;
    }

    public boolean remove(String username){
        open();
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Student std;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    std = postSnapshot.getValue(Student.class);
                    assert std != null;
                    if(std.getUsername().equals(username)) {
                        dataSnapshot.getRef().child(Objects.requireNonNull(postSnapshot.getKey())).removeValue();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return true;
    }
}
