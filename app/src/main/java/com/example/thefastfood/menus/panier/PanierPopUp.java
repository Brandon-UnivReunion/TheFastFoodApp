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

import java.util.ArrayList;

public class PanierPopUp extends Dialog {
    private ListView listView;
    private TextView prixTotal;
    private Button valideButton, videButton, annuleButton;

    public PanierPopUp(Activity context,  DatabaseManager dm) {
        super(context, R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.popup_panier);

        listView = findViewById(R.id.panierListview);
        prixTotal = findViewById(R.id.panierPrixTotal);
        valideButton = findViewById(R.id.panierValider);
        videButton = findViewById(R.id.panierAnnuler);
//        Log.d("PANIERPOPUP", listView.toString());
        PanierAdapter panierAdapter = new PanierAdapter(context, dm);
        listView.setAdapter(panierAdapter);
        panierAdapter.notifyDataSetChanged();
    }




}
