package org;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        try {
            // Chemin vers le fichier compagnie_vol.fxml dans vos resources
            URL fxmlLocation = getClass().getResource("/org/tp1_jdbc/compagnie_vol.fxml");
            System.out.println("URL FXML trouvée: " + fxmlLocation);

            if (fxmlLocation == null) {
                System.err.println("ERREUR: Fichier FXML non trouvé");
                System.err.println("Recherché: /org/tp1_jdbc/compagnie_vol.fxml");
                return;
            }

            // Chargement de l'interface FXML
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
            Parent root = fxmlLoader.load();

            // Configuration de la scène
            Scene scene = new Scene(root, 1200, 800);
            stage.setTitle("Système de Gestion de Compagnie Aérienne");
            stage.setResizable(true);
            stage.setMinWidth(1000);
            stage.setMinHeight(700);
            stage.setScene(scene);
            stage.show();

            System.out.println("Interface graphique lancée avec succès");

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'interface FXML: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
