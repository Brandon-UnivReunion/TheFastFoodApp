package com.example.thefastfood.menus.listOffres;

import com.example.thefastfood.menus.dataBase.DatabaseManager;

/**
 * Liste d'offres des Dessert
 */
public class ListOffresDessert extends ListOffres {
    public ListOffresDessert(DatabaseManager dm) {
        super();
        title = "Desserts";
        list = dm.readOffre("categorie = 'desserts'");
    }
}