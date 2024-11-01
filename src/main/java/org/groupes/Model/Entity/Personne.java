package org.groupes.Model.Entity;

import java.util.ArrayList;
import java.util.List;

public class Personne {


    public Personne() {

    }

    public enum Type {eleve, enseignant}

    private int id;
    private String nom;
    private String prenom;
    private Type type;
    private List<Groupe> groupes;


    public Personne(int id, String nom, String prenom, Type type) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.type = type;
        this.groupes = new ArrayList<>();
    }


    public Personne(String nom, String prenom, Type type) {
        this.nom = nom;
        this.prenom = prenom;
        this.type = type;
        this.groupes = new ArrayList<>();
    }

    public Personne(int id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Personne(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setGroupes(List<Groupe> groupes) {
        this.groupes = groupes;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom; // Corrected this line
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom; // Corrected this line
    }

    public Type getType() {
        return type; // Getter for type
    }

    public List<Groupe> getGroupe() {
        return groupes;
    }

    public void setGroupe(List<Groupe> groupe) {
        this.groupes = groupe;
    }

    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", type=" + type +
                ", groupes=" + groupes +
                '}';
    }
}