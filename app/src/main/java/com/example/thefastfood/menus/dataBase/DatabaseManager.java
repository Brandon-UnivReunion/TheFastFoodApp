package com.example.thefastfood.menus.dataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.thefastfood.menus.item.Offre;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "References.db";
    private static final int DATABASE_VERSION = 1;

    /**
     *
     *
     * @param context à utiliser pour localiser la bdd
     */
    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DATABASE", "onCreate");
        // Table des offres
        String strSql = "create table Offres ("
                + "    id integer primary key autoincrement,"
                + "    nom text not null,"
                + "    prix integer not null,"
                + "    categorie text not null,"
                + "    populaire boolean not null,"
                + "    imgR integer not null"
                + ")";
        db.execSQL( strSql );

        // Table du panier
        strSql = "create table Panier ("
                + "    id integer primary key autoincrement,"
                + "    idOffre integer not null"
                + ")";
        db.execSQL( strSql );

        // TODO historique d'achat

//        CreateurMenu.initialInsert(this);

    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Pas de maj pour le moment
    }

    /**
     * Insert une offre dans la table offre
     * @param nom
     * @param prix
     * @param categorie
     * @param populaire
     * @param imgr
     */
    public void insertOffre(String nom, int prix, String categorie, boolean populaire, int imgr ){
        Log.i("DATABASE", "insertOffre");
        nom = nom.replace("'","''");
        categorie = categorie.replace("'","''");
        String insert = "insert into Offres (nom, prix, categorie, populaire, imgr) values (" +
                "'" + nom + "', " + prix + ", '" + categorie+ "' ," + populaire +", " + imgr + " )";
        this.getWritableDatabase().execSQL( insert );

    }

    /**
     * Renvoie la selection sur la table offre sous forme de liste d'offres
     * @param condition
     * @return
     */
    public ArrayList<Offre> readOffre(@Nullable String condition){
        ArrayList<Offre> offres = new ArrayList<Offre>();
        if(condition == null){
            condition = "1";
        }

        String[] select = {"id", "nom", "prix",  "imgR"};
        Cursor cursor = this.getReadableDatabase().query("Offres",select, condition,null,null,null,null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Offre current = new Offre(cursor.getInt(0),cursor.getString(1), cursor.getInt(2),cursor.getInt(3));
            offres.add(current);
            cursor.moveToNext();
        }
        cursor.close();
        return offres;
    }

    /**
     * Insert un item au panier
     * @param idOffre
     */
    public void insertItem(int idOffre){
        Log.i("DATABASE", "insertItem");
        String insert = String.format("insert into Panier (idOffre) values ( %d )", idOffre);
        this.getWritableDatabase().execSQL( insert );
    }

    /**
     * Renvoie la liste des offres associé au panier
     * @return
     */
    public ArrayList<Offre> readPanier(){
        ArrayList<Offre> offres = new ArrayList<Offre>();

        String from = "Panier " +
                        "INNER JOIN Offres " +
                        "ON Panier.idOffre = Offres.id";
        String[] select = {"Offres.id", "Offres.nom", "Offres.prix",  "Offres.imgR", "Panier.id"};


        Cursor cursor = this.getReadableDatabase().query(from,select, null,null,null,null,null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Offre current = new Offre(cursor.getInt(0),cursor.getString(1), cursor.getInt(2),cursor.getInt(3), cursor.getInt(4));
            offres.add(current);
            cursor.moveToNext();
        }
        cursor.close();
        return offres;
    }

    /**
     * Vide le panier
     */
    public void videPanier(){
        String delete = "delete from Panier" ;
        this.getWritableDatabase().execSQL( delete );
    }

    /**
     * Validation du panier
     */
    public void validePanier(){
        String delete = "delete from Panier" ;
        this.getWritableDatabase().execSQL( delete );
    }


}
