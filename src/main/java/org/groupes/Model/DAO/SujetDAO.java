package org.groupes.Model.DAO;

import org.groupes.Config.DatabaseConfig;
import org.groupes.Model.Entity.Sujet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SujetDAO implements ISujetDAO {

    @Override
    public void addSujet(Sujet sujet) throws SQLException {
        String query = "INSERT INTO sujet (intitule) VALUES (?)";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, sujet.getIntitule());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    sujet.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public Sujet getSujetById(int id) throws SQLException {
        String query = "SELECT * FROM sujet WHERE id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Sujet(
                        resultSet.getInt("id"),
                        resultSet.getString("intitule")
                );
            }
        }
        return null;
    }

    @Override
    public List<Sujet> getAllSujets() throws SQLException {
        List<Sujet> sujets = new ArrayList<>();
        String query = "SELECT * FROM sujet";
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Sujet sujet = new Sujet(
                        resultSet.getInt("id"),
                        resultSet.getString("intitule")
                );
                sujets.add(sujet);
            }
        }
        return sujets;
    }

    @Override
    public void updateSujet(Sujet sujet) throws SQLException {
        String query = "UPDATE sujet SET intitule = ? WHERE id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, sujet.getIntitule());
            statement.setInt(2, sujet.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void deleteSujet(int id) throws SQLException {
        String query = "DELETE FROM sujet WHERE id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
