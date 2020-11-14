package com.example.thefastfood.menus.item;

import com.example.thefastfood.R;

public class Offre {
    // code de l'image representant l'offre dans les ressources
    int pathImg;
    // name nom de l'offre
    String name;

    public Offre(int pathImg, String name) {
        this.pathImg = pathImg;
        this.name = name;
    }

    public int getPathImg() {
        return pathImg;
    }

    public void setPathImg(int pathImg) {
        this.pathImg = pathImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
