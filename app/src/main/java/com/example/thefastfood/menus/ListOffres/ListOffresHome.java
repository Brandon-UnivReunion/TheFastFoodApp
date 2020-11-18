package com.example.thefastfood.menus.ListOffres;

import com.example.thefastfood.R;
import com.example.thefastfood.menus.item.Offre;

import java.util.ArrayList;

public class ListOffresHome extends ListOffres {

    public ListOffresHome() {
        super();
        title = "Populaire";
        list = new ArrayList<>();
        for(int i=1;i<10;i++)
            list.add(new Offre(R.drawable.na, "Offre "+i));
    }
}
