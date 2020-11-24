package com.example.thefastfood.menus.adapter;

import android.content.Context;
import android.content.SharedPreferences;
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

public class OffreAdapter extends BaseAdapter {

    protected Context context;
    protected ArrayList<Offre> listOffre;
    protected LayoutInflater inflater;
    protected DatabaseManager databaseManager;

    public OffreAdapter(Context context, ArrayList<Offre> listOffre, DatabaseManager databaseManager) {
        this.context = context;
        this.listOffre = listOffre;
        this.inflater = LayoutInflater.from(context);
        this.databaseManager = databaseManager;
        Log.d("Panier25", String.valueOf(listOffre));
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
        Log.d("Panier25", "Ajout panier");

        view = inflater.inflate(R.layout.adapter_offre,null);

        final Offre offre = getItem(i);

        TextView name = view.findViewById(R.id.offreName);
        name.setText(offre.getName());

        ImageView img = view.findViewById(R.id.offreImg);
        img.setImageResource(offre.getPathImg());

        TextView prix = view.findViewById(R.id.offrePrix);
        prix.setText(offre.getPrix()+context.getString(R.string.euro));

        CardView cardView = view.findViewById(R.id.cardView);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Ajout au panier des éléments
                Log.d("Panier", "Ajout panier");
                databaseManager.insertItem(offre.getId());

                // Notifier que le panier à été utilisé
                final String SHARED_PREFERENCES_NAME = "DB";
                SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("panier", true);
                editor.apply();

                Toast.makeText(context, "Ajout "+offre.getName()+" au panier", Toast.LENGTH_SHORT).show();

            }
        });




        return view;
    }


    public void setListOffre(ArrayList<Offre> listOffre) {
        this.listOffre = listOffre;
    }
}
