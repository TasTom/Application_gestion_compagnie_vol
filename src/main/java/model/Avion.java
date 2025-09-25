package model;

public class Avion {

    private String modele;
    private final int id_avion;
    private int capacite;
    private static int compteur = 0;

    public Avion(String modele, int capacite) {
        this.modele = modele;
        this.capacite = capacite;
        this.id_avion = compteur++;
    }

    public String getModele() {
        return modele;
    }

    public int getId_avion() {
        return id_avion;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    @Override
    public String toString() {
        return "Avion [modele=" + modele + ", id_avion=" + id_avion + ", capacite=" + capacite + "]";
    }



}
