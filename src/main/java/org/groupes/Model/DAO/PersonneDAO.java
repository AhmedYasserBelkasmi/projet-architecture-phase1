package org.groupes.Model.DAO;
import org.groupes.Config.DatabaseConfig;
import org.groupes.Model.Entity.Personne;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class PersonneDAO implements IPersonneDAO {

    @Override
    public void addEleve(Personne eleve) throws SQLException {
        String query = "INSERT INTO personne (nom, prenom, type) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, eleve.getNom());
            statement.setString(2, eleve.getPrenom());
            statement.setString(3, "eleve");
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    eleve.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public Personne getEleveById(int id) throws SQLException {
        String query = "SELECT * FROM personne WHERE id = ? AND type = 'eleve'";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String typeStr = resultSet.getString("type");
                Personne.Type type = Personne.Type.valueOf(typeStr.toLowerCase());
                return new Personne(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        type
                );
            }
        }
        return null;
    }

    @Override
    public List<Personne> getAllEleves() throws SQLException {
        List<Personne> eleves = new ArrayList<>();
        String query = "SELECT * FROM personne WHERE type = 'eleve'";
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String typeStr = resultSet.getString("type");
                Personne.Type type = Personne.Type.valueOf(typeStr.toLowerCase());
                Personne eleve = new Personne(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        type
                );
                eleves.add(eleve);
            }
        }
        return eleves;
    }

    @Override
    public void updateEleve(Personne eleve) throws SQLException {
        String query = "UPDATE personne SET nom = ?, prenom = ? WHERE id = ? AND type = 'eleve'";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, eleve.getNom());
            statement.setString(2, eleve.getPrenom());
            statement.setInt(3, eleve.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void deleteEleve(int id) throws SQLException {
        String query = "DELETE FROM personne WHERE id = ? AND type = 'eleve'";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}