package com.example.thefastfood.menus.listOffres;

import com.example.thefastfood.menus.item.Offre;

import java.util.ArrayList;

public class ListOffres {
    protected String title;
    protected ArrayList<Offre> list;

    public ListOffres() {
        this.title = null;
        this.list = null;
    }

    public ListOffres(String title, ArrayList<Offre> list) {
        this.title = title;
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Offre> getList() {
        return list;
    }

    public void setList(ArrayList<Offre> list) {
        this.list = list;
    }
}
