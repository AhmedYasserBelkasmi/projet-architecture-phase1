package org.groupes.Model.DAO;

import org.groupes.Model.Entity.Groupe;

import java.sql.SQLException;
import java.util.List;

public interface IGroupeDAO {

    void addGroupe(Groupe groupe) throws SQLException;
    Groupe getGroupeById(int id) throws SQLException;
    List<Groupe> getAllGroupes() throws SQLException;
    void updateGroupe(Groupe groupe) throws SQLException;
    void deleteGroupe(int id) throws SQLException;
}
