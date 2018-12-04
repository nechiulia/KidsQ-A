package com.example.docta.myapplication.Classes.Adaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.docta.myapplication.R;
import com.example.docta.myapplication.Classes.Student;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {

    private Context context;
    private int resource;
    private List<Student> students;
    private LayoutInflater inflater;

    public StudentAdapter(@NonNull Context context,
                          int resource,
                          @NonNull List<Student> objects,
                          LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.students = objects;
        this.inflater = inflater;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        View row = inflater.inflate(resource, parent, false);

        TextView tvName = row.findViewById(R.id.studentsrow_lv_name);
        TextView tvGender = row.findViewById(R.id.studentsrow_lv_sex);
        TextView tvAge = row.findViewById(R.id.studentsrow_lv_age);

        Student student = students.get(position);

        tvName.setText(student.getAvatarName());
        tvAge.setText(String.valueOf(student.getAge()));
        tvGender.setText(student.getGender()==R.id.signup_rb_boy ? "Băiat":"Fată");

        return row;
    }
}
