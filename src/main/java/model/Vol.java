package model;

import java.util.Date;

public class Vol {
	private int id_vol;
	private String numero_Vol;
	private int id_avion;
	private int id_pilote;
	private String ville_depart;
	private String ville_arrivee;
	private Date date_depart;
	private Date date_arrivee;
	private String statut;

	public Vol(int id_vol, String numero_Vol, int id_avion, int id_pilote, String ville_depart, String ville_arrivee, Date date_depart, Date date_arrivee, String statut) {
		this.id_vol =   id_vol;
		this.numero_Vol = numero_Vol;
		this.id_avion = id_avion;
		this.id_pilote = id_pilote;
		this.ville_depart = ville_depart;
		this.ville_arrivee = ville_arrivee;
		this.date_depart = date_depart;
		this.date_arrivee = date_arrivee;
		this.statut = statut;
	}
	public int getId_vol() {
		return id_vol;
	}
	public void setId_vol(int id_vol) {
		this.id_vol = id_vol;
	}
	public String getNumero_Vol() {
		return numero_Vol;
	}
	public void setNumero_Vol(String numero_Vol) {
		this.numero_Vol = numero_Vol;
	}
	public int getId_avion() {
		return id_avion;
	}
	public void setId_avion(int id_avion) {
		this.id_avion = id_avion;
	}
	public int getId_pilote() {
		return id_pilote;
    }
	public void setId_pilote(int id_pilote) {
		this.id_pilote = id_pilote;
	}
	public String getVille_depart() {
		return ville_depart;
	}
	public void setVille_depart(String ville_depart) {
		this.ville_depart = ville_depart;
	}
	public String getVille_arrivee() {
		return ville_arrivee;
	}
	public void setVille_arrivee(String ville_arrivee) {
		this.ville_arrivee = ville_arrivee;
    }
	public Date getDate_depart() {
		return date_depart;
	}
	public void setDate_depart(Date date_depart) {
		this.date_depart = date_depart;
	}
	public Date getDate_arrivee() {
		return date_arrivee;
	}
	public void setDate_arrivee(Date date_arrivee) {
		this.date_arrivee = date_arrivee;
	}
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
    }

}
