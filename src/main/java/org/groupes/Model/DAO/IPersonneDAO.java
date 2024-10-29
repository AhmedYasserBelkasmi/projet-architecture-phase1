package org.groupes.Model.DAO;

import org.groupes.Model.Entity.Personne;

import java.sql.SQLException;
import java.util.List;

public interface IPersonneDAO {

        void addEleve(Personne eleve) throws SQLException; // Add a student
        Personne getEleveById(int id) throws SQLException; // Get a student by ID
        List<Personne> getAllEleves() throws SQLException; // Get all students
        void updateEleve(Personne eleve) throws SQLException; // Update a student
        void deleteEleve(int id) throws SQLException; // Delete a student


}
