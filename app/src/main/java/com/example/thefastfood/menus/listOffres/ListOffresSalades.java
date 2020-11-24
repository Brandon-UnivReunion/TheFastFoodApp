package com.example.thefastfood.menus.listOffres;

import com.example.thefastfood.menus.dataBase.DatabaseManager;

public class ListOffresSalades  extends ListOffres {
    public ListOffresSalades(DatabaseManager dm) {
        super();
        title = "Salades";
        list = dm.readOffre("categorie = 'salads'");
    }
}