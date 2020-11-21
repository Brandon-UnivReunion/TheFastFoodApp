package com.example.thefastfood.menus.toolbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.thefastfood.R;
import com.example.thefastfood.menus.dataBase.DatabaseManager;
import com.example.thefastfood.menus.panier.PanierPopUp;

public class MenuToolbarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu_toolbar);
    }

    public void panierClick(View view){

        PanierPopUp panierPopUp = new PanierPopUp(this, new DatabaseManager(this));
        panierPopUp.show();
    }
}