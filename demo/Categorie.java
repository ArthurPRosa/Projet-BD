package demo;

public class Categorie {
	private Categorie catMere = null;
	private int profondeur;
	
	public Categorie(Categorie catMere, int p) {
		this.catMere = (p == 0) ? null: catMere;
		this.profondeur = p;
	}
}
