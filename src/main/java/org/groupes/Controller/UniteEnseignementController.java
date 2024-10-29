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
            showAlert("Error", "Failed to load unités d'enseignement: " + e.getMessage());
        }
    }

    @FXML
    private void handleAdd() {
        String code = codeField.getText().trim();
        String designation = designationField.getText().trim();
        if (!code.isEmpty() && !designation.isEmpty()) {
            try {
                UniteEnseignement newUE = new UniteEnseignement(code, designation);
                uniteEnseignementDAO.addUniteEnseignement(newUE);
                uniteEnseignements.add(newUE);
                clearFields();
            } catch (SQLException e) {
                showAlert("Error", "Failed to add unité d'enseignement: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleUpdate() {
        UniteEnseignement selectedUE = uniteEnseignementTable.getSelectionModel().getSelectedItem();
        if (selectedUE != null) {
            String newCode = codeField.getText().trim();
            String newDesignation = designationField.getText().trim();
            if (!newCode.isEmpty() && !newDesignation.isEmpty()) {
                try {
                    selectedUE.setCode(newCode);
                    selectedUE.setDesignation(newDesignation);
                    uniteEnseignementDAO.updateUniteEnseignement(selectedUE);
                    uniteEnseignementTable.refresh();
                    clearFields();
                } catch (SQLException e) {
                    showAlert("Error", "Failed to update unité d'enseignement: " + e.getMessage());
                }
            }
        } else {
            showAlert("Warning", "Please select an unité d'enseignement to update.");
        }
    }

    @FXML
    private void handleDelete() {
        UniteEnseignement selectedUE = uniteEnseignementTable.getSelectionModel().getSelectedItem();
        if (selectedUE != null) {
            try {
                uniteEnseignementDAO.deleteUniteEnseignement(selectedUE.getId());
                uniteEnseignements.remove(selectedUE);
            } catch (SQLException e) {
                showAlert("Error", "Failed to delete unité d'enseignement: " + e.getMessage());
            }
        } else {
            showAlert("Warning", "Please select an unité d'enseignement to delete.");
        }
    }

    private void clearFields() {
        codeField.clear();
        designationField.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}