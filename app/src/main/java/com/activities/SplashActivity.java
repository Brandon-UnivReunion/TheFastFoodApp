package com.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.thefastfood.R;

public class SplashActivity extends AppCompatActivity {

    //Variables
    Handler handler;
    ImageView logo;

    /**
     * Méthode qui va se lancer à l'appel de l'activité.
     * @param savedInstanceState Sauvegarde d'instance sauvegardée.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //XML
        logo = findViewById(R.id.logoSP);



        //Gestion du changement d'activité : de l'activité splash à l'activité de connexion
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);




    }
}