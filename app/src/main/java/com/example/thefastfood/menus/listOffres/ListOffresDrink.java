package com.example.thefastfood.menus.listOffres;

import com.example.thefastfood.menus.dataBase.DatabaseManager;

/**
 * Liste d'offre des boisson
 */
public class ListOffresDrink extends ListOffres {

    public ListOffresDrink(DatabaseManager dm) {
        super();
        title = "Boissons";
        list = dm.readOffre("categorie = 'drinks'");

    }
}
