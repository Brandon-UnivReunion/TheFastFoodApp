package com.example.thefastfood.menus.dataBase;

import com.example.thefastfood.R;

public class CreateurMenu {
    public static void initialInsert(DatabaseManager dbm){
        for(int i=1;i<4;i++)
            dbm.insertOffre("OffreX "+i, 5, "boisson", true, R.drawable.na);
        for(int i=1;i<2;i++)
            dbm.insertOffre("OffreY "+i, 2, "snack", true, R.drawable.na);
        for(int i=1;i<5;i++)
            dbm.insertOffre("OffreZ "+i, 5, "boisson", false, R.drawable.na);

    }

}
