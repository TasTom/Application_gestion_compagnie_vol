package org;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import dao.*;
import model.*;
import util.DatabaseConnection;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.Date;

public class App extends Application {
    private static Connection connection;
    private static Scanner scanner;
    private static VolDAO volDAO;
    private static PiloteDAO piloteDAO;
    private static AvionDAO avionDAO;

    @Override
    public void start(Stage stage) throws IOException {
        // Chemin complet depuis la racine des resources
        /*FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/tp1_jdbc/hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        */
        try {
            DatabaseConnection db = new DatabaseConnection();
            connection = db.getConnection();
            scanner = new Scanner(System.in);
            volDAO = new VolDAO(connection);
            piloteDAO = new PiloteDAO(connection);
            avionDAO = new AvionDAO(connection);

            // Lancement du menu principal
            afficherMenuGestionVols();

        } catch (Exception e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
        } finally {
            fermerConnexion();
        }
    }

    private static void afficherMenuGestionVols() {
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n=== GESTION DES VOLS ===");
            System.out.println("1. Créer un vol");
            System.out.println("2. Lister tous les vols");
            System.out.println("3. Modifier le statut d'un vol");
            System.out.println("4. Supprimer un vol");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");

            int choix = lireEntier();

            switch (choix) {
                case 1:
                    creerVol();
                    break;
                case 2:
                    listerTousLesVols();
                    break;
                case 3:
                    modifierStatutVol();
                    break;
                case 4:
                    supprimerVol();
                    break;
                case 0:
                    continuer = false;
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
                    break;
            }
        }
    }

    // 1. Créer un vol en sélectionnant un pilote et un avion existants
    private static void creerVol() {
        try {
            System.out.println("\n=== CRÉATION D'UN VOL ===");

            // Afficher les pilotes disponibles
            System.out.println("\nPilotes disponibles :");
            List<Pilote> pilotes = piloteDAO.readAll();
            if (pilotes.isEmpty()) {
                System.out.println("Aucun pilote disponible. Créez d'abord un pilote.");
                return;
            }

            for (Pilote pilote : pilotes) {
                System.out.printf("%d - %s %s (Expérience: %d ans)\n",
                        pilote.getId_pilote(), pilote.getPrenom(), pilote.getNom(), pilote.getExperience());
            }

            System.out.print("Sélectionnez l'ID du pilote : ");
            int idPilote = lireEntier();

            // Afficher les avions disponibles
            System.out.println("\nAvions disponibles :");
            List<Avion> avions = avionDAO.readAll();
            if (avions.isEmpty()) {
                System.out.println("Aucun avion disponible. Créez d'abord un avion.");
                return;
            }

            for (Avion avion : avions) {
                System.out.printf("%d - %s (Capacité: %d passagers)\n",
                        avion.getId_avion(), avion.getModele(), avion.getCapacite());
            }

            System.out.print("Sélectionnez l'ID de l'avion : ");
            int idAvion = lireEntier();

            // Saisie des informations du vol
            System.out.print("Numéro du vol : ");
            String numeroVol = scanner.nextLine();

            System.out.print("Ville de départ : ");
            String villeDepart = scanner.nextLine();

            System.out.print("Ville d'arrivée : ");
            String villeArrivee = scanner.nextLine();

            System.out.print("Date de départ (format: yyyy-MM-dd HH:mm) : ");
            Date dateDepart = lireDate();

            System.out.print("Date d'arrivée (format: yyyy-MM-dd HH:mm) : ");
            Date dateArrivee = lireDate();

            String statut = "prévu"; // Statut par défaut

            // Création du vol
            Vol nouveauVol = new Vol(0, numeroVol, idAvion, idPilote,
                    villeDepart, villeArrivee, dateDepart, dateArrivee, statut);

            volDAO.create(nouveauVol);
            System.out.println("Vol créé avec succès !");

        } catch (SQLException e) {
            System.err.println("Erreur lors de la création du vol : " + e.getMessage());
        }
    }

    // 2. Lister tous les vols avec informations détaillées
    private static void listerTousLesVols() {
        try {
            System.out.println("\n=== LISTE DES VOLS ===");

            List<Vol> vols = volDAO.readAll();
            if (vols.isEmpty()) {
                System.out.println("Aucun vol enregistré.");
                return;
            }

            System.out.printf("%-10s %-15s %-15s %-15s %-20s %-15s %-10s\n",
                    "ID", "Numéro", "Départ", "Arrivée", "Pilote", "Avion", "Statut");
            System.out.println("=" + "-".repeat(110));

            for (Vol vol : vols) {
                // Récupération des informations du pilote et de l'avion
                Pilote pilote = piloteDAO.findById(vol.getId_pilote());
                Avion avion = avionDAO.findById(vol.getId_avion());

                String nomPilote = (pilote != null) ?
                        pilote.getPrenom() + " " + pilote.getNom() : "Inconnu";
                String modeleAvion = (avion != null) ? avion.getModele() : "Inconnu";

                System.out.printf("%-10d %-15s %-15s %-15s %-20s %-15s %-10s\n",
                        vol.getId_vol(),
                        vol.getNumero_Vol(),
                        vol.getVille_depart(),
                        vol.getVille_arrivee(),
                        nomPilote,
                        modeleAvion,
                        vol.getStatut());
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des vols : " + e.getMessage());
        }
    }

    // 3. Modifier le statut d'un vol (prévu, retardé, annulé)
    private static void modifierStatutVol() {
        try {
            System.out.println("\n=== MODIFICATION DU STATUT D'UN VOL ===");

            // Afficher d'abord la liste des vols
            listerTousLesVols();

            System.out.print("\nSélectionnez l'ID du vol à modifier : ");
            int idVol = lireEntier();

            Vol vol = volDAO.findById(idVol);
            if (vol == null) {
                System.out.println("Vol introuvable avec l'ID : " + idVol);
                return;
            }

            System.out.println("Vol sélectionné : " + vol.getNumero_Vol() +
                    " (" + vol.getVille_depart() + " → " + vol.getVille_arrivee() + ")");
            System.out.println("Statut actuel : " + vol.getStatut());

            System.out.println("\nNouveaux statuts disponibles :");
            System.out.println("1. prévu");
            System.out.println("2. retardé");
            System.out.println("3. annulé");
            System.out.print("Choisissez le nouveau statut : ");

            int choixStatut = lireEntier();
            String nouveauStatut;

            switch (choixStatut) {
                case 1:
                    nouveauStatut = "prévu";
                    break;
                case 2:
                    nouveauStatut = "retardé";
                    break;
                case 3:
                    nouveauStatut = "annulé";
                    break;
                default:
                    System.out.println("Choix invalide.");
                    return;
            }

            vol.setStatut(nouveauStatut);
            volDAO.update(vol);
            System.out.println("Statut du vol modifié avec succès : " + nouveauStatut);

        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du statut : " + e.getMessage());
        }
    }

    // 4. Supprimer un vol
    private static void supprimerVol() {
        try {
            System.out.println("\n=== SUPPRESSION D'UN VOL ===");

            // Afficher la liste des vols
            listerTousLesVols();

            System.out.print("\nSélectionnez l'ID du vol à supprimer : ");
            int idVol = lireEntier();

            Vol vol = volDAO.findById(idVol);
            if (vol == null) {
                System.out.println("Vol introuvable avec l'ID : " + idVol);
                return;
            }

            System.out.println("Vol à supprimer : " + vol.getNumero_Vol() +
                    " (" + vol.getVille_depart() + " → " + vol.getVille_arrivee() + ")");

            System.out.print("Confirmer la suppression ? (o/n) : ");
            String confirmation = scanner.nextLine().toLowerCase();

            if (confirmation.equals("o") || confirmation.equals("oui")) {
                volDAO.delete(idVol);
                System.out.println("Vol supprimé avec succès !");
            } else {
                System.out.println("Suppression annulée.");
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du vol : " + e.getMessage());
        }
    }

    // Méthodes utilitaires
    private static int lireEntier() {
        while (true) {
            try {
                int valeur = Integer.parseInt(scanner.nextLine().trim());
                return valeur;
            } catch (NumberFormatException e) {
                System.out.print("Veuillez saisir un nombre valide : ");
            }
        }
    }

    private static Date lireDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        while (true) {
            try {
                String dateStr = scanner.nextLine().trim();
                return sdf.parse(dateStr);
            } catch (ParseException e) {
                System.out.print("Format de date invalide. Utilisez yyyy-MM-dd HH:mm : ");
            }
        }
    }

    private static void fermerConnexion() {
        try {
            if (scanner != null) {
                scanner.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connexion fermée.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
        }
    }



    public static void main(String[] args) {
        launch();
    }
}