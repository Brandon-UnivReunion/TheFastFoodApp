package com.example.thefastfood.menus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thefastfood.R;
import com.example.thefastfood.menus.item.Offre;

import java.util.ArrayList;

public class OffreAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Offre> listOffre;
    private LayoutInflater inflater;

    public OffreAdapter(Context context, ArrayList<Offre> listOffre) {
        this.context = context;
        this.listOffre = listOffre;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listOffre.size();
    }

    @Override
    public Offre getItem(int i) {
        return listOffre.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflater.inflate(R.layout.adapter_offre,null);

        Offre offre = getItem(i);

        String offreName = offre.getName();
        int offreImg = offre.getPathImg();

        TextView name = view.findViewById(R.id.offreName);
        name.setText(offreName);

        ImageView img = view.findViewById(R.id.imageOffre);







        return view;
    }
}
