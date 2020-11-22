package com.example.thefastfood.menus.panier;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.thefastfood.R;
import com.example.thefastfood.menus.adapter.PanierAdapter;
import com.example.thefastfood.menus.dataBase.DatabaseManager;
import com.example.thefastfood.menus.item.Offre;
import com.example.thefastfood.menus.listOffres.ListOffres;

import java.util.ArrayList;

public class PanierPopUp extends Dialog {
    private ListView listView;
    private TextView prixTotal;
    private Button valideButton, videButton, annuleButton;
    private PanierPopUp panierPopUp;

    public PanierPopUp(Activity context,  DatabaseManager dm) {
        super(context, R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.popup_panier);

        panierPopUp = this;

        listView = findViewById(R.id.panierListview);

        prixTotal = findViewById(R.id.panierPrixTotal);
        ArrayList<Offre> listOffres = dm.readPanier();
        float total = 0;
        for (Offre offre: listOffres) {
            total += offre.getPrix();
        }
        prixTotal.setText(String.valueOf(total));

        valideButton = findViewById(R.id.panierValider);
        videButton = findViewById(R.id.panierVider);

        annuleButton = findViewById(R.id.panierAnnuler);
        annuleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panierPopUp.dismiss();
            }
        });
//        Log.d("PANIERPOPUP", listView.toString());
        PanierAdapter panierAdapter = new PanierAdapter(context, dm);
        listView.setAdapter(panierAdapter);
        panierAdapter.notifyDataSetChanged();
    }



}
