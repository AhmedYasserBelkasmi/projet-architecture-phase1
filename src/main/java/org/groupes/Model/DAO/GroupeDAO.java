package org.groupes.Model.DAO;

import org.groupes.Config.DatabaseConfig;
import org.groupes.Model.Entity.Groupe;
import org.groupes.Model.Entity.Personne;
import org.groupes.Model.Entity.Sujet;
import org.groupes.Model.Entity.UniteEnseignement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupeDAO implements IGroupeDAO {

    @Override
    public void addGroupe(Groupe groupe) throws SQLException {
        String query = "INSERT INTO groupe (identifiant, id_sujet, id_ue) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, groupe.getIdentifiant());
            statement.setInt(2, groupe.getSujet().getId());
            statement.setInt(3, groupe.getUniteEnseignement().getId());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    groupe.setId(generatedKeys.getInt(1));
                }
            }
            addPersonnesToGroupe(groupe);
        }
    }

    private void addPersonnesToGroupe(Groupe groupe) throws SQLException {
        String query = "INSERT INTO groupe_personne (groupe_id, personne_id) VALUES (?, ?)";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            for (Personne personne : groupe.getPersonnes()) {
                statement.setInt(1, groupe.getId());
                statement.setInt(2, personne.getId());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    @Override
    public Groupe getGroupeById(int id) throws SQLException {
        String query = "SELECT g.*, s.intitule AS sujet_intitule, ue.code AS ue_code, ue.designation AS ue_designation " +
                "FROM groupe g " +
                "JOIN sujet s ON g.id_sujet = s.id " +
                "JOIN unite_enseignement ue ON g.id_ue = ue.id " +
                "WHERE g.id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Groupe groupe = new Groupe();
                    groupe.setId(resultSet.getInt("id"));
                    groupe.setIdentifiant(resultSet.getString("identifiant"));
                    groupe.setSujet(new Sujet(resultSet.getInt("id_sujet"), resultSet.getString("sujet_intitule")));
                    groupe.setUniteEnseignement(new UniteEnseignement(resultSet.getInt("id_ue"), resultSet.getString("ue_code"), resultSet.getString("ue_designation")));
                    groupe.setPersonnes(getPersonnesByGroupeId(id));
                    return groupe;
                }
            }
        }
        return null;
    }

    @Override
    public List<Groupe> getAllGroupes() throws SQLException {
        List<Groupe> groupes = new ArrayList<>();
        String query = "SELECT g.*, s.intitule AS sujet_intitule, ue.code AS ue_code, ue.designation AS ue_designation " +
                "FROM groupe g " +
                "JOIN sujet s ON g.id_sujet = s.id " +
                "JOIN unite_enseignement ue ON g.id_ue = ue.id";
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Groupe groupe = new Groupe();
                groupe.setId(resultSet.getInt("id"));
                groupe.setIdentifiant(resultSet.getString("identifiant"));
                groupe.setSujet(new Sujet(resultSet.getInt("id_sujet"), resultSet.getString("sujet_intitule")));
                groupe.setUniteEnseignement(new UniteEnseignement(resultSet.getInt("id_ue"), resultSet.getString("ue_code"), resultSet.getString("ue_designation")));
                groupe.setPersonnes(getPersonnesByGroupeId(groupe.getId()));
                groupes.add(groupe);
            }
        }
        return groupes;
    }

    @Override
    public void updateGroupe(Groupe groupe) throws SQLException {
        String query = "UPDATE groupe SET identifiant = ?, id_sujet = ?, id_ue = ? WHERE id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, groupe.getIdentifiant());
            statement.setInt(2, groupe.getSujet().getId());
            statement.setInt(3, groupe.getUniteEnseignement().getId());
            statement.setInt(4, groupe.getId());
            statement.executeUpdate();

            deletePersonnesByGroupeId(groupe.getId());
            addPersonnesToGroupe(groupe);
        }
    }

    @Override
    public void deleteGroupe(int id) throws SQLException {
        String query = "DELETE FROM groupe WHERE id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    private List<Personne> getPersonnesByGroupeId(int groupeId) throws SQLException {
        List<Personne> personnes = new ArrayList<>();
        String query = "SELECT p.* FROM personne p " +
                "JOIN groupe_personne gp ON p.id = gp.personne_id " +
                "WHERE gp.groupe_id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, groupeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Personne personne = new Personne();
                    personne.setId(resultSet.getInt("id"));
                    personne.setNom(resultSet.getString("nom"));
                    personne.setPrenom(resultSet.getString("prenom"));
                    personne.setType(Personne.Type.valueOf(resultSet.getString("type")));
                    personnes.add(personne);
                }
            }
        }
        return personnes;
    }

    private void deletePersonnesByGroupeId(int groupeId) throws SQLException {
        String query = "DELETE FROM groupe_personne WHERE groupe_id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, groupeId);
            statement.executeUpdate();
        }
    }
}