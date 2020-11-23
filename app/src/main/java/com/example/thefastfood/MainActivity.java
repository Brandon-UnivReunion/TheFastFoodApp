package com.example.thefastfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.thefastfood.maps.fragments.MapsFragment;
import com.example.thefastfood.menus.activities.MainMenuActivity;
import com.example.thefastfood.menus.dataBase.DatabaseManager;
import com.example.thefastfood.menus.fragments.AfficheMenuFragment;
import com.example.thefastfood.menus.panier.PanierPopUp;
import com.google.android.gms.maps.MapFragment;

public class MainActivity extends AppCompatActivity  {



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

    public void menuClick(View view){
        displayFragment(new AfficheMenuFragment(),"menu");
    }
    public void mapsClick(View view){
        displayFragment(new MapsFragment(), "maps");
    }

    public void panierClick(View view){
        final String SHARED_PREFERENCES_NAME = "DB";
        DatabaseManager databaseManager = new DatabaseManager(this);

        // Persistence pour savoir si le panier à déjà été utilisé
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);


        if(sharedPreferences.getBoolean("panier", false) && databaseManager.readPanier().size() != 0) {
            PanierPopUp panierPopUp = new PanierPopUp(this, databaseManager);
            panierPopUp.show();
        }else{
            Toast.makeText(this, "Le panier est vide !", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Affiche le fragment
     * @param fragment
     */
    private void displayFragment(Fragment fragment, String tag){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framel, fragment, tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }



    /**
     * Gestion des evenements
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO implement fragment gesture listener
//        myGestureDetectorListener.onTouchEvent(ev);
        Log.d("dispatch2", "dispatchTouchEvent");
        AfficheMenuFragment afficheMenuFragment = (AfficheMenuFragment) getSupportFragmentManager().findFragmentByTag("menu");
        Log.d("dispatch2", String.valueOf(afficheMenuFragment));
        if(afficheMenuFragment != null && afficheMenuFragment instanceof AfficheMenuFragment)
            afficheMenuFragment.actionDispatchTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

}