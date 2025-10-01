package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Passager;

public class PassagerDAO {
    private Connection connection;

    public PassagerDAO(Connection connection) {
        this.connection = connection;
    }

    // Création d'un passager dans la BDD
    public void create(Passager passager) throws SQLException {
        String sql = "INSERT INTO passager (prenom, nom, nationalite) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, passager.getPrenom());
        statement.setString(2, passager.getNom());
        statement.setString(3, passager.getNationalite());
        statement.executeUpdate();
        statement.close();
    }

    // Mise à jour d'un passager
    public void update(Passager passager) throws SQLException {
        String sql = "UPDATE passager SET prenom = ?, nom = ?, nationalite = ? WHERE id_passager = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, passager.getPrenom());
        statement.setString(2, passager.getNom());
        statement.setString(3, passager.getNationalite());
        statement.setInt(4, passager.getId_passager());
        statement.executeUpdate();
        statement.close();
    }

    // Suppression d'un passager
    public void delete(int id_passager) throws SQLException {
        String sql = "DELETE FROM passager WHERE id_passager = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id_passager);
        statement.executeUpdate();
        statement.close();
    }

    // Recherche par ID
    public Passager findById(int id) throws SQLException {
        Passager passager = null;
        String sql = "SELECT * FROM passager WHERE id_passager = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            passager = new Passager(
                    resultSet.getInt("id_passager"),
                    resultSet.getString("prenom"),
                    resultSet.getString("nom"),
                    resultSet.getString("nationalite")
            );
        }
        resultSet.close();
        statement.close();
        return passager;
    }

    // Récupération de tous les passagers
    public List<Passager> readAll() throws SQLException {
        List<Passager> passagers = new ArrayList<>();
        String sql = "SELECT * FROM passager";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Passager passager = new Passager(
                    resultSet.getInt("id_passager"),
                    resultSet.getString("prenom"),
                    resultSet.getString("nom"),
                    resultSet.getString("nationalite")
            );
            passagers.add(passager);
        }
        resultSet.close();
        statement.close();
        return passagers;
    }

    // Recherche par nom
    public List<Passager> findPassagersByVol(int id_vol) {
        List<Passager> passagers = new ArrayList<>();
        String sql = "SELECT p.* FROM passager p " +
                "INNER JOIN reservation r ON p.id_passager = r.id_passager " +
                "WHERE r.id_vol = ?";
        try {
            java.sql.PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id_vol);
            java.sql.ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Passager passager = new Passager(
                        rs.getInt("id_passager"),
                        rs.getString("prenom"),
                        rs.getString("nom"),
                        rs.getString("nationalite")
                );
                passagers.add(passager);
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return passagers;
    }

}
