package com.example.docta.myapplication.clase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.docta.myapplication.R;

import java.util.List;

public class EleviAdaptor extends ArrayAdapter<Elev> {

    private Context context;
    private int resource;
    private List<Elev> elevi;
    private LayoutInflater inflater;

    public EleviAdaptor(@NonNull Context context,
                        int resource,
                        @NonNull List<Elev> objects,
                        LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.elevi = objects;
        this.inflater = inflater;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        View rand = inflater.inflate(resource, parent, false);

        TextView tvNume = rand.findViewById(R.id.lv_elevi_rand_nume);
       // TextView tvGen = rand.findViewById(R.id.lv_elevi_rand_gen);
        TextView tvVarsta = rand.findViewById(R.id.lv_elevi_rand_varsta);

        Elev elev= elevi.get(position);

        /*tvNume.setText(elev.getNumeAvatar() != null ?
              elev.getNumeAvatar().toString()
                : context.getString(R.string.adaptor_elevi_eroare_nume));*/
        tvNume.setText(elev.getNumeAvatar());
        //tvGen.setText(elev.isGen() ? context.getString(R.string.elevi_adaptor_gen_baiat) : context.getString(R.string.elevi_adaptor_gen_fata));
        tvVarsta.setText(elev.getVarsta());

        return rand;
    }
}
