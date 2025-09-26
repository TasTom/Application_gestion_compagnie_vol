package dao;

import model.Pilote;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class PiloteDAO{
    private Connection connection;
    public PiloteDAO(Connection connection){
        this.connection = connection;
    }
    public void create(Pilote pilote){
        String sql = "INSERT INTO pilote (nom, prenom, experience) VALUES (?, ?, ?)";
        try{
            java.sql.PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, pilote.getNom());
            statement.setString(2, pilote.getPrenom());
            statement.setInt(3, pilote.getExperience());
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void update(Pilote pilote){
        String sql = "UPDATE pilote SET nom = ?, prenom = ?, experience = ? WHERE id_pilote = ?";
        try{
            java.sql.PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, pilote.getNom());
            statement.setString(2, pilote.getPrenom());
            statement.setInt(3, pilote.getExperience());
            statement.setInt(4, pilote.getId_pilote());
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete(Pilote pilote){
        String sql = "DELETE FROM pilote WHERE id_pilote = ?";
        try{
            java.sql.PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, pilote.getId_pilote());
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Pilote findById(int id){
        Pilote pilote = null;
        String sql = "SELECT * FROM pilote WHERE id_pilote = ?";
        try{
            java.sql.PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            java.sql.ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                pilote = new Pilote(resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getInt("experience"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return pilote;
    }

    public List<Pilote> readAll() {
        List<Pilote> pilotes = new ArrayList<>();  // ← Créer une vraie liste
        String sql = "SELECT * FROM pilote";
        try {
            java.sql.PreparedStatement statement = connection.prepareStatement(sql);
            java.sql.ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Debug (gardez si vous voulez)
                System.out.println(resultSet.getString("nom") + " " +
                        resultSet.getString("prenom") + " " +
                        resultSet.getInt("experience"));

                // ← AJOUTEZ : Créer l'objet Pilote et l'ajouter à la liste
                Pilote pilote = new Pilote(
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getInt("experience")
                );
                pilotes.add(pilote);  // ← Ajouter à la liste
            }
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pilotes;  // ← Retourner la vraie liste
    }







}