package model;
import java.util.Date;

public class Reservation {

	private int id_reservation;
	private int id_passager;
	private int id_vol;
	private Date date_Reservation;

	public Reservation(int id_reservation, int id_passager, int id_vol, Date date_Reservation) {
		this.id_reservation = id_reservation;
		this.id_passager = id_passager;
		this.id_vol = id_vol;
		this.date_Reservation = date_Reservation;
	}

	public int getId_reservation() {
		return id_reservation;
	}
	public void setId_reservation(int id_reservation) {
		this.id_reservation = id_reservation;
	}
	public int getId_passager() {
		return id_passager;
	}
	public void setId_passager(int id_passager) {
		this.id_passager = id_passager;
	}
	public int getId_vol() {
		return id_vol;
	}
	public void setId_vol(int id_vol) {
		this.id_vol = id_vol;
	}
	public Date getDate_Reservation() {
		return date_Reservation;
	}
	public void setDate_Reservation(Date date_Reservation) {
		this.date_Reservation = date_Reservation;
	}




}
