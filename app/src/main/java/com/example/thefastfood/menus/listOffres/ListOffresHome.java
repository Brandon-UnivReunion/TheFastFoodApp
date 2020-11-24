package com.example.thefastfood.menus.listOffres;

import com.example.thefastfood.menus.dataBase.DatabaseManager;

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
