package org.groupes.Model.Entity;

import java.util.ArrayList;
import java.util.List;

public class Personne {
    private int id;
    private String firstName;
    private String lastName;
    private int age ;
    private List<Groupe> groupes; // l'etudiant peut etre attribue a plusieure groupe ou none pour les eleves


    public Personne(int id, String firstName, String lastName, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.groupes = new ArrayList<>();
    }

    public Personne(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.groupes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Groupe> getGroupe() {
        return groupes;
    }

    public void setGroupe(List<Groupe> groupe) {
        this.groupes = groupe;
    }
}