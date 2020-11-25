package com.example.thefastfood.menus.listOffres;

import com.example.thefastfood.menus.dataBase.DatabaseManager;

/**
 * Liste des offres salades
 */
public class ListOffresSalades  extends ListOffres {
    public ListOffresSalades(DatabaseManager dm) {
        super();
        title = "Salades";
        list = dm.readOffre("categorie = 'salads'");
    }
}