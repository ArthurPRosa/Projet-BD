package demo;

import java.util.ArrayList;
import java.util.HashMap;

public class Restaurant {
	private String email;
	private String nom;
	private int numTel;
	private String adr;
	private HashMap<String, String> horOuv;
	private int nbPlace;
	private String textPres;
	private ArrayList<Type> typeComm;
	private ArrayList<Evaluation> evals;
	private double note;

	public Restaurant(String email) {
		this.email = email;
	}

	public Restaurant nom(String nom) {
		this.nom = nom;
		return this;
	}

	public Restaurant numTel(int numTel) {
		this.numTel = numTel;
		return this;
	}

	public Restaurant adr(String adr) {
		this.adr = adr;
		return this;
	}

	public Restaurant horOuv(HashMap<String, String> horOuv) {
		this.horOuv = horOuv;
		return this;
	}

	public Restaurant nbPlace(int nbPlace) {
		this.nbPlace = nbPlace;
		return this;
	}

	public Restaurant textPres(String textPres) {
		this.textPres = textPres;
		return this;
	}

	public Restaurant typeComm(ArrayList<Type> typeComm) {
		this.typeComm = typeComm;
		return this;
	}

	public Restaurant evals(ArrayList<Evaluation> evals) {
		this.evals = evals;
		return this;
	}

	public Restaurant note() {
		double nMoyenne = 0;
		for (Evaluation eval : this.evals) {
			nMoyenne += eval.getNote();
		}
		nMoyenne /= this.evals.size();
		this.note = nMoyenne;
		return this;
	}

	public enum Type {
		LIVRAISON, A_EMPORTER, SUR_PLACE
	}

}
