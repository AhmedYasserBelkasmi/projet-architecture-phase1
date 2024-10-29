package org.groupes.Model.DAO;

import org.groupes.Model.Entity.UniteEnseignement;

import java.sql.SQLException;
import java.util.List;

public interface IUniteEnseignementDAO {
    void addUniteEnseignement(UniteEnseignement uniteEnseignement) throws SQLException;
    UniteEnseignement getUniteEnseignementById(int id) throws SQLException;
    List<UniteEnseignement> getAllUnitesEnseignements() throws SQLException;
    void updateUniteEnseignement(UniteEnseignement uniteEnseignement) throws SQLException;
    void deleteUniteEnseignement(int id) throws SQLException;
}
