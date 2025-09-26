package model;

public class Pilote {
    private int id_pilote;
    private String nom;
    private String prenom;
    private int experience;

    public Pilote(int id_pilote,String nom, String prenom, int experience) {
        this.id_pilote = id_pilote;
        this.nom = nom;
        this.prenom = prenom;
        this.experience = experience;
    }

    public int getId_pilote() {
        return id_pilote;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String toString() {
        return "Pilote{" + "id_pilote=" + id_pilote + ", nom=" + nom + ", prenom=" + prenom + ", experience=" + experience + '}';
    }


}
