package com.example.thefastfood.menus.item;

import androidx.annotation.Nullable;

public class Offre{
    // code de l'image representant l'offre dans les ressources
    int pathImg;
    // name nom de l'offre
    String name;
    // prix de l'offre
    int prix;
    // id de l'offre
    int id;
    // id dans le panier si il provient du panier
    int idP;


    public Offre(int id, String name, int prix, int pathImg, int idP) {
        this.pathImg = pathImg;
        this.name = name;
        this.prix = prix;
        this.id = id;
        this.idP = idP;
    }

    public Offre(int id, String name, int prix, int pathImg) {
        this.pathImg = pathImg;
        this.name = name;
        this.prix = prix;
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public int getPrix() {
        return prix;
    }
    public int getPathImg() {
        return pathImg;
    }

    public String getName() {
        return name;
    }

}
