package org;

import dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import model.*;
import util.DatabaseConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;


public class VolController implements Initializable {

    // Connexion à la base de données
    private Connection connection;
    private VolDAO volDAO;
    private PiloteDAO piloteDAO;
    private AvionDAO avionDAO;
    private PassagerDAO passagerDAO;
    private ReservationDAO reservationDAO;

    // ===== ELEMENTS FXML - VOLS =====
    @FXML private TextField numeroVolField;
    @FXML private ComboBox<Pilote> piloteComboBox;
    @FXML private ComboBox<Avion> avionComboBox;
    @FXML private ComboBox<String> statutComboBox;
    @FXML private TextField villeDepartField;
    @FXML private TextField villeArriveeField;
    @FXML private DatePicker dateDepartPicker;
    @FXML private DatePicker dateArriveePicker;
    @FXML private TableView<Vol> volsTableView;
    @FXML private TableColumn<Vol, Integer> volIdColumn;
    @FXML private TableColumn<Vol, String> numeroVolColumn;
    @FXML private TableColumn<Vol, Integer> piloteColumn;
    @FXML private TableColumn<Vol, Integer> avionColumn;
    @FXML private TableColumn<Vol, String> villeDepartColumn;
    @FXML private TableColumn<Vol, String> villeArriveeColumn;
    @FXML private TableColumn<Vol, Date> dateDepartColumn;
    @FXML private TableColumn<Vol, Date> dateArriveeColumn;
    @FXML private TableColumn<Vol, String> statutColumn;

    // ===== ELEMENTS FXML - PILOTES =====
    @FXML private TextField piloteNomField;
    @FXML private TextField pilotePrenomField;
    @FXML private TextField piloteExperienceField;
    @FXML private TableView<Pilote> pilotesTableView;
    @FXML private TableColumn<Pilote, Integer> piloteIdColumn;
    @FXML private TableColumn<Pilote, String> piloteNomColumn;
    @FXML private TableColumn<Pilote, String> pilotePrenomColumn;
    @FXML private TableColumn<Pilote, Integer> piloteExperienceColumn;

    // ===== ELEMENTS FXML - AVIONS =====
    @FXML private TextField avionModeleField;
    @FXML private TextField avionCapaciteField;
    @FXML private TableView<Avion> avionsTableView;
    @FXML private TableColumn<Avion, Integer> avionIdColumn;
    @FXML private TableColumn<Avion, String> avionModeleColumn;
    @FXML private TableColumn<Avion, Integer> avionCapaciteColumn;

    // ===== ELEMENTS FXML - PASSAGERS =====
    @FXML private TextField passagerNomField;
    @FXML private TextField passagerPrenomField;
    @FXML private TextField passagerNationaliteField;
    @FXML private TableView<Passager> passagersTableView;
    @FXML private TableColumn<Passager, Integer> passagerIdColumn;
    @FXML private TableColumn<Passager, String> passagerNomColumn;
    @FXML private TableColumn<Passager, String> passagerPrenomColumn;
    @FXML private TableColumn<Passager, String> passagerNationaliteColumn;

    // ===== ELEMENTS FXML - RESERVATIONS =====
    @FXML private ComboBox<Passager> reservationPassagerComboBox;
    @FXML private ComboBox<Vol> reservationVolComboBox;
    @FXML private DatePicker dateReservationPicker;
    @FXML private TableView<Reservation> reservationsTableView;
    @FXML private TableColumn<Reservation, Integer> reservationIdColumn;
    @FXML private TableColumn<Reservation, Integer> reservationPassagerColumn;
    @FXML private TableColumn<Reservation, Integer> reservationVolColumn;
    @FXML private TableColumn<Reservation, Date> reservationDateColumn;

    // ===== ELEMENTS FXML - CONSULTATIONS =====
    @FXML private ComboBox<Vol> consultationVolComboBox;
    @FXML private ComboBox<Passager> consultationPassagerComboBox;
    @FXML private TableView<Passager> passagersDuVolTableView;
    @FXML private TableView<Vol> volsDuPassagerTableView;

    // Colonnes pour passagers d'un vol
    @FXML private TableColumn<Passager, Integer> passagerVolIdColumn;
    @FXML private TableColumn<Passager, String> passagerVolNomColumn;
    @FXML private TableColumn<Passager, String> passagerVolPrenomColumn;
    @FXML private TableColumn<Passager, String> passagerVolNationaliteColumn;

    // Colonnes pour vols d'un passager
    @FXML private TableColumn<Vol, Integer> volPassagerIdColumn;
    @FXML private TableColumn<Vol, String> volPassagerNumeroColumn;
    @FXML private TableColumn<Vol, String> volPassagerDepartColumn;
    @FXML private TableColumn<Vol, String> volPassagerArriveeColumn;
    @FXML private TableColumn<Vol, Date> volPassagerDateDepartColumn;
    @FXML private TableColumn<Vol, Date> volPassagerDateArriveeColumn;
    @FXML private TableColumn<Vol, String> volPassagerStatutColumn;

    // ===== STATUS BAR =====
    @FXML private Label statusLabel;

    // ObservableLists pour les données
    private ObservableList<Vol> volsData = FXCollections.observableArrayList();
    private ObservableList<Pilote> pilotesData = FXCollections.observableArrayList();
    private ObservableList<Avion> avionsData = FXCollections.observableArrayList();
    private ObservableList<Passager> passagersData = FXCollections.observableArrayList();
    private ObservableList<Reservation> reservationsData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Initialisation de la connexion à la base de données
            DatabaseConnection dbConnection = new DatabaseConnection();
            connection = dbConnection.getConnection();

            // Initialisation des DAO
            volDAO = new VolDAO(connection);
            piloteDAO = new PiloteDAO(connection);
            avionDAO = new AvionDAO(connection);
            passagerDAO = new PassagerDAO(connection);
            reservationDAO = new ReservationDAO(connection);

            // Configuration des colonnes des tables
            setupTableColumns();

            // Configuration des ComboBox
            setupComboBoxes();

            // Configuration des listeners
            setupTableListeners();

            // Chargement initial des données
            rafraichirToutesLesDonnees();

            updateStatus("Application initialisée avec succès");
        } catch (Exception e) {
            updateStatus("Erreur d'initialisation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupTableColumns() {
        // Configuration des colonnes de la table Vols
        volIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_vol"));
        numeroVolColumn.setCellValueFactory(new PropertyValueFactory<>("numero_Vol"));
        villeDepartColumn.setCellValueFactory(new PropertyValueFactory<>("ville_depart"));
        villeArriveeColumn.setCellValueFactory(new PropertyValueFactory<>("ville_arrivee"));
        dateDepartColumn.setCellValueFactory(new PropertyValueFactory<>("date_depart"));
        dateArriveeColumn.setCellValueFactory(new PropertyValueFactory<>("date_arrivee"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));
        piloteColumn.setCellValueFactory(new PropertyValueFactory<>("id_pilote"));
        avionColumn.setCellValueFactory(new PropertyValueFactory<>("id_avion"));

        // Configuration des colonnes de la table Pilotes
        piloteIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_pilote"));
        piloteNomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        pilotePrenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        piloteExperienceColumn.setCellValueFactory(new PropertyValueFactory<>("experience"));

        // Configuration des colonnes de la table Avions
        avionIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_avion"));
        avionModeleColumn.setCellValueFactory(new PropertyValueFactory<>("modele"));
        avionCapaciteColumn.setCellValueFactory(new PropertyValueFactory<>("capacite"));

        // Configuration des colonnes de la table Passagers
        passagerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_passager"));
        passagerNomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        passagerPrenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        passagerNationaliteColumn.setCellValueFactory(new PropertyValueFactory<>("nationalite"));

        // Configuration des colonnes de la table Réservations
        reservationIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_reservation"));
        reservationDateColumn.setCellValueFactory(new PropertyValueFactory<>("date_Reservation"));
        reservationPassagerColumn.setCellValueFactory(new PropertyValueFactory<>("id_passager"));
        reservationVolColumn.setCellValueFactory(new PropertyValueFactory<>("id_vol"));

        // Configuration des colonnes pour passagers d'un vol
        passagerVolIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_passager"));
        passagerVolNomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        passagerVolPrenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        passagerVolNationaliteColumn.setCellValueFactory(new PropertyValueFactory<>("nationalite"));

        // Configuration des colonnes pour vols d'un passager
        volPassagerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_vol"));
        volPassagerNumeroColumn.setCellValueFactory(new PropertyValueFactory<>("numero_Vol"));
        volPassagerDepartColumn.setCellValueFactory(new PropertyValueFactory<>("ville_depart"));
        volPassagerArriveeColumn.setCellValueFactory(new PropertyValueFactory<>("ville_arrivee"));
        volPassagerDateDepartColumn.setCellValueFactory(new PropertyValueFactory<>("date_depart"));
        volPassagerDateArriveeColumn.setCellValueFactory(new PropertyValueFactory<>("date_arrivee"));
        volPassagerStatutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));

        // Formater les dates dans les colonnes
        formatDateColumn(volPassagerDateDepartColumn);
        formatDateColumn(volPassagerDateArriveeColumn);
    }

    private void formatDateColumn(TableColumn<Vol, Date> column) {
        column.setCellFactory(col -> new TableCell<Vol, Date>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // Utiliser getTime() qui fonctionne pour tous les types de Date
                    LocalDate localDate = LocalDate.ofEpochDay(item.getTime() / (1000 * 60 * 60 * 24));
                    setText(formatter.format(localDate));
                }
            }
        });
    }


    private void setupComboBoxes() {
        // Configuration du ComboBox des statuts
        ObservableList<String> statuts = FXCollections.observableArrayList(
                "En cours", "Terminé", "Annulé", "Retardé");
        statutComboBox.setItems(statuts);

        // Configuration des StringConverter pour les ComboBox
        piloteComboBox.setConverter(new StringConverter<Pilote>() {
            @Override
            public String toString(Pilote pilote) {
                return pilote != null ? pilote.getPrenom() + " " + pilote.getNom() : "";
            }

            @Override
            public Pilote fromString(String string) {
                return null;
            }
        });

        avionComboBox.setConverter(new StringConverter<Avion>() {
            @Override
            public String toString(Avion avion) {
                return avion != null ? avion.getModele() + " (ID: " + avion.getId_avion() + ")" : "";
            }

            @Override
            public Avion fromString(String string) {
                return null;
            }
        });

        reservationPassagerComboBox.setConverter(new StringConverter<Passager>() {
            @Override
            public String toString(Passager passager) {
                return passager != null ? passager.getPrenom() + " " + passager.getNom() : "";
            }

            @Override
            public Passager fromString(String string) {
                return null;
            }
        });

        reservationVolComboBox.setConverter(new StringConverter<Vol>() {
            @Override
            public String toString(Vol vol) {
                return vol != null ? vol.getNumero_Vol() + " (" + vol.getVille_depart() + " → " + vol.getVille_arrivee() + ")" : "";
            }

            @Override
            public Vol fromString(String string) {
                return null;
            }
        });

        // ComboBox pour les consultations
        consultationVolComboBox.setConverter(new StringConverter<Vol>() {
            @Override
            public String toString(Vol vol) {
                if (vol == null) return null;
                return vol.getNumero_Vol() + " (" + vol.getVille_depart() + " → " + vol.getVille_arrivee() + ")";
            }

            @Override
            public Vol fromString(String string) {
                return null;
            }
        });

        consultationPassagerComboBox.setConverter(new StringConverter<Passager>() {
            @Override
            public String toString(Passager passager) {
                if (passager == null) return null;
                return passager.getPrenom() + " " + passager.getNom() + " (" + passager.getNationalite() + ")";
            }

            @Override
            public Passager fromString(String string) {
                return null;
            }
        });
    }

    private void setupTableListeners() {
        // Listeners pour remplir automatiquement les formulaires lors de la sélection
        volsTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                remplirFormVol(newSelection);
            }
        });

        pilotesTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                remplirFormPilote(newSelection);
            }
        });

        avionsTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                remplirFormAvion(newSelection);
            }
        });

        passagersTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                remplirFormPassager(newSelection);
            }
        });
    }

    // ===== MÉTHODES POUR LES VOLS =====
    @FXML
    private void creerVol() {
        try {
            String numeroVol = numeroVolField.getText();
            Pilote piloteSelectionne = piloteComboBox.getValue();
            Avion avionSelectionne = avionComboBox.getValue();
            String statut = statutComboBox.getValue();
            String villeDepart = villeDepartField.getText();
            String villeArrivee = villeArriveeField.getText();
            LocalDate dateDepart = dateDepartPicker.getValue();
            LocalDate dateArrivee = dateArriveePicker.getValue();

            if (numeroVol.isEmpty() || piloteSelectionne == null || avionSelectionne == null ||
                    statut == null || villeDepart.isEmpty() || villeArrivee.isEmpty() ||
                    dateDepart == null || dateArrivee == null) {
                updateStatus("Veuillez remplir tous les champs");
                return;
            }

            // Conversion LocalDate vers Date
            Date dateDepartDate = Date.from(dateDepart.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date dateArriveeDate = Date.from(dateArrivee.atStartOfDay(ZoneId.systemDefault()).toInstant());

            Vol nouveauVol = new Vol(0, numeroVol, avionSelectionne.getId_avion(),
                    piloteSelectionne.getId_pilote(), villeDepart, villeArrivee,
                    dateDepartDate, dateArriveeDate, statut);

            volDAO.create(nouveauVol);
            rafraichirVols();
            viderFormVol();
            updateStatus("Vol créé avec succès");
        } catch (SQLException e) {
            updateStatus("Erreur lors de la création du vol: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void modifierVol() {
        Vol volSelectionne = volsTableView.getSelectionModel().getSelectedItem();
        if (volSelectionne == null) {
            updateStatus("Veuillez sélectionner un vol à modifier");
            return;
        }

        try {
            volSelectionne.setNumero_Vol(numeroVolField.getText());
            volSelectionne.setId_pilote(piloteComboBox.getValue().getId_pilote());
            volSelectionne.setId_avion(avionComboBox.getValue().getId_avion());
            volSelectionne.setStatut(statutComboBox.getValue());
            volSelectionne.setVille_depart(villeDepartField.getText());
            volSelectionne.setVille_arrivee(villeArriveeField.getText());

            if (dateDepartPicker.getValue() != null) {
                volSelectionne.setDate_depart(Date.from(dateDepartPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            }

            if (dateArriveePicker.getValue() != null) {
                volSelectionne.setDate_arrivee(Date.from(dateArriveePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            }

            volDAO.update(volSelectionne);
            rafraichirVols();
            viderFormVol();
            updateStatus("Vol modifié avec succès");
        } catch (SQLException e) {
            updateStatus("Erreur lors de la modification du vol: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void supprimerVol() {
        Vol volSelectionne = volsTableView.getSelectionModel().getSelectedItem();
        if (volSelectionne == null) {
            updateStatus("Veuillez sélectionner un vol à supprimer");
            return;
        }

        try {
            volDAO.delete(volSelectionne.getId_vol());
            rafraichirVols();
            viderFormVol();
            updateStatus("Vol supprimé avec succès");
        } catch (SQLException e) {
            updateStatus("Erreur lors de la suppression du vol: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void rafraichirVols() {
        try {
            List<Vol> vols = volDAO.readAll();
            volsData.setAll(vols);
            volsTableView.setItems(volsData);
            consultationVolComboBox.setItems(FXCollections.observableArrayList(vols));
            updateStatus("Vols rafraîchis: " + vols.size() + " trouvés");
        } catch (SQLException e) {
            updateStatus("Erreur SQL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ===== MÉTHODES POUR LES PILOTES =====
    @FXML
    private void ajouterPilote() {
        try {
            String nom = piloteNomField.getText();
            String prenom = pilotePrenomField.getText();
            String experienceText = piloteExperienceField.getText();

            if (nom.isEmpty() || prenom.isEmpty() || experienceText.isEmpty()) {
                updateStatus("Veuillez remplir tous les champs du pilote");
                return;
            }

            int experience = Integer.parseInt(experienceText);
            Pilote nouveauPilote = new Pilote(0, nom, prenom, experience);
            piloteDAO.create(nouveauPilote);
            rafraichirPilotes();
            viderFormPilote();
            updateStatus("Pilote ajouté avec succès");
        } catch (NumberFormatException e) {
            updateStatus("Veuillez entrer un nombre valide pour l'expérience");
        } catch (Exception e) {
            updateStatus("Erreur lors de l'ajout du pilote: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void modifierPilote() {
        Pilote piloteSelectionne = pilotesTableView.getSelectionModel().getSelectedItem();
        if (piloteSelectionne == null) {
            updateStatus("Veuillez sélectionner un pilote à modifier");
            return;
        }

        try {
            piloteSelectionne.setNom(piloteNomField.getText());
            piloteSelectionne.setPrenom(pilotePrenomField.getText());
            piloteSelectionne.setExperience(Integer.parseInt(piloteExperienceField.getText()));
            piloteDAO.update(piloteSelectionne);
            rafraichirPilotes();
            viderFormPilote();
            updateStatus("Pilote modifié avec succès");
        } catch (Exception e) {
            updateStatus("Erreur lors de la modification du pilote: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void supprimerPilote() {
        Pilote piloteSelectionne = pilotesTableView.getSelectionModel().getSelectedItem();
        if (piloteSelectionne == null) {
            updateStatus("Veuillez sélectionner un pilote à supprimer");
            return;
        }

        try {
            piloteDAO.delete(piloteSelectionne);
            rafraichirPilotes();
            viderFormPilote();
            updateStatus("Pilote supprimé avec succès");
        } catch (Exception e) {
            updateStatus("Erreur lors de la suppression du pilote: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void rafraichirPilotes() {
        try {
            List<Pilote> pilotes = piloteDAO.readAll();
            pilotesData.setAll(pilotes);
            pilotesTableView.setItems(pilotesData);
            piloteComboBox.setItems(pilotesData);
            updateStatus("Liste des pilotes rafraîchie");
        } catch (Exception e) {
            updateStatus("Erreur lors du rafraîchissement des pilotes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ===== MÉTHODES POUR LES AVIONS =====
    @FXML
    private void ajouterAvion() {
        try {
            String modele = avionModeleField.getText();
            String capaciteText = avionCapaciteField.getText();

            if (modele.isEmpty() || capaciteText.isEmpty()) {
                updateStatus("Veuillez remplir tous les champs de l'avion");
                return;
            }

            int capacite = Integer.parseInt(capaciteText);
            Avion nouvelAvion = new Avion(0, modele, capacite);
            avionDAO.create(nouvelAvion);
            rafraichirAvions();
            viderFormAvion();
            updateStatus("Avion ajouté avec succès");
        } catch (NumberFormatException e) {
            updateStatus("Veuillez entrer un nombre valide pour la capacité");
        } catch (SQLException e) {
            updateStatus("Erreur lors de l'ajout de l'avion: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void modifierAvion() {
        Avion avionSelectionne = avionsTableView.getSelectionModel().getSelectedItem();
        if (avionSelectionne == null) {
            updateStatus("Veuillez sélectionner un avion à modifier");
            return;
        }

        try {
            avionSelectionne.setModele(avionModeleField.getText());
            avionSelectionne.setCapacite(Integer.parseInt(avionCapaciteField.getText()));
            avionDAO.update(avionSelectionne);
            rafraichirAvions();
            viderFormAvion();
            updateStatus("Avion modifié avec succès");
        } catch (SQLException e) {
            updateStatus("Erreur lors de la modification de l'avion: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void supprimerAvion() {
        Avion avionSelectionne = avionsTableView.getSelectionModel().getSelectedItem();
        if (avionSelectionne == null) {
            updateStatus("Veuillez sélectionner un avion à supprimer");
            return;
        }

        try {
            avionDAO.delete(avionSelectionne.getId_avion());
            rafraichirAvions();
            viderFormAvion();
            updateStatus("Avion supprimé avec succès");
        } catch (SQLException e) {
            updateStatus("Erreur lors de la suppression de l'avion: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void rafraichirAvions() {
        try {
            List<Avion> avions = avionDAO.readAll();
            avionsData.setAll(avions);
            avionsTableView.setItems(avionsData);
            avionComboBox.setItems(avionsData);
            updateStatus("Liste des avions rafraîchie");
        } catch (SQLException e) {
            updateStatus("Erreur lors du rafraîchissement des avions: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ===== MÉTHODES POUR LES PASSAGERS =====
    @FXML
    private void ajouterPassager() {
        try {
            String nom = passagerNomField.getText();
            String prenom = passagerPrenomField.getText();
            String nationalite = passagerNationaliteField.getText();

            if (nom.isEmpty() || prenom.isEmpty() || nationalite.isEmpty()) {
                updateStatus("Veuillez remplir tous les champs du passager");
                return;
            }

            Passager nouveauPassager = new Passager(0, prenom, nom, nationalite);
            passagerDAO.create(nouveauPassager);
            rafraichirPassagers();
            viderFormPassager();
            updateStatus("Passager ajouté avec succès");
        } catch (SQLException e) {
            updateStatus("Erreur lors de l'ajout du passager: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void modifierPassager() {
        Passager passagerSelectionne = passagersTableView.getSelectionModel().getSelectedItem();
        if (passagerSelectionne == null) {
            updateStatus("Veuillez sélectionner un passager à modifier");
            return;
        }

        try {
            passagerSelectionne.setNom(passagerNomField.getText());
            passagerSelectionne.setPrenom(passagerPrenomField.getText());
            passagerSelectionne.setNationalite(passagerNationaliteField.getText());
            passagerDAO.update(passagerSelectionne);
            rafraichirPassagers();
            viderFormPassager();
            updateStatus("Passager modifié avec succès");
        } catch (SQLException e) {
            updateStatus("Erreur lors de la modification du passager: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void supprimerPassager() {
        Passager passagerSelectionne = passagersTableView.getSelectionModel().getSelectedItem();
        if (passagerSelectionne == null) {
            updateStatus("Veuillez sélectionner un passager à supprimer");
            return;
        }

        try {
            passagerDAO.delete(passagerSelectionne.getId_passager());
            rafraichirPassagers();
            viderFormPassager();
            updateStatus("Passager supprimé avec succès");
        } catch (SQLException e) {
            updateStatus("Erreur lors de la suppression du passager: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void rafraichirPassagers() {
        try {
            List<Passager> passagers = passagerDAO.readAll();
            passagersData.setAll(passagers);
            passagersTableView.setItems(passagersData);
            reservationPassagerComboBox.setItems(passagersData);
            consultationPassagerComboBox.setItems(FXCollections.observableArrayList(passagers));
            updateStatus("Liste des passagers rafraîchie");
        } catch (SQLException e) {
            updateStatus("Erreur lors du rafraîchissement des passagers: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ===== MÉTHODES POUR LES RÉSERVATIONS =====
    @FXML
    private void creerReservation() {
        try {
            Passager passagerSelectionne = reservationPassagerComboBox.getValue();
            Vol volSelectionne = reservationVolComboBox.getValue();
            LocalDate dateReservation = dateReservationPicker.getValue();

            if (passagerSelectionne == null || volSelectionne == null || dateReservation == null) {
                updateStatus("Veuillez remplir tous les champs de la réservation");
                return;
            }

            // Vérifier la capacité avant de créer la réservation
            int nbReservations = reservationDAO.countReservationsByVol(volSelectionne.getId_vol());
            int capaciteVol = volDAO.getCapaciteVol(volSelectionne.getId_vol());

            if (nbReservations >= capaciteVol) {
                // Afficher une alerte à l'utilisateur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Capacité dépassée");
                alert.setHeaderText("Impossible de créer la réservation");
                alert.setContentText("Le vol " + volSelectionne.getNumero_Vol() +
                        " a atteint sa capacité maximale.\n" +
                        "Capacité: " + capaciteVol + "\n" +
                        "Réservations actuelles: " + nbReservations);
                alert.showAndWait();
                updateStatus("Réservation refusée : capacité maximale atteinte");
                return;
            }

            Date dateReservationDate = Date.from(dateReservation.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Reservation nouvelleReservation = new Reservation(0, passagerSelectionne.getId_passager(),
                    volSelectionne.getId_vol(), dateReservationDate);

            reservationDAO.create(nouvelleReservation);
            rafraichirReservations();
            viderFormReservation();
            updateStatus("Réservation créée avec succès (" + (nbReservations + 1) + "/" + capaciteVol + " places)");

        } catch (SQLException e) {
            if (e.getMessage().contains("Capacité maximale")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de capacité");
                alert.setHeaderText("Réservation impossible");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                updateStatus("Erreur: " + e.getMessage());
            } else {
                updateStatus("Erreur lors de la création de la réservation: " + e.getMessage());
            }
            e.printStackTrace();
        }
    }


    @FXML
    private void supprimerReservation() {
        Reservation reservationSelectionnee = reservationsTableView.getSelectionModel().getSelectedItem();
        if (reservationSelectionnee == null) {
            updateStatus("Veuillez sélectionner une réservation à supprimer");
            return;
        }

        try {
            reservationDAO.delete(reservationSelectionnee.getId_reservation());
            rafraichirReservations();
            updateStatus("Réservation supprimée avec succès");
        } catch (SQLException e) {
            updateStatus("Erreur lors de la suppression de la réservation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void rafraichirReservations() {
        try {
            List<Reservation> reservations = reservationDAO.readAll();
            reservationsData.setAll(reservations);
            reservationsTableView.setItems(reservationsData);
            reservationVolComboBox.setItems(volsData);
            updateStatus("Liste des réservations rafraîchie");
        } catch (SQLException e) {
            updateStatus("Erreur lors du rafraîchissement des réservations: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ===== MÉTHODES POUR LES CONSULTATIONS =====
    @FXML
    private void rechercherPassagersDuVol() {
        Vol volSelectionne = consultationVolComboBox.getValue();

        if (volSelectionne == null) {
            updateStatus("Veuillez sélectionner un vol");
            return;
        }

        try {
            List<Passager> passagers = passagerDAO.findPassagersByVol(volSelectionne.getId_vol());
            passagersDuVolTableView.setItems(FXCollections.observableArrayList(passagers));
            updateStatus(passagers.size() + " passager(s) trouvé(s) pour le vol " + volSelectionne.getNumero_Vol());
        } catch (Exception e) {
            updateStatus("Erreur lors de la recherche: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void rechercherVolsDuPassager() {
        Passager passagerSelectionne = consultationPassagerComboBox.getValue();

        if (passagerSelectionne == null) {
            updateStatus("Veuillez sélectionner un passager");
            return;
        }

        try {
            List<Vol> vols = volDAO.findVolsByPassager(passagerSelectionne.getId_passager());
            volsDuPassagerTableView.setItems(FXCollections.observableArrayList(vols));
            updateStatus(vols.size() + " vol(s) trouvé(s) pour " + passagerSelectionne.getPrenom() + " " + passagerSelectionne.getNom());
        } catch (Exception e) {
            updateStatus("Erreur lors de la recherche: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ===== MÉTHODES UTILITAIRES =====
    private void rafraichirToutesLesDonnees() {
        rafraichirPilotes();
        rafraichirAvions();
        rafraichirPassagers();
        rafraichirVols();
        rafraichirReservations();
    }

    private void viderFormVol() {
        numeroVolField.clear();
        villeDepartField.clear();
        villeArriveeField.clear();
        piloteComboBox.setValue(null);
        avionComboBox.setValue(null);
        statutComboBox.setValue(null);
        dateDepartPicker.setValue(null);
        dateArriveePicker.setValue(null);
    }

    private void viderFormPilote() {
        piloteNomField.clear();
        pilotePrenomField.clear();
        piloteExperienceField.clear();
    }

    private void viderFormAvion() {
        avionModeleField.clear();
        avionCapaciteField.clear();
    }

    private void viderFormPassager() {
        passagerNomField.clear();
        passagerPrenomField.clear();
        passagerNationaliteField.clear();
    }

    private void viderFormReservation() {
        reservationPassagerComboBox.setValue(null);
        reservationVolComboBox.setValue(null);
        dateReservationPicker.setValue(null);
    }

    private void updateStatus(String message) {
        statusLabel.setText(message);
        System.out.println("Status: " + message);
    }

    private void remplirFormVol(Vol vol) {
        numeroVolField.setText(vol.getNumero_Vol());
        villeDepartField.setText(vol.getVille_depart());
        villeArriveeField.setText(vol.getVille_arrivee());
        statutComboBox.setValue(vol.getStatut());

        // Conversion Date vers LocalDate
        if (vol.getDate_depart() != null) {
            dateDepartPicker.setValue(vol.getDate_depart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }

        if (vol.getDate_arrivee() != null) {
            dateArriveePicker.setValue(vol.getDate_arrivee().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }

        // Trouver et sélectionner le pilote et l'avion correspondants
        for (Pilote p : pilotesData) {
            if (p.getId_pilote() == vol.getId_pilote()) {
                piloteComboBox.setValue(p);
                break;
            }
        }

        for (Avion a : avionsData) {
            if (a.getId_avion() == vol.getId_avion()) {
                avionComboBox.setValue(a);
                break;
            }
        }
    }

    private void remplirFormPilote(Pilote pilote) {
        piloteNomField.setText(pilote.getNom());
        pilotePrenomField.setText(pilote.getPrenom());
        piloteExperienceField.setText(String.valueOf(pilote.getExperience()));
    }

    private void remplirFormAvion(Avion avion) {
        avionModeleField.setText(avion.getModele());
        avionCapaciteField.setText(String.valueOf(avion.getCapacite()));
    }

    private void remplirFormPassager(Passager passager) {
        passagerNomField.setText(passager.getNom());
        passagerPrenomField.setText(passager.getPrenom());
        passagerNationaliteField.setText(passager.getNationalite());
    }
}
