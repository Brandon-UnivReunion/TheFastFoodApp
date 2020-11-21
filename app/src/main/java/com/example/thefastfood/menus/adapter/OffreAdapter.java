package com.example.thefastfood.menus.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.example.thefastfood.R;
import com.example.thefastfood.menus.item.Offre;

import java.util.ArrayList;

public class OffreAdapter extends BaseAdapter {

    protected Context context;
    protected ArrayList<Offre> listOffre;
    protected LayoutInflater inflater;
    protected ArrayList all;

    public OffreAdapter(Context context, ArrayList<Offre> listOffre) {
        this.context = context;
        this.listOffre = listOffre;
        this.inflater = LayoutInflater.from(context);
        this.all = new ArrayList<>();
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
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        all.clear();

        view = inflater.inflate(R.layout.adapter_offre,null);

        final Offre offre = getItem(i);

        String offreName = offre.getName();
        int offreImg = offre.getPathImg();

        TextView name = view.findViewById(R.id.offreName);
        name.setText(offreName);

        ImageView img = view.findViewById(R.id.imageOffre);
        // TODO changer l'image

        // TODO ajouter le prix

        CardView cardView = view.findViewById(R.id.cardView);

        all.add(name.getText());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Ajout au panier des éléments
                ArrayList a = (ArrayList) all.clone();
                Toast.makeText(context, i+"name: " + offre.getId() + "///" + R.id.imageOffre, Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, i+"name: " + all.get(0) + "///" + R.id.imageOffre, Toast.LENGTH_SHORT).show();
                Log.d("onclickview","h");
            }
        });




        return view;
    }


    public void setListOffre(ArrayList<Offre> listOffre) {
        this.listOffre = listOffre;
    }
}
