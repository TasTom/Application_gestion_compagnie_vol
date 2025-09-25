package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Vol;

public class VolDAO {
    private Connection connection;

    public VolDAO(Connection connection) {
        this.connection = connection;
    }

    // Création d'un vol dans la BDD
    public void create(Vol vol) throws SQLException {
        String sql = "INSERT INTO vol (numero_vol, id_avion, id_pilote, ville_depart, ville_arrivee, date_depart, date_arrivee, statut) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, vol.getNumero_Vol());
        statement.setInt(2, vol.getId_avion());
        statement.setInt(3, vol.getId_pilote());
        statement.setString(4, vol.getVille_depart());
        statement.setString(5, vol.getVille_arrivee());
        statement.setTimestamp(6, new Timestamp(vol.getDate_depart().getTime()));
        statement.setTimestamp(7, new Timestamp(vol.getDate_arrivee().getTime()));
        statement.setString(8, vol.getStatut());
        statement.executeUpdate();
        statement.close();
    }

    // Mise à jour d'un vol
    public void update(Vol vol) throws SQLException {
        String sql = "UPDATE vol SET numero_vol = ?, id_avion = ?, id_pilote = ?, ville_depart = ?, ville_arrivee = ?, date_depart = ?, date_arrivee = ?, statut = ? WHERE id_vol = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, vol.getNumero_Vol());
        statement.setInt(2, vol.getId_avion());
        statement.setInt(3, vol.getId_pilote());
        statement.setString(4, vol.getVille_depart());
        statement.setString(5, vol.getVille_arrivee());
        statement.setTimestamp(6, new Timestamp(vol.getDate_depart().getTime()));
        statement.setTimestamp(7, new Timestamp(vol.getDate_arrivee().getTime()));
        statement.setString(8, vol.getStatut());
        statement.setInt(9, vol.getId_vol());
        statement.executeUpdate();
        statement.close();
    }

    // Suppression d'un vol
    public void delete(int id_vol) throws SQLException {
        String sql = "DELETE FROM vol WHERE id_vol = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id_vol);
        statement.executeUpdate();
        statement.close();
    }

    // Recherche par ID
    public Vol findById(int id) throws SQLException {
        Vol vol = null;
        String sql = "SELECT * FROM vol WHERE id_vol = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            vol = new Vol(
                    resultSet.getInt("id_vol"),
                    resultSet.getString("numero_vol"),
                    resultSet.getInt("id_avion"),
                    resultSet.getInt("id_pilote"),
                    resultSet.getString("ville_depart"),
                    resultSet.getString("ville_arrivee"),
                    resultSet.getTimestamp("date_depart"),
                    resultSet.getTimestamp("date_arrivee"),
                    resultSet.getString("statut")
            );
        }
        resultSet.close();
        statement.close();
        return vol;
    }

    // Récupération de tous les vols
    public List<Vol> readAll() throws SQLException {
        List<Vol> vols = new ArrayList<>();
        String sql = "SELECT * FROM vol";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Vol vol = new Vol(
                    resultSet.getInt("id_vol"),
                    resultSet.getString("numero_vol"),
                    resultSet.getInt("id_avion"),
                    resultSet.getInt("id_pilote"),
                    resultSet.getString("ville_depart"),
                    resultSet.getString("ville_arrivee"),
                    resultSet.getTimestamp("date_depart"),
                    resultSet.getTimestamp("date_arrivee"),
                    resultSet.getString("statut")
            );
            vols.add(vol);
        }
        resultSet.close();
        statement.close();
        return vols;
    }
}
