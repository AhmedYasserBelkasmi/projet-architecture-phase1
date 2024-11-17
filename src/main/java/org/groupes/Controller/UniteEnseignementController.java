package org.groupes.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.groupes.Model.DAO.IUniteEnseignementDAO;
import org.groupes.Model.DAO.UniteEnseignementDAO;
import org.groupes.Model.Entity.UniteEnseignement;

import java.sql.SQLException;

public class UniteEnseignementController {

    @FXML private TextField codeField;
    @FXML private TextField designationField;
    @FXML private TableView<UniteEnseignement> uniteEnseignementTable;
    @FXML private TableColumn<UniteEnseignement, Integer> idColumn;
    @FXML private TableColumn<UniteEnseignement, String> codeColumn;
    @FXML private TableColumn<UniteEnseignement, String> designationColumn;

    private final ObservableList<UniteEnseignement> uniteEnseignements = FXCollections.observableArrayList();
    private final IUniteEnseignementDAO uniteEnseignementDAO = new UniteEnseignementDAO();

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        designationColumn.setCellValueFactory(new PropertyValueFactory<>("designation"));
        uniteEnseignementTable.setItems(uniteEnseignements);
        loadUniteEnseignements();
    }

    private void loadUniteEnseignements() {
        try {
            uniteEnseignements.setAll(uniteEnseignementDAO.getAllUnitesEnseignements());
        } catch (SQLException e) {
            showErrorAlert("Erreur", "Échec du chargement des unités d'enseignement: " + e.getMessage());
        }
    }

    @FXML
    private void handleAdd() {
        String code = codeField.getText().trim();
        String designation = designationField.getText().trim();
        if (code.isEmpty() || designation.isEmpty()) {
            showErrorAlert("Erreur", "Tous les champs doivent être remplis.");
        } else {
            try {
                UniteEnseignement newUE = new UniteEnseignement(code, designation);
                uniteEnseignementDAO.addUniteEnseignement(newUE);
                uniteEnseignements.add(newUE);
                showInfoAlert("Succès", "Unité d'enseignement ajoutée avec succès.");
                clearFields();
            } catch (SQLException e) {
                showErrorAlert("Erreur", "Échec de l'ajout de l'unité d'enseignement: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleUpdate() {
        UniteEnseignement selectedUE = uniteEnseignementTable.getSelectionModel().getSelectedItem();
        if (selectedUE != null) {
            String newCode = codeField.getText().trim();
            String newDesignation = designationField.getText().trim();
            if (newCode.isEmpty() || newDesignation.isEmpty()) {
                showErrorAlert("Erreur", "Tous les champs doivent être remplis.");
            } else {
                try {
                    selectedUE.setCode(newCode);
                    selectedUE.setDesignation(newDesignation);
                    uniteEnseignementDAO.updateUniteEnseignement(selectedUE);
                    uniteEnseignementTable.refresh();
                    showInfoAlert("Succès", "Unité d'enseignement mise à jour avec succès.");
                    clearFields();
                } catch (SQLException e) {
                    showErrorAlert("Erreur", "Échec de la mise à jour de l'unité d'enseignement: " + e.getMessage());
                }
            }
        } else {
            showErrorAlert("Avertissement", "Veuillez sélectionner une unité d'enseignement à mettre à jour.");
        }
    }

    @FXML
    private void handleDelete() {
        UniteEnseignement selectedUE = uniteEnseignementTable.getSelectionModel().getSelectedItem();
        if (selectedUE != null) {
            try {
                uniteEnseignementDAO.deleteUniteEnseignement(selectedUE.getId());
                uniteEnseignements.remove(selectedUE);
                showInfoAlert("Succès", "Unité d'enseignement supprimée avec succès.");
            } catch (SQLException e) {
                showErrorAlert("Erreur", "Échec de la suppression de l'unité d'enseignement: " + e.getMessage());
            }
        } else {
            showErrorAlert("Avertissement", "Veuillez sélectionner une unité d'enseignement à supprimer.");
        }
    }

    private void clearFields() {
        codeField.clear();
        designationField.clear();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfoAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
