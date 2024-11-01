package org.groupes.Model.Entity;

import java.util.ArrayList;
import java.util.List;

public class Sujet {
    private int id;
    private String intitule;
    private List<Groupe> groupes;

    public Sujet(int id, String intitule) {
        this.id = id;
        this.intitule = intitule;
        this.groupes = new ArrayList<>();
    }

   // constructeur sans id si one se base sur l'autoincrement du DB
    public Sujet(String intitule) {
        this.intitule = intitule;
        this.groupes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public List<Groupe> getGroupe() {
        return groupes;
    }

    public void setGroupe(List<Groupe> groupe) {
        this.groupes = groupe;
    }

    @Override
    public String toString() {
        return "intitule :" + intitule;
    }
}
