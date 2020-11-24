package com.example.thefastfood.menus.item;

public class Item {
    // code de l'image representant l'offre dans les ressources
    int pathImg;
    // name nom de l'offre
    String name;
    // prix de l'offre
    int prix;
    // id de l'offre
    int id;

    public Item(int pathImg, String name) {
        this.pathImg = pathImg;
        this.name = name;
    }



    public Item(int id, String name, int prix, int pathImg) {
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
