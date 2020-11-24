package com.example.thefastfood.menus.dataBase;

import com.example.thefastfood.R;

public class CreateurMenu {
    public static boolean POPULAR = true;
    public static String SNACKS = "snacks";
    public static String DRINKS = "drinks";
    public static String BURGERS = "burgers";
    public static String DESSERTS = "desserts";
    public static String SALADS = "salads";

    public static void initialInsert(DatabaseManager dbm){

        //Burgers
        dbm.insertOffre("L'Incontournable", 6, BURGERS, POPULAR, R.drawable.meat_burger);
        dbm.insertOffre("L'Irrésistible", 5, BURGERS, POPULAR, R.drawable.chiken_burger);
        dbm.insertOffre("L'Indomptable", 4, BURGERS, false, R.drawable.fish_burger);
        dbm.insertOffre("Le Rabatjoie", 4, BURGERS, false, R.drawable.vegan_burger);

        //Salad
        dbm.insertOffre("La Printanière", 6, SALADS, false, R.drawable.only_salad);
        dbm.insertOffre("La Fermière", 6, SALADS, false, R.drawable.chiken_salad);

        //Snacks
        dbm.insertOffre("Frites S", 1, SNACKS, false, R.drawable.small_fries;
        dbm.insertOffre("Frites M", 2, SNACKS, false, R.drawable.medium_fries);
        dbm.insertOffre("Frites L", 4, SNACKS, POPULAR, R.drawable.large_fries);
        dbm.insertOffre("Les Fondants x5", 5, SNACKS, POPULAR, R.drawable.cheese_snack);
        dbm.insertOffre("Les Croustillants x5", 5, SNACKS, POPULAR, R.drawable.chiken_snack);
        dbm.insertOffre("Les Panés", 4, SNACKS, false, R.drawable.fish_snack);

        //Desserts
        dbm.insertOffre("Donut", 4, DESSERTS, false, R.drawable.donut);
        dbm.insertOffre("Muffin", 5, DESSERTS, POPULAR, R.drawable.muffin);
        dbm.insertOffre("Cornet", 3, DESSERTS, false, R.drawable.cornet);

        //Drinks
        dbm.insertOffre("Eau plate", 1, DRINKS, false, R.drawable.water_drink);
        dbm.insertOffre("Soda", 3, DRINKS, POPULAR, R.drawable.soda_drink);
        dbm.insertOffre("Jus", 2, DRINKS, false, R.drawable.juice_drink);
        dbm.insertOffre("Bière", 5, DRINKS, POPULAR, R.drawable.beer_drink);


    }

}
