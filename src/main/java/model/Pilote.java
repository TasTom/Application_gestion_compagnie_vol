package model;

public class Pilote {
    private static int compteur = 0;
    private int id_pilote;
    private String nom;
    private String prenom;
    private int experience;

    public Pilote(String nom, String prenom, int experience) {
        this.id_pilote = compteur++;
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

    public String getPrenom() {
        return prenom;
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
