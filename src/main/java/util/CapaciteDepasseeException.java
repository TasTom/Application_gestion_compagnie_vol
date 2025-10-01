package util;

public class CapaciteDepasseeException extends Exception {
    private int capaciteMax;
    private int reservationsActuelles;

    public CapaciteDepasseeException(int capaciteMax, int reservationsActuelles) {
        super("Capacité de l'avion dépassée. Capacité: " + capaciteMax +
                ", Réservations actuelles: " + reservationsActuelles);
        this.capaciteMax = capaciteMax;
        this.reservationsActuelles = reservationsActuelles;
    }

    public int getCapaciteMax() {
        return capaciteMax;
    }

    public int getReservationsActuelles() {
        return reservationsActuelles;
    }
}
