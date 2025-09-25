package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Avion;

public class AvionDAO {
    private Connection connection;

    public AvionDAO(Connection connection) {
        this.connection = connection;
    }

    // Création d'un avion dans la BDD
    public void create(Avion avion) throws SQLException {
        String req = "INSERT INTO avion (modele, capacite) VALUES (?,?)";
        PreparedStatement pst = connection.prepareStatement(req);
        pst.setString(1, avion.getModele());
        pst.setInt(2, avion.getCapacite());
        pst.executeUpdate();
        pst.close();
    }

    // Mise à jour d'un avion dans la BDD
    public void update(Avion avion) throws SQLException {
        String req = "UPDATE avion SET modele = ?, capacite = ? WHERE id_avion = ?";
        PreparedStatement pst = connection.prepareStatement(req);
        pst.setString(1, avion.getModele());
        pst.setInt(2, avion.getCapacite());
        pst.setInt(3, avion.getId_avion());
        pst.executeUpdate();
        pst.close();
    }

    // Suppression d'un avion dans la BDD
    public void delete(int id_avion) throws SQLException {
        String req = "DELETE FROM avion WHERE id_avion = ?";
        PreparedStatement pst = connection.prepareStatement(req);
        pst.setInt(1, id_avion);
        pst.executeUpdate();
        pst.close();
    }

    // Récupération d'un avion par ID
    public Avion findById(int id_avion) throws SQLException {
        String req = "SELECT * FROM avion WHERE id_avion = ?";
        PreparedStatement pst = connection.prepareStatement(req);
        pst.setInt(1, id_avion);
        ResultSet rs = pst.executeQuery();

        Avion avion = null;
        if (rs.next()) {
            avion = new Avion(rs.getString("modele"), rs.getInt("capacite"));
        }
        rs.close();
        pst.close();
        return avion;
    }

    // Récupération de tous les avions
    public List<Avion> readAll() throws SQLException {
        List<Avion> avions = new ArrayList<>();
        String req = "SELECT * FROM avion";
        PreparedStatement pst = connection.prepareStatement(req);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Avion avion = new Avion(rs.getString("modele"), rs.getInt("capacite"));
            avions.add(avion);
        }
        rs.close();
        pst.close();
        return avions;
    }
}
