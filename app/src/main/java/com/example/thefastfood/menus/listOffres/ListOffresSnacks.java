package com.example.thefastfood.menus.listOffres;

import com.example.thefastfood.menus.dataBase.DatabaseManager;

public class ListOffresSnacks extends ListOffres {
    public ListOffresSnacks(DatabaseManager dm) {
        super();
        title = "Snacks";
        list = dm.readOffre("categorie = 'snacks'");
    }
}
