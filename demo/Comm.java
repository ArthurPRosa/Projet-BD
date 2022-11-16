package demo;

public class Comm {
	private int idComm;
	private String date;
	private String h;
	private String client;
	private String restau;
	private Restaurant.Type typeComm;
	private String contenu;
	private int pFinal;
	private Statut statut;
	private String adrLivraison = null;
	private String infoLivreur = null;
	private String hLivraison = null;
	private int nbPersonnes = 0;
	private String hArrivee = null;
	private Evaluation evalComm = null;

	public Comm(int idCommande) {
		this.idComm = idCommande;
	}

	public Comm idCommande(int idCommande) {
		this.idComm = idCommande;
		return this;
	}

	public Comm h(String h) {
		this.h = h;
		return this;
	}

	public Comm client(String client) {
		this.client = client;
		return this;
	}

	public Comm restau(String restau) {
		this.restau = restau;
		return this;
	}

	public Comm typeComm(Restaurant.Type typeComm) {
		this.typeComm = typeComm;
		return this;
	}

	public Comm contenu(String contenu) {
		this.contenu = contenu;
		return this;
	}

	public Comm pFinal(int pFinal) {
		this.pFinal = pFinal;
		return this;
	}

	public Comm statut(Statut statut) {
		this.statut = statut;
		return this;
	}

	public Comm adrLivraison(String adrLivraison) {
		if (this.typeComm == Restaurant.Type.LIVRAISON) {
			this.adrLivraison = adrLivraison;
		}
		return this;
	}

	public Comm infoLivreur(String infoLivreur) {
		if (this.typeComm == Restaurant.Type.LIVRAISON) {
			this.infoLivreur = infoLivreur;
		}
		return this;
	}

	public Comm hLivraison(String hLivraison) {
		if (this.typeComm == Restaurant.Type.LIVRAISON) {
			this.hLivraison = hLivraison;
		}
		return this;
	}

	public Comm nbPersonnes(int nbPersonnes) {
		if (this.typeComm == Restaurant.Type.SUR_PLACE) {
			this.nbPersonnes = nbPersonnes;
		}
		return this;
	}

	public Comm hArrivee(String hArrivee) {
		if (this.typeComm == Restaurant.Type.SUR_PLACE) {
			this.hArrivee = hArrivee;
		}
		return this;
	}
	
	public Comm evalComm(Evaluation evalComm) {
		this.evalComm = evalComm;
		return this;
	}
}

enum Statut {
	ATTENTE_DE_CONFIRMATION, VALIDEE, DISPONIBLE, EN_LIVRAISON, ANNULEE_PAR_LE_CLIENT, ANNULEE_PAR_LE_RESTAU,
}
