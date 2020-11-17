package com.example.thefastfood.menus.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;

import com.example.thefastfood.MainActivity;
import com.example.thefastfood.R;
import com.example.thefastfood.menus.adapter.OffreAdapter;
import com.example.thefastfood.menus.item.Offre;

import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<Offre> listOffres,listOffres2;
    OffreAdapter offreAdapter;
    MyGestureDetectorListener myGestureDetectorListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Crée le gestionnaires d'actions
        myGestureDetectorListener = new MyGestureDetectorListener(this);

        // Créé la liste des offres
        listOffres = new ArrayList<Offre>();
        for(int i=1;i<10;i++)
            listOffres.add(new Offre(R.drawable.na, "Offre "+i));

        // Créé la liste des offres 2
        listOffres2 = new ArrayList<Offre>();
        for(int i=1;i<5;i++)
            listOffres2.add(new Offre(R.drawable.na, "Offre2 "+i));

        // Recuperes la GridView
        gridView = findViewById(R.id.menu_gridview);
        offreAdapter = new OffreAdapter(this, listOffres);
        gridView.setAdapter(offreAdapter);




    }

    public void clickCardTest(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

//        offreAdapter = new OffreAdapter(this, listOffres2);
//        gridView.setAdapter(offreAdapter);


    }

    /**
     * Gestion des evenements
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        myGestureDetectorListener.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    /**
     * Classe privée qui va gerer les geste communs
     */
    private class MyGestureDetectorListener extends GestureDetector{
        private static final int SWIPE_MIN_DISTANCE = 120;
        private static final int SWIPE_THRESHOLD_VELOCITY = 200;


        public MyGestureDetectorListener(Context context) {
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
                        if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
                            Log.d("Log.DEBUG","// Left swipe...");
                        if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
                            Log.d("Log.DEBUG","// Right swipe...");
                    }



                    return false;
                }
            });
        }


    }


}