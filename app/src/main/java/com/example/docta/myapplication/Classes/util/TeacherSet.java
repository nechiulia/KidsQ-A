package com.example.docta.myapplication.Classes.util;

import java.io.Serializable;
import java.util.ArrayList;

public class TeacherSet implements Serializable {
    private ArrayList<Teacher> teacherList;

    public TeacherSet(ArrayList<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    public TeacherSet() {
    }

    public ArrayList<Teacher> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(ArrayList<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    @Override
    public String toString() {
        return "TeacherSet{" +
                "teacherList=" + teacherList +
                '}';
    }

}
