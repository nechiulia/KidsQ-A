package com.example.docta.myapplication.clase;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.docta.myapplication.R;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> listaIntrebari; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> listaRaspunsuri;

    public ExpandableListAdapter(Context context, List<String> listaIntrebari,
                                 HashMap<String, List<String>> listaRaspunsuri) {
        this._context = context;
        this.listaIntrebari = listaIntrebari;
        this.listaRaspunsuri = listaRaspunsuri;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listaRaspunsuri.get(this.listaIntrebari.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listaRaspunsuri.get(this.listaIntrebari.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listaIntrebari.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listaIntrebari.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.ex_list_item, null);
        }

        TextView txtListChild = convertView
                .findViewById(R.id.tv_ex_list_item);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.ex_list_group, null);
        }

        TextView lblListHeader = convertView
                .findViewById(R.id.tv_ex_list_group);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

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
