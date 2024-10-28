package org.groupes.Model.Entity;

import java.util.List;

public class Groupe {
    private int id;
    private List<Personne> personne; // les membre du groupe minimuml 1
    private Sujet sujet; // le groupe a 1 et seulement 1 sujet
    private UniteEnseignement uniteEnseignement;  // le groupe appartient a une seul UE


}
