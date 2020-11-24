package com.example.thefastfood.menus.listOffres;

import com.example.thefastfood.menus.dataBase.DatabaseManager;

public class ListOffresPop extends ListOffres {

    public ListOffresPop(DatabaseManager dm) {
        super();
        title = "Populaires";
        list = dm.readOffre("populaire = 1");

    }
}
