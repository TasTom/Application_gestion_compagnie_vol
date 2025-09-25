package model;

public class Passager {
    private static int compteur = 0;
    private final int id_passager;
    private String prenom;
    private String nom;
    private String nationalite;

    public Passager(int id_passager, String prenom, String nom, String nationalite) {
        this.id_passager = compteur++;
        this.prenom = prenom;
        this.nom = nom;
        this.nationalite = nationalite;
    }

    public String getNom() {
        return nom;
    }

    public String getNationalite() {
        return nationalite;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getId_passager() {
        return id_passager;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public String toString() {
        return "Passager{" + "id_passager=" + id_passager + ", prenom=" + prenom + ", nom=" + nom + ", nationalite=" + nationalite + '}';
    }
}
