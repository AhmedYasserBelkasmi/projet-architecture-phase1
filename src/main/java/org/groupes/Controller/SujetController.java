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
    @FXML private TableView<Object> sujetTable;
    @FXML private TableColumn<Sujet, Integer> idColumn;
    @FXML private TableColumn<Sujet, String> intituleColumn;

    private final ObservableList<Object> sujets = FXCollections.observableArrayList();
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
            showAlert("Error", "Failed to load sujets: " + e.getMessage());
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
            } catch (SQLException e) {
                showAlert("Error", "Failed to add sujet: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleUpdate() {
        Sujet selectedSujet = (Sujet) sujetTable.getSelectionModel().getSelectedItem();
        if (selectedSujet != null) {
            String newIntitule = intituleField.getText().trim();
            if (!newIntitule.isEmpty()) {
                try {
                    selectedSujet.setIntitule(newIntitule); // Ensure Sujet has a setIntitule method
                    sujetDAO.updateSujet(selectedSujet); // Update database
                    sujetTable.refresh(); // Refresh table view
                    intituleField.clear(); // Clear text field after updating
                } catch (SQLException e) {
                    showAlert("Error", "Failed to update sujet: " + e.getMessage());
                }
            } else {
                showAlert("Warning", "The new title cannot be empty.");
            }
        } else {
            showAlert("Warning", "Please select a sujet to update.");
        }
    }

    @FXML
    private void handleDelete() {
        Sujet selectedSujet = (Sujet) sujetTable.getSelectionModel().getSelectedItem();
        if (selectedSujet != null) {
            try {
                sujetDAO.deleteSujet(selectedSujet.getId());
                sujets.remove(selectedSujet);
            } catch (SQLException e) {
                showAlert("Error", "Failed to delete sujet: " + e.getMessage());
            }
        } else {
            showAlert("Warning", "Please select a sujet to delete.");
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
