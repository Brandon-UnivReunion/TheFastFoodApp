package com.example.thefastfood.menus.listOffres;

import com.example.thefastfood.menus.dataBase.DatabaseManager;

public class ListOffresBurger extends ListOffres {
    public ListOffresBurger(DatabaseManager dm) {
        super();
        title = "Burgers";
        list = dm.readOffre("categorie = 'burgers'");
    }
}

