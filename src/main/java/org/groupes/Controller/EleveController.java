package org.groupes.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.groupes.Model.DAO.IPersonneDAO;
import org.groupes.Model.DAO.PersonneDAO;
import org.groupes.Model.Entity.Personne;

import java.sql.SQLException;

public class EleveController {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TableView<Personne> personneTable;
    @FXML private TableColumn<Personne, Integer> idColumn;
    @FXML private TableColumn<Personne, String> firstNameColumn;
    @FXML private TableColumn<Personne, String> lastNameColumn;
    @FXML private TableColumn<Personne, Personne.Type> typeColumn;

    private final ObservableList<Personne> personnes = FXCollections.observableArrayList();
    private final IPersonneDAO personneDAO = new PersonneDAO();

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        personneTable.setItems(personnes);

        loadPersonnes();
    }

    private void loadPersonnes() {
        try {
            personnes.setAll(personneDAO.getAllEleves());
        } catch (SQLException e) {
            showAlert("Erreur", "Échec du chargement des élèves : " + e.getMessage());
        }
    }

    @FXML
    private void handleAdd() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();

        if (!firstName.isEmpty() && !lastName.isEmpty()) {
            try {
                Personne newEleve = new Personne(lastName, firstName, Personne.Type.eleve);
                personneDAO.addEleve(newEleve);
                personnes.add(newEleve);
                clearFields();
                showAlert("Succès", "Élève ajouté avec succès.");
            } catch (SQLException e) {
                showAlert("Erreur", "Échec de l'ajout de l'élève : " + e.getMessage());
            }
        } else {
            showAlert("Avertissement", "Veuillez remplir tous les champs.");
        }
    }

    @FXML
    private void handleUpdate() {
        Personne selectedEleve = personneTable.getSelectionModel().getSelectedItem();
        if (selectedEleve != null) {
            String newFirstName = firstNameField.getText().trim();
            String newLastName = lastNameField.getText().trim();

            if (!newFirstName.isEmpty() && !newLastName.isEmpty()) {
                try {
                    selectedEleve.setPrenom(newFirstName);
                    selectedEleve.setNom(newLastName);
                    personneDAO.updateEleve(selectedEleve);
                    personneTable.refresh();
                    clearFields();
                    showAlert("Succès", "Élève mis à jour avec succès.");
                } catch (SQLException e) {
                    showAlert("Erreur", "Échec de la mise à jour de l'élève : " + e.getMessage());
                }
            } else {
                showAlert("Avertissement", "Veuillez remplir tous les champs.");
            }
        } else {
            showAlert("Avertissement", "Veuillez sélectionner un élève à mettre à jour.");
        }
    }

    @FXML
    private void handleDelete() {
        Personne selectedEleve = personneTable.getSelectionModel().getSelectedItem();
        if (selectedEleve != null) {
            try {
                personneDAO.deleteEleve(selectedEleve.getId());
                personnes.remove(selectedEleve);
                showAlert("Succès", "Élève supprimé avec succès.");
            } catch (SQLException e) {
                showAlert("Erreur", "Échec de la suppression de l'élève : " + e.getMessage());
            }
        } else {
            showAlert("Avertissement", "Veuillez sélectionner un élève à supprimer.");
        }
    }

    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
