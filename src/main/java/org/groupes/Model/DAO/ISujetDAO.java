package org.groupes.Model.DAO;


import java.sql.SQLException;
import java.util.List;

import org.groupes.Model.Entity.Sujet;
public interface ISujetDAO {
    void addSujet(Sujet sujet) throws SQLException;
    Sujet getSujetById(int id) throws SQLException;
    List<Sujet> getAllSujets() throws SQLException;
    void updateSujet(Sujet sujet) throws SQLException;
    void deleteSujet(int id) throws SQLException;

}
