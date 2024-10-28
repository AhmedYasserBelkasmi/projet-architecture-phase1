package org.groupes.Model.DAO;

import org.groupes.Model.Entity.Groupe;

import java.util.List;

public interface IGroupeDAO {
    public List<Groupe> getAllGroupes();
    public Groupe getGroupeyByPersonne(int personneId);
    public Groupe getGroupeyById(int id);
    public boolean groupeIdExists(int id);
    public boolean groupeNameExists(String name);
    public void addGrpoupe(Groupe groupe);
}
