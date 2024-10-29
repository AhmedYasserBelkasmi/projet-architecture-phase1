package org.groupes.Model.DAO;

import org.groupes.Config.DatabaseConfig;
import org.groupes.Model.Entity.UniteEnseignement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UniteEnseignementDAO implements IUniteEnseignementDAO {

    @Override
    public void addUniteEnseignement(UniteEnseignement uniteEnseignement) throws SQLException {
        String query = "INSERT INTO unite_enseignement (code, designation) VALUES (?, ?)";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, uniteEnseignement.getCode());
            statement.setString(2, uniteEnseignement.getDesignation());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (( generatedKeys).next()) {
                    uniteEnseignement.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public UniteEnseignement getUniteEnseignementById(int id) throws SQLException {
        String query = "SELECT * FROM unite_enseignement WHERE id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new UniteEnseignement(
                        resultSet.getInt("id"),
                        resultSet.getString("code"),
                        resultSet.getString("designation")
                );
            }
        }
        return null;
    }

    @Override
    public List<UniteEnseignement> getAllUnitesEnseignements() throws SQLException {
        List<UniteEnseignement> unites = new ArrayList<>();
        String query = "SELECT * FROM unite_enseignement";
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                UniteEnseignement unite = new UniteEnseignement(
                        resultSet.getInt("id"),
                        resultSet.getString("code"),
                        resultSet.getString("designation")
                );
                unites.add(unite);
            }
        }
        return unites;
    }

    @Override
    public void updateUniteEnseignement(UniteEnseignement uniteEnseignement) throws SQLException {
        String query = "UPDATE unite_enseignement SET code = ?, designation = ? WHERE id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, uniteEnseignement.getCode());
            statement.setString(2, uniteEnseignement.getDesignation());
            statement.setInt(3, uniteEnseignement.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void deleteUniteEnseignement(int id) throws SQLException {
        String query = "DELETE FROM unite_enseignement WHERE id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
