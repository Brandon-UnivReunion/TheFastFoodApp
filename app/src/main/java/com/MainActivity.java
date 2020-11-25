package com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.activities.LoginActivity;
import com.example.thefastfood.R;
import com.example.thefastfood.maps.fragments.MapsFragment;
import com.example.thefastfood.menus.dataBase.DatabaseManager;
import com.example.thefastfood.menus.fragments.AfficheMenuFragment;
import com.example.thefastfood.menus.panier.PanierPopUp;
import com.fragments.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    /* DEBUT : Déclaration de variables */

    private FirebaseAuth auth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;

    LinearLayout bottomBar_item1, bottomBar_item2, bottomBar_item3, empty_basket, powerBtn, logoBtn;
    ImageView item1IV, item2IV, item3IV;
    TextView item1TV, item2TV, item3TV;

    Boolean bottomBar_item1_selectionned = true;
    Boolean bottomBar_item2_selectionned = false;
    Boolean bottomBar_item3_selectionned = false;

    /* FIN : Déclaration de variables */

    /**
     * Constructeur par défaut vide
     */
    public MainActivity(){}

    /**
     * Méthode qui annule l'action du bouton retour du smartphone
     */
    @Override
    public void onBackPressed() {
        //Désactiver le boutton retour d'android
    }


    /**
     * Méthode lancée à la création de la vue et relancée periodiquement
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Firebase
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        //XML
        bottomBar_item1 = findViewById(R.id.bottomBar_item1);
        bottomBar_item2 = findViewById(R.id.bottomBar_item2);
        bottomBar_item3 = findViewById(R.id.bottomBar_item3);

        item1IV = findViewById(R.id.item1IV);
        item2IV = findViewById(R.id.item2IV);
        item3IV = findViewById(R.id.item3IV);

        item1TV = findViewById(R.id.item1TV);
        item2TV = findViewById(R.id.item2TV);
        item3TV = findViewById(R.id.item3TV);

        powerBtn = findViewById(R.id.powerBtn);
        logoBtn = findViewById(R.id.logoBtn);
        empty_basket = findViewById(R.id.empty_basket);

        powerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        logoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(new AfficheMenuFragment(), "menu");
            }
        });

        empty_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String SHARED_PREFERENCES_NAME = "DB";
                DatabaseManager databaseManager = new DatabaseManager(MainActivity.this);

                // Persistence pour savoir si le panier à déjà été utilisé
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);


                if(sharedPreferences.getBoolean("panier", false) && databaseManager.readPanier().size() != 0) {
                    PanierPopUp panierPopUp = new PanierPopUp(MainActivity.this, databaseManager);
                    panierPopUp.show();
                }else{
                    Toast.makeText(MainActivity.this, "Le panier est vide !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Appel à la méthode de mise à jour de la bottom bar selon le fragment actif
        updateBottomBarItemsUI(bottomBar_item1_selectionned,bottomBar_item2_selectionned,bottomBar_item3_selectionned);


        //Mise en place des listeners sur les fragments de la bottombar pour la gestion des fragments selon celui qui est actif
        bottomBar_item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomBar_item1_selectionned = true;
                bottomBar_item2_selectionned = false;
                bottomBar_item3_selectionned = false;

                updateBottomBarItemsUI(bottomBar_item1_selectionned,bottomBar_item2_selectionned,bottomBar_item3_selectionned);
            }
        });

        bottomBar_item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomBar_item1_selectionned = false;
                bottomBar_item2_selectionned = true;
                bottomBar_item3_selectionned = false;

                updateBottomBarItemsUI(bottomBar_item1_selectionned,bottomBar_item2_selectionned,bottomBar_item3_selectionned);
            }
        });

        bottomBar_item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomBar_item1_selectionned = false;
                bottomBar_item2_selectionned = false;
                bottomBar_item3_selectionned = true;

                updateBottomBarItemsUI(bottomBar_item1_selectionned,bottomBar_item2_selectionned,bottomBar_item3_selectionned);
            }
        });




    }

    /**
     * Méthode appelé quand l'activité entre dans l'état Started. L'état Started permet d'intéragir avec les différents éléments de l'activité sans que l'utilisateur ait accès
     * à la vue. Utile pour les traitements backend comme la vérification de connexion
     */
    @Override
    protected void onStart() {
        super.onStart();

        //Vérification de l'état de connexion de l'utilisateur
        if(auth.getCurrentUser() == null){
            Intent login_activity_intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(login_activity_intent);
            finish();
        }
    }

    /**
     * Méthode qui met à jour la bottombar selon le fragment actif
     * @param item1_selected boolean qui prend la valeur true si le fragment 1(shop) est actif
     * @param item2_selected boolean qui prend la valeur true si le fragment 2(profile) est actif
     * @param item3_selected boolean qui prend la valeur true si le fragment 1(map) est actif
     */
    private void updateBottomBarItemsUI(Boolean item1_selected, Boolean item2_selected, Boolean item3_selected) {

        if(item1_selected){
            bottomBar_item1.setBackgroundColor(Color.rgb(204,203,195));
            item1TV.setTextColor(Color.rgb(194,149,27));
            item1IV.setImageResource(R.drawable.store_selected);

            showFragment(new AfficheMenuFragment(), "menu");
            System.out.println("Shop Selected from main");
        }else{
            bottomBar_item1.setBackgroundColor(Color.rgb(255,255,255));
            item1TV.setTextColor(Color.rgb(21,21,21));
            item1IV.setImageResource(R.drawable.store);
        }

        if(item2_selected){
            bottomBar_item2.setBackgroundColor(Color.rgb(204,203,195));
            item2TV.setTextColor(Color.rgb(194,149,27));
            item2IV.setImageResource(R.drawable.profile_selected);


            showFragment(new ProfileFragment());
            System.out.println("Profile Selected from main");
        }else{
            bottomBar_item2.setBackgroundColor(Color.rgb(255,255,255));
            item2TV.setTextColor(Color.rgb(21,21,21));
            item2IV.setImageResource(R.drawable.profile);
        }

        if(item3_selected){
            bottomBar_item3.setBackgroundColor(Color.rgb(204,203,195));
            item3TV.setTextColor(Color.rgb(194,149,27));
            item3IV.setImageResource(R.drawable.location_selected);

            showFragment(new MapsFragment());
            System.out.println("Shop Selected from main");

        }else{
            bottomBar_item3.setBackgroundColor(Color.rgb(255,255,255));
            item3TV.setTextColor(Color.rgb(21,21,21));
            item3IV.setImageResource(R.drawable.location);
        }


    }

    /**
     * méthode d'affichage des fragments
     * @param fragment
     */
    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.page, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    /**
     * méthode d'affichage des fragments avec tag
     * @param fragment
     * @param tag
     */
    private void showFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.page, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    /**
     * méthode permettant la deconnexion de l'utilisateur
     */
    public void signOut(){
        auth.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Gestion des evenements
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        AfficheMenuFragment afficheMenuFragment = (AfficheMenuFragment) getSupportFragmentManager().findFragmentByTag("menu");
        Log.d("dispatch2", String.valueOf(afficheMenuFragment));
        if(afficheMenuFragment instanceof AfficheMenuFragment)
            afficheMenuFragment.actionDispatchTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
}