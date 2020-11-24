package com.example.thefastfood.menus.listOffres;

import com.example.thefastfood.menus.dataBase.DatabaseManager;

public class ListOffresBurger extends ListOffres {
    public ListOffresBurger(DatabaseManager dm) {
        super();
        title = "Burger";
        list = dm.readOffre("categorie = 'burger'");
    }
}

