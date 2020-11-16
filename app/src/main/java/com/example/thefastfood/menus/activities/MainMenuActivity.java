package com.example.thefastfood.menus.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.example.thefastfood.MainActivity;
import com.example.thefastfood.R;
import com.example.thefastfood.menus.adapter.OffreAdapter;
import com.example.thefastfood.menus.item.Offre;

import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Créé la liste des offres
        ArrayList<Offre> listOffres = new ArrayList<Offre>();
        for(int i=1;i<10;i++)
            listOffres.add(new Offre(R.drawable.na, "Offre "+i));

        // Recuperes la GridView
        GridView gridView = findViewById(R.id.menu_gridview);
        gridView.setAdapter((new OffreAdapter(this, listOffres)));

    }

    public void clickCardTest(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}