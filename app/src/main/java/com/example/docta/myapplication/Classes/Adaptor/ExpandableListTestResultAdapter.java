package com.example.docta.myapplication.Classes.Adaptor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.docta.myapplication.Classes.util.Student;
import com.example.docta.myapplication.Classes.util.TestResult;
import com.example.docta.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ExpandableListTestResultAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<Student> header;
    private HashMap<Student,TestResult> child;

    public ExpandableListTestResultAdapter(Context context, ArrayList<Student> studentHeader, HashMap<Student, TestResult> studentTest) {
        this.context = context;
        this.header = studentHeader;
        this.child = studentTest;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final TestResult result = (TestResult) getChild(groupPosition, childPosition);
        //final TestResult result1 = child.get(groupPosition).get(childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.ex_tests_results_items, null);
        }

        if(result == null){
            TextView error = convertView.findViewById(R.id.ex_item_noCorrects);
            error.setText("Nu exista teste rezolvate");
            return convertView;
        }
        else {
            TextView Usor = convertView.findViewById(R.id.ex_item_Usor);
            TextView Mediu = convertView.findViewById(R.id.ex_item_Mediu);
            TextView Greu = convertView.findViewById(R.id.ex_item_Greu);

            TextView noCorrect = convertView.findViewById(R.id.ex_item_noCorrects);
            TextView score = convertView.findViewById(R.id.ex_item_score);

            Usor.setText("Test Usor: "+String.valueOf(result.getDif_tests().get(0)));
            Mediu.setText("Test Mediu: "+String.valueOf(result.getDif_tests().get(1)));
            Greu.setText("Test Greu: "+String.valueOf(result.getDif_tests().get(2)));

            noCorrect.setText("Raspunsuri \ncorecte: "+String.valueOf(result.getNoCorrectAnswers()));
            score.setText("Punctaj Total: "+String.valueOf(result.getScore()));
            return convertView;
        }
    }

    @Override
    public int getGroupCount() {

        return this.header.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {

        return this.header.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {

        return this.child.get(this.header.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Student student = (Student) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.ex_tests_results_group, null);
        }

        ImageView avatar = convertView.findViewById(R.id.imgAvatar_ex_tests_results_group);
        TextView username = convertView.findViewById(R.id.tvUsername_ex_tests_results_group);

        byte[] avt = student.getAvatar();
        Bitmap bitmap = BitmapFactory.decodeByteArray(avt,0,avt.length);
        avatar.setImageBitmap(bitmap);

        username.setText(student.getUsername());

        return convertView;
    }



    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}