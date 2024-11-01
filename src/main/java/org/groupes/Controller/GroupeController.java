package org.groupes.Controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.groupes.Model.DAO.*;
import org.groupes.Model.Entity.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupeController {
    @FXML
    private TextField identifiantField;
    @FXML
    private ComboBox<Sujet> sujetComboBox;
    @FXML
    private ComboBox<UniteEnseignement> uniteEnseignementComboBox;
    @FXML
    private ListView<Personne> personneListView;
    @FXML
    private TableView<Groupe> groupeTable;
    @FXML
    private TableColumn<Groupe, Long> idColumn;
    @FXML
    private TableColumn<Groupe, String> identifiantColumn;
    @FXML
    private TableColumn<Groupe, Sujet> sujetColumn;
    @FXML
    private TableColumn<Groupe, UniteEnseignement> uniteColumn;

    private final GroupeDAO groupeDAO = new GroupeDAO();
    private final PersonneDAO personneDAO = new PersonneDAO();

    @FXML
    public void initialize() {
        // Initialize columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id")); // Assuming `id` is of type Long
        identifiantColumn.setCellValueFactory(new PropertyValueFactory<>("identifiant")); // Assuming `identifiant` is of type String
        sujetColumn.setCellValueFactory(new PropertyValueFactory<>("sujet")); // Assuming `sujet` is of type Sujet
        uniteColumn.setCellValueFactory(new PropertyValueFactory<>("uniteEnseignement")); // Assuming `uniteEnseignement` is of type UniteEnseignement

        // Load existing groupes into the TableView
        loadGroupeData();
    }

    private void loadGroupeData() {
        try {
            List<Groupe> groupes = groupeDAO.getAllGroupes(); // Ensure this method exists in your DAO
            ObservableList<Groupe> observableGroupes = FXCollections.observableArrayList(groupes);
            groupeTable.setItems(observableGroupes);
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions
        }
    }

    @FXML
    public void handleAdd() {
        String identifiant = identifiantField.getText();
        Sujet selectedSujet = sujetComboBox.getValue();
        UniteEnseignement selectedUnite = uniteEnseignementComboBox.getValue();

        if (selectedSujet != null && selectedUnite != null && !identifiant.isEmpty()) {
            Groupe newGroupe = new Groupe();
            newGroupe.setIdentifiant(identifiant);
            newGroupe.setSujet(selectedSujet);
            newGroupe.setUniteEnseignement(selectedUnite);

            // Get selected personnes
            ObservableList<Personne> selectedPersonnes = personneListView.getSelectionModel().getSelectedItems();
            newGroupe.setPersonnes(new ArrayList<>(selectedPersonnes));

            try {
                groupeDAO.addGroupe(newGroupe);
                loadGroupeData(); // Refresh the table
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions
            }
        }
    }

    @FXML
    public void handleUpdate() {
        Groupe selectedGroupe = groupeTable.getSelectionModel().getSelectedItem();

        if (selectedGroupe != null) {
            selectedGroupe.setIdentifiant(identifiantField.getText());
            selectedGroupe.setSujet(sujetComboBox.getValue());
            selectedGroupe.setUniteEnseignement(uniteEnseignementComboBox.getValue());

            // Get selected personnes
            ObservableList<Personne> selectedPersonnes = personneListView.getSelectionModel().getSelectedItems();
            selectedGroupe.setPersonnes(new ArrayList<>(selectedPersonnes));

            try {
                groupeDAO.updateGroupe(selectedGroupe);
                loadGroupeData(); // Refresh the table
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions
            }
        }
    }

    @FXML
    public void handleDelete() {
        Groupe selectedGroupe = groupeTable.getSelectionModel().getSelectedItem();

        if (selectedGroupe != null) {
            try {
                groupeDAO.deleteGroupe(selectedGroupe.getId()); // Assuming deleteGroupe accepts an ID
                loadGroupeData(); // Refresh the table
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions
            }
        }
    }
    private void clearFields() {
        identifiantField.clear();
        sujetComboBox.getSelectionModel().clearSelection();
        uniteEnseignementComboBox.getSelectionModel().clearSelection();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
