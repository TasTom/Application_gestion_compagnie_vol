package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Reservation;

public class ReservationDAO {
    private Connection connection;

    public ReservationDAO(Connection connection) {
        this.connection = connection;
    }

    // Création d'une réservation dans la BDD
    public void create(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO reservation (id_passager, id_vol, date_reservation) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, reservation.getId_passager());
        statement.setInt(2, reservation.getId_vol());
        statement.setTimestamp(3, new Timestamp(reservation.getDate_Reservation().getTime()));
        statement.executeUpdate();
        statement.close();
    }

    // Mise à jour d'une réservation
    public void update(Reservation reservation) throws SQLException {
        String sql = "UPDATE reservation SET id_passager = ?, id_vol = ?, date_reservation = ? WHERE id_reservation = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, reservation.getId_passager());
        statement.setInt(2, reservation.getId_vol());
        statement.setTimestamp(3, new Timestamp(reservation.getDate_Reservation().getTime()));
        statement.setInt(4, reservation.getId_reservation());
        statement.executeUpdate();
        statement.close();
    }

    // Suppression d'une réservation
    public void delete(int id_reservation) throws SQLException {
        String sql = "DELETE FROM reservation WHERE id_reservation = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id_reservation);
        statement.executeUpdate();
        statement.close();
    }

    // Recherche par ID
    public Reservation findById(int id) throws SQLException {
        Reservation reservation = null;
        String sql = "SELECT * FROM reservation WHERE id_reservation = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            reservation = new Reservation(
                    resultSet.getInt("id_reservation"),
                    resultSet.getInt("id_passager"),
                    resultSet.getInt("id_vol"),
                    resultSet.getTimestamp("date_reservation")
            );
        }
        resultSet.close();
        statement.close();
        return reservation;
    }

    // Récupération de toutes les réservations
    public List<Reservation> readAll() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservation";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Reservation reservation = new Reservation(
                    resultSet.getInt("id_reservation"),
                    resultSet.getInt("id_passager"),
                    resultSet.getInt("id_vol"),
                    resultSet.getTimestamp("date_reservation")
            );
            reservations.add(reservation);
        }
        resultSet.close();
        statement.close();
        return reservations;
    }

    // Recherche des réservations par passager
    public List<Reservation> findByPassager(int id_passager) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservation WHERE id_passager = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id_passager);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Reservation reservation = new Reservation(
                    resultSet.getInt("id_reservation"),
                    resultSet.getInt("id_passager"),
                    resultSet.getInt("id_vol"),
                    resultSet.getTimestamp("date_reservation")
            );
            reservations.add(reservation);
        }
        resultSet.close();
        statement.close();
        return reservations;
    }
}
