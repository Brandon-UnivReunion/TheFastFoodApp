package com.example.thefastfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.thefastfood.menus.activities.MainMenuActivity;
import com.example.thefastfood.menus.dataBase.DatabaseManager;
import com.example.thefastfood.menus.panier.PanierPopUp;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view){
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
//        finish();
    }

    public void panierClick(View view){

        PanierPopUp panierPopUp = new PanierPopUp(this, new DatabaseManager(this));
        panierPopUp.show();
    }
}