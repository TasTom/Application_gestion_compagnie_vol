package model;

public class Avion {

    private String modele;
    private final int id_avion;
    private int capacite;

    public Avion(int id_avion, String modele, int capacite) {
        this.id_avion = id_avion;
        this.modele = modele;
        this.capacite = capacite;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
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
