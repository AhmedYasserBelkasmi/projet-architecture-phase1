package org.groupes.Model.Entity;

import java.util.ArrayList;
import java.util.List;

public class Groupe {
    private int id;
    private String identifiant;
    private Sujet sujet;
    private UniteEnseignement uniteEnseignement;
    private List<Personne> personnes;

    public Groupe() {
        this.personnes = new ArrayList<>();
    }

    public Groupe(String identifiant, Sujet sujet, UniteEnseignement uniteEnseignement) {
        this.identifiant = identifiant;
        this.sujet = sujet;
        this.uniteEnseignement = uniteEnseignement;
        this.personnes = new ArrayList<>();
    }

    public Groupe(int id, String identifiant, Sujet sujet, UniteEnseignement uniteEnseignement) {
        this.id = id;
        this.identifiant = identifiant;
        this.sujet = sujet;
        this.uniteEnseignement = uniteEnseignement;
        this.personnes = new ArrayList<>();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public Sujet getSujet() {
        return sujet;
    }

    public void setSujet(Sujet sujet) {
        this.sujet = sujet;
    }

    public UniteEnseignement getUniteEnseignement() {
        return uniteEnseignement;
    }

    public void setUniteEnseignement(UniteEnseignement uniteEnseignement) {
        this.uniteEnseignement = uniteEnseignement;
    }

    public List<Personne> getPersonnes() {
        return personnes;
    }

    public void setPersonnes(List<Personne> personnes) {
        this.personnes = personnes;
    }

    public void addPersonne(Personne personne) {
        this.personnes.add(personne);
    }

    public void removePersonne(Personne personne) {
        this.personnes.remove(personne);
    }

    @Override
    public String toString() {
        return "Groupe{" +
                "id=" + id +
                ", identifiant='" + identifiant + '\'' +
                ", sujet=" + sujet +
                ", uniteEnseignement=" + uniteEnseignement +
                ", personnes=" + personnes +
                '}';
    }
}