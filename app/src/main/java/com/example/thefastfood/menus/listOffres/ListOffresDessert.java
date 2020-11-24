package com.example.thefastfood.menus.listOffres;

import com.example.thefastfood.menus.dataBase.DatabaseManager;

public class ListOffresDessert extends ListOffres {
    public ListOffresDessert(DatabaseManager dm) {
        super();
        title = "Dessert";
        list = dm.readOffre("categorie = 'dessert'");
    }
}