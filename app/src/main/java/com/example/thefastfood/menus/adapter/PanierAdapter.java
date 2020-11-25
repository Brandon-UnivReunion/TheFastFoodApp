package com.example.thefastfood.menus.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.example.thefastfood.R;
import com.example.thefastfood.menus.dataBase.DatabaseManager;
import com.example.thefastfood.menus.item.Offre;

import java.util.ArrayList;

/**
 * Adapteur de la listView de l'affichage du panier
 */
public class PanierAdapter extends BaseAdapter {
    protected Context context;
    // List des item à afficher
    protected ArrayList<Offre> listItem;
    protected LayoutInflater inflater;
    // Manager de la base de donnée
    protected DatabaseManager databaseManager;

    public PanierAdapter(Context context,  DatabaseManager databaseManager) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.databaseManager = databaseManager;
        this.listItem = databaseManager.readPanier();
        Log.d("PanierAdapterDD", listItem.toString());
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Offre getItem(int i) {
        return listItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
//        Log.d("PanierAdapterDD", String.valueOf(i));
        view = inflater.inflate(R.layout.adapter_popup_panier,null);

        // Offre courante à afficher
        final Offre offre = getItem(i);


        TextView name = view.findViewById(R.id.itemName);
        name.setText(offre.getName());

        ImageView img = view.findViewById(R.id.itemImg);
        img.setImageResource(offre.getPathImg());

        TextView prix = view.findViewById(R.id.itemPrix);
        prix.setText(offre.getPrix()+context.getString(R.string.euro));


        return view;
    }
}
