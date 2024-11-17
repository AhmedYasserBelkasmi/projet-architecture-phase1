package org.groupes.Controller;

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
    @FXML
    private TableColumn<Groupe, Personne> personneColumn;

    @FXML
    private TextField searchField;

    private final GroupeDAO groupeDAO = new GroupeDAO();
    private final SujetDAO sujetDAO = new SujetDAO();
    private final UniteEnseignementDAO uniteEnseignementDAO = new UniteEnseignementDAO();
    private final PersonneDAO personneDAO = new PersonneDAO();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        identifiantColumn.setCellValueFactory(new PropertyValueFactory<>("identifiant"));
        sujetColumn.setCellValueFactory(new PropertyValueFactory<>("sujet"));
        uniteColumn.setCellValueFactory(new PropertyValueFactory<>("uniteEnseignement"));
        personneColumn.setCellValueFactory(new PropertyValueFactory<>("personnes"));
        loadGroupeData();
        loadSujets();
        loadUnitesEnseignement();
        loadPersonnes();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                try {
                    personneListView.setItems(FXCollections.observableArrayList(personneDAO.getAllEleves()));
                } catch (SQLException e) {
                    showErrorAlert("Erreur", "Échec de la recherche des élèves : " + e.getMessage());
                }
            } else {
                List<Personne> filteredPersonnes = new ArrayList<>();
                try {
                    for (Personne p : personneDAO.getAllEleves()) {
                        if (p.getNom().toLowerCase().contains(newValue.toLowerCase())) {
                            filteredPersonnes.add(p);
                        }
                    }
                } catch (SQLException e) {
                    showErrorAlert("Erreur", "Échec de la recherche des élèves : " + e.getMessage());
                }
                personneListView.setItems(FXCollections.observableArrayList(filteredPersonnes));
            }
        });
    }
    private void loadGroupeData() {
        try {
            List<Groupe> groupes = groupeDAO.getAllGroupes();
            ObservableList<Groupe> observableGroupes = FXCollections.observableArrayList(groupes);
            groupeTable.setItems(observableGroupes);
        } catch (SQLException e) {
            showErrorAlert("Erreur", "Échec du chargement des groupes : " + e.getMessage());
        }
    }

    private void loadSujets() {
        try {
            List<Sujet> sujets = sujetDAO.getAllSujets();
            ObservableList<Sujet> observableSujets = FXCollections.observableArrayList(sujets);
            sujetComboBox.setItems(observableSujets);
        } catch (SQLException e) {
            showErrorAlert("Erreur", "Échec du chargement des sujets : " + e.getMessage());
        }
    }

    private void loadUnitesEnseignement() {
        try {
            List<UniteEnseignement> unites = uniteEnseignementDAO.getAllUnitesEnseignements();
            ObservableList<UniteEnseignement> observableUnites = FXCollections.observableArrayList(unites);
            uniteEnseignementComboBox.setItems(observableUnites);
        } catch (SQLException e) {
            showErrorAlert("Erreur", "Échec du chargement des unités d'enseignement : " + e.getMessage());
        }
    }

    private void loadPersonnes() {
        try {
            List<Personne> personnes = personneDAO.getAllEleves();
            ObservableList<Personne> observablePersonnes = FXCollections.observableArrayList(personnes);
            personneListView.setItems(observablePersonnes);
            personneListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        } catch (SQLException e) {
            showErrorAlert("Erreur", "Échec du chargement des élèves : " + e.getMessage());
        }
    }

    @FXML
    public void handleAdd() {
        String identifiant = identifiantField.getText();
        Sujet selectedSujet = sujetComboBox.getValue();
        UniteEnseignement selectedUnite = uniteEnseignementComboBox.getValue();

        if (identifiant.isEmpty() || selectedSujet == null || selectedUnite == null) {
            showErrorAlert("Erreur", "Veuillez remplir tous les champs.");
        } else {
            Groupe newGroupe = new Groupe();
            newGroupe.setIdentifiant(identifiant);
            newGroupe.setSujet(selectedSujet);
            newGroupe.setUniteEnseignement(selectedUnite);

            ObservableList<Personne> selectedPersonnes = personneListView.getSelectionModel().getSelectedItems();
            newGroupe.setPersonnes(new ArrayList<>(selectedPersonnes));

            try {
                groupeDAO.addGroupe(newGroupe);
                loadGroupeData();
                showInfoAlert("Succès", "Groupe ajouté avec succès.");
            } catch (SQLException e) {
                showErrorAlert("Erreur", "Échec de l'ajout du groupe : " + e.getMessage());
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

            ObservableList<Personne> selectedPersonnes = personneListView.getSelectionModel().getSelectedItems();
            selectedGroupe.setPersonnes(new ArrayList<>(selectedPersonnes));

            try {
                groupeDAO.updateGroupe(selectedGroupe);
                loadGroupeData();
                showInfoAlert("Succès", "Groupe mis à jour avec succès.");
            } catch (SQLException e) {
                showErrorAlert("Erreur", "Échec de la mise à jour du groupe : " + e.getMessage());
            }
        }
    }

    @FXML
    public void handleDelete() {
        Groupe selectedGroupe = groupeTable.getSelectionModel().getSelectedItem();

        if (selectedGroupe != null) {
            try {
                groupeDAO.deleteGroupe(selectedGroupe.getId());
                loadGroupeData(); // Rafraîchir la table
                showInfoAlert("Succès", "Groupe supprimé avec succès.");
            } catch (SQLException e) {
                showErrorAlert("Erreur", "Échec de la suppression du groupe : " + e.getMessage());
            }
        }
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
