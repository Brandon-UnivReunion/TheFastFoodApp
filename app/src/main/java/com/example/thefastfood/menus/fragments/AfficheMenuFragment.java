package com.example.thefastfood.menus.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thefastfood.R;
import com.example.thefastfood.menus.adapter.OffreAdapter;
import com.example.thefastfood.menus.dataBase.CreateurMenu;
import com.example.thefastfood.menus.dataBase.DatabaseManager;
import com.example.thefastfood.menus.listOffres.ListOffres;
import com.example.thefastfood.menus.listOffres.ListOffresDrink;
import com.example.thefastfood.menus.listOffres.ListOffresPop;
import com.example.thefastfood.menus.panier.PanierPopUp;

import java.util.ArrayList;


public class AfficheMenuFragment extends Fragment {

    TextView titleTV;
    GridView gridView;
    ArrayList<ListOffres> packOffres;
    int idPack;
    MyGestureDetectorListener myGestureDetectorListener;
    DatabaseManager databaseManager;



    public AfficheMenuFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO initialize db on view
        View fragmentView = inflater.inflate(R.layout.fragment_affiche_menu, container, false);
        Log.i("Main", "onCreate");
        // initialisation de la BDD et remplissage si c'est le premier lancement
        initializeDB();
        Log.i("Main", "onCreate2");

        // Crée le gestionnaires d'actions
        myGestureDetectorListener = new MyGestureDetectorListener(getActivity());

        // Création des offres
        packOffres = new ArrayList<ListOffres>();
        packOffres.add(new ListOffresPop(databaseManager));
        packOffres.add(new ListOffresDrink(databaseManager));
        idPack = 0;

        // Recuperes la textview
        titleTV = fragmentView.findViewById(R.id.categorieMenu);
        titleTV.setText(packOffres.get(idPack).getTitle());

        // Recuperes la GridView et lui assigne un adapter
        gridView = fragmentView.findViewById(R.id.menu_gridview);
        gridView.setAdapter(new OffreAdapter(getActivity(), packOffres.get(idPack).getList(), databaseManager));



        // Inflate the layout for this fragment
        return fragmentView;
    }











    private void initializeDB(){
        // Initialisation du gestionnaire de la BDD
        Log.d("DBDB", String.valueOf(getActivity()));
        databaseManager = new DatabaseManager(getActivity());

        final String SHARED_PREFERENCES_NAME = "DB";

        // Persistence pour savoir si la base à déjà été remplie
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        final String FLAG_DB = "rempli";

        if(!sharedPreferences.getBoolean(FLAG_DB, false)){
            Log.i("SharedP", "false");
            // On fait l'insertion des offres
            CreateurMenu.initialInsert(databaseManager);
            // On mets à jour l'etat du remplissage
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(FLAG_DB, true);
            editor.apply();
            Log.i("SharedP2", String.valueOf(sharedPreferences.getBoolean(FLAG_DB, false)));
        }

    }







    public void panierClick(View view){
        final String SHARED_PREFERENCES_NAME = "DB";

        // Persistence pour savoir si le panier à déjà été utilisé
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);


        if(sharedPreferences.getBoolean("panier", false) && databaseManager.readPanier().size() != 0) {
            PanierPopUp panierPopUp = new PanierPopUp(getActivity(), databaseManager);
            panierPopUp.show();
        }else{
            Toast.makeText(getActivity(), "Le panier est vide !", Toast.LENGTH_SHORT).show();
        }
    }




    /**
     * Gestion des evenements
     * @param ev
     * @return
     */
    public boolean actionDispatchTouchEvent(MotionEvent ev) {
        return myGestureDetectorListener.onTouchEvent(ev);
    }



    /**
     * Classe privée qui va gerer les geste communs
     */
    private class MyGestureDetectorListener extends GestureDetector {
        private static final int SWIPE_MIN_DISTANCE = 120;
        private static final int SWIPE_THRESHOLD_VELOCITY = 200;


        public MyGestureDetectorListener(final Context context) {
            super(context, new GestureDetector.SimpleOnGestureListener(){
                /**
                 * Notified of a fling event when it occurs with the initial on down {@link MotionEvent}
                 * and the matching up {@link MotionEvent}. The calculated velocity is supplied along
                 * the x and y axis in pixels per second.
                 *
                 * @param e1        The first down motion event that started the fling.
                 * @param e2        The move motion event that triggered the current onFling.
                 * @param velocityX The velocity of this fling measured in pixels per second
                 *                  along the x axis.
                 * @param velocityY The velocity of this fling measured in pixels per second
                 *                  along the y axis.
                 * @return true if the event is consumed, else false
                 */
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                    if (Math.abs(e1.getY()-e2.getY()) < SWIPE_MIN_DISTANCE) {
                        if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
                            Log.d("Log.DEBUG","// Left swipe...");

                            if (idPack<packOffres.size()-1){
                                idPack += 1;
                                titleTV.setText(packOffres.get(idPack).getTitle());
                                gridView.setAdapter(new OffreAdapter(context, packOffres.get(idPack).getList(), databaseManager));
                            }
                        }

                        if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
                            Log.d("Log.DEBUG","// Right swipe...");
                            if (idPack>0){
                                idPack -= 1;
                                titleTV.setText(packOffres.get(idPack).getTitle());
                                gridView.setAdapter(new OffreAdapter(context, packOffres.get(idPack).getList(),databaseManager));
                            }
                        }

                    }



                    return false;
                }
            });
        }


    }

}