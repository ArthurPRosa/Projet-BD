package demo;

import java.util.ArrayList;

public class Plat {
	private int num;
	private String nom;
	private String desc;
	private int prix;
	private ArrayList<String> lAllerg;

	public Plat(int num) {
		this.num = num;
	}

	public Plat numero(int num) {
		this.num = num;
		return this;
	}

	public Plat nom(String nom) {
		this.nom = nom;
		return this;
	}

	public Plat desc(String desc) {
		this.desc = desc;
		return this;
	}

	public Plat prix(int prix) {
		this.prix = prix;
		return this;
	}

	public Plat lAllerg(ArrayList<String> lAllerg) {
		this.lAllerg = lAllerg;
		return this;
	}

}
