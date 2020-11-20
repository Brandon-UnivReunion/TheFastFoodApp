package com.example.thefastfood.menus.item;

import com.example.thefastfood.R;

public class Offre {
    // code de l'image representant l'offre dans les ressources
    int pathImg;
    // name nom de l'offre
    String name;
    // prix de l'offre
    int prix;
    // id de l'offre
    int id;
    // categorie de l'offre
    String categorie;
    // Popularité
    boolean popularité;

    public Offre(int pathImg, String name) {
        this.pathImg = pathImg;
        this.name = name;
    }



    public Offre(int id, String name, int prix, String categorie, boolean popularité , int pathImg) {
        this.pathImg = pathImg;
        this.name = name;
        this.prix = prix;
        this.id = id;
        this.categorie = categorie;
        this.popularité = popularité;
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

    @Override
    public String toString() {
        return "Offre{" +
                "pathImg=" + pathImg +
                ", name='" + name + '\'' +
                ", prix=" + prix +
                ", id=" + id +
                ", categorie='" + categorie + '\'' +
                ", popularité=" + popularité +
                '}';
    }
}
