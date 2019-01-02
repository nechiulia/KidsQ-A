package com.example.docta.myapplication.Classes.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.docta.myapplication.Classes.util.Constants;
import com.example.docta.myapplication.Classes.util.Student;
import com.example.docta.myapplication.R;

import java.util.List;

public class ClasamentAdapter extends ArrayAdapter<Student> {

    private Context context;
    private int resource;
    private List<Student> students;
    private LayoutInflater inflater;

    public ClasamentAdapter(@NonNull Context context,
                          int resource,
                          @NonNull List<Student> objects,
                          LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.students = objects;
        this.inflater = inflater;
    }


    @SuppressLint({"ResourceType", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        @SuppressLint("ViewHolder") View row = inflater.inflate(resource, parent, false);

        TextView tvNrcrt = row.findViewById(R.id.tv_clasament_nrcrt);
        TextView tvUsername = row.findViewById(R.id.tv_clasament_username);
        TextView tvScore = row.findViewById(R.id.tv_clasament_score);
        ImageView imgTrophy= row.findViewById(R.id.img_clasament);

        Student student = students.get(position);
        int pos = position+1;

        tvNrcrt.setText(String.valueOf(pos));
        tvUsername.setText(student.getUsername());
        tvScore.setText(student.getScore().toString());
        if(position==0){
            imgTrophy.setBackgroundResource(R.drawable.trophy1);
        }else if(position==1){
            imgTrophy.setBackgroundResource(R.drawable.trophy2);
        }else if(position==2){
            imgTrophy.setBackgroundResource(R.drawable.trophy3);
        }else{
            imgTrophy.setBackgroundResource(R.drawable.medal);

        }

        return row;
    }
}
