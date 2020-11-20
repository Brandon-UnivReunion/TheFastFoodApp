package com.example.thefastfood.menus.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.thefastfood.R;
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
        String strSql = "create table Offres ("
                + "    id integer primary key autoincrement,"
                + "    nom text not null,"
                + "    prix integer not null,"
                + "    categorie text not null,"
                + "    populaire boolean not null,"
                + "    imgR int not null"
                + ")";
        db.execSQL( strSql );

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

    public void insertOffre(String nom, int prix, String categorie, boolean populaire, int imgr ){
        Log.i("DATABASE", "insertOffre");
        nom = nom.replace("'","''");
        categorie = categorie.replace("'","''");
        String inser = "insert into Offres (nom, prix, categorie, populaire, imgr) values (" +
                "'" + nom + "', " + prix + ", '" + categorie+ "' ," + populaire +", " + imgr + " )";
        this.getWritableDatabase().execSQL( inser );

    }

    public ArrayList<Offre> readOffre(@Nullable String condition){
        ArrayList<Offre> offres = new ArrayList<Offre>();

        if(condition == null){
            condition = "1";
        }

        Cursor cursor = this.getReadableDatabase().query("Offres",null, condition,null,null,null,null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Offre current = new Offre(cursor.getInt(0),cursor.getString(1), cursor.getInt(2),cursor.getString(3),cursor.getInt(4)==1,cursor.getInt(5));
            offres.add(current);
            cursor.moveToNext();
        }
        cursor.close();

        return offres;
    }




}
