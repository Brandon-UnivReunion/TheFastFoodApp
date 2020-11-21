package com.example.thefastfood.menus.panier;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.example.thefastfood.R;
import com.example.thefastfood.menus.adapter.PanierAdapter;
import com.example.thefastfood.menus.dataBase.DatabaseManager;
import com.example.thefastfood.menus.item.Offre;

import java.util.ArrayList;

public class PanierPopUp extends Dialog {
    private ListView listView;

    public PanierPopUp(Activity context,  DatabaseManager dm) {
        super(context, R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.popup_panier);
        listView = findViewById(R.id.panierListview);
//        Log.d("PANIERPOPUP", listView.toString());
        PanierAdapter panierAdapter = new PanierAdapter(context, dm);
        listView.setAdapter(panierAdapter);
        panierAdapter.notifyDataSetChanged();
    }

    public void build(){
        show();
    }
}
