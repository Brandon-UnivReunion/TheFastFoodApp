package com.example.thefastfood.menus.ListOffres;

import com.example.thefastfood.R;
import com.example.thefastfood.menus.DataBase.DatabaseManager;
import com.example.thefastfood.menus.item.Offre;

import java.util.ArrayList;

public class ListOffresHome extends ListOffres {

    public ListOffresHome(DatabaseManager dm) {
        super();
        title = "Populaire";
        list = dm.readOffre("populaire = TRUE");
                //new ArrayList<>();
        //for(int i=1;i<10;i++)
            //list.add(new Offre(R.drawable.na, "Offre "+i));
    }
}
