package org.groupes.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.groupes.Model.DAO.ISujetDAO;
import org.groupes.Model.DAO.SujetDAO;
import org.groupes.Model.Entity.Sujet;

import java.sql.SQLException;

public class SujetController {
    @FXML private TextField intituleField;
    @FXML private TableView<Sujet> sujetTable;
    @FXML private TableColumn<Sujet, Integer> idColumn;
    @FXML private TableColumn<Sujet, String> intituleColumn;

    private final ObservableList<Sujet> sujets = FXCollections.observableArrayList();
    private final ISujetDAO sujetDAO = new SujetDAO();

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        intituleColumn.setCellValueFactory(new PropertyValueFactory<>("intitule"));
        sujetTable.setItems(sujets);
        loadSujets();
    }

    private void loadSujets() {
        try {
            sujets.setAll(sujetDAO.getAllSujets());
        } catch (SQLException e) {
            showAlert("Erreur", "Échec du chargement des sujets : " + e.getMessage());
        }
    }

    @FXML
    private void handleAdd() {
        String intitule = intituleField.getText().trim();
        if (!intitule.isEmpty()) {
            try {
                Sujet newSujet = new Sujet(intitule);
                sujetDAO.addSujet(newSujet);
                sujets.add(newSujet);
                intituleField.clear();
                showAlert("Succès", "Sujet ajouté avec succès.");
            } catch (SQLException e) {
                showAlert("Erreur", "Échec de l'ajout du sujet : " + e.getMessage());
            }
        } else {
            showAlert("Avertissement", "Le champ du titre ne peut pas être vide.");
        }
    }

    @FXML
    private void handleUpdate() {
        Sujet selectedSujet = sujetTable.getSelectionModel().getSelectedItem();
        if (selectedSujet != null) {
            String newIntitule = intituleField.getText().trim();
            if (!newIntitule.isEmpty()) {
                try {
                    selectedSujet.setIntitule(newIntitule); // Assurez-vous que Sujet a une méthode setIntitule
                    sujetDAO.updateSujet(selectedSujet); // Mise à jour dans la base de données
                    sujetTable.refresh(); // Rafraîchir la table
                    intituleField.clear(); // Effacer le champ de texte après mise à jour
                    showAlert("Succès", "Sujet mis à jour avec succès.");
                } catch (SQLException e) {
                    showAlert("Erreur", "Échec de la mise à jour du sujet : " + e.getMessage());
                }
            } else {
                showAlert("Avertissement", "Le nouveau titre ne peut pas être vide.");
            }
        } else {
            showAlert("Avertissement", "Veuillez sélectionner un sujet à mettre à jour.");
        }
    }

    @FXML
    private void handleDelete() {
        Sujet selectedSujet = sujetTable.getSelectionModel().getSelectedItem();
        if (selectedSujet != null) {
            try {
                sujetDAO.deleteSujet(selectedSujet.getId());
                sujets.remove(selectedSujet);
                showAlert("Succès", "Sujet supprimé avec succès.");
            } catch (SQLException e) {
                showAlert("Erreur", "Échec de la suppression du sujet : " + e.getMessage());
            }
        } else {
            showAlert("Avertissement", "Veuillez sélectionner un sujet à supprimer.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
