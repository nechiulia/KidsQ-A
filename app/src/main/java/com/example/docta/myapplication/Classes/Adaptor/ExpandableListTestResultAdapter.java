package com.example.docta.myapplication.Classes.Adaptor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.docta.myapplication.Classes.util.Student;
import com.example.docta.myapplication.Classes.util.TestResult;
import com.example.docta.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ExpandableListTestResultAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<Student> header;
    private HashMap<String,ArrayList<TestResult>> child;

    public ExpandableListTestResultAdapter(Context context, ArrayList<Student> studentHeader, HashMap<String, ArrayList<TestResult>> studentTest) {
        this.context = context;
        this.header = studentHeader;
        this.child = studentTest;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {

        // This will return the child
        return this.child.get(this.header.get(groupPosition).getUsername()).get(
                childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        // return children count
        return this.child.get(this.header.get(groupPosition).getUsername()).size();
    }
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final TestResult result = (TestResult) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.ex_tests_results_items, null);
        }
        if(result==null){
            TextView categ = convertView.findViewById(R.id.ex_item_categ);
            categ.setText("Nu exista teste rezolvate");
            return convertView;
        }

        TextView categ = convertView.findViewById(R.id.ex_item_categ);
        TextView dific = convertView.findViewById(R.id.ex_item_dificulty);
        TextView noCorrect = convertView.findViewById(R.id.ex_item_noCorrects);
        TextView score = convertView.findViewById(R.id.ex_item_score);

        categ.setText(result.getCategory());
        dific.setText(result.getDifficulty());
        noCorrect.setText(result.getNoCorrectAnswers());
        score.setText(String.valueOf(result.getScore()));
        return convertView;
    }

    @Override
    public Object getGroup(int groupPosition) {

        // Get header position
        return this.header.get(groupPosition);
    }

    @Override
    public int getGroupCount() {

        // Get header size
        return this.header.size();
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
            convertView = inflater.inflate(R.layout.ex_tests_results_group, parent,false);
        }

        ImageView avatar = convertView.findViewById(R.id.imgAvatar_ex_tests_results_group);
        TextView username = convertView.findViewById(R.id.tvUsername_ex_tests_results_group);
        Spinner category = convertView.findViewById(R.id.spnCateg_ex_tests_results_group);

        byte[] avt = student.getAvatar();
        Bitmap bitmap = BitmapFactory.decodeByteArray(avt,0,avt.length);
        avatar.setImageBitmap(bitmap);

        username.setText(student.getUsername());

        ArrayAdapter<CharSequence> adapterspn = ArrayAdapter.createFromResource(context,R.array.ex_test_result,R.layout.support_simple_spinner_dropdown_item);
        category.setAdapter(adapterspn);

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
