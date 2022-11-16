package demo;

public class Client {
	private int idUtil;
	private String emailClient;
	private String mdp;
	private String nomClient;
	private String prenomClient;
	private String adrPost;

	public Client(int idUtil) {
		this.idUtil = idUtil;
	}

	public Client idUtil(int idUtil) {
		this.idUtil = idUtil;
		return this;
	}

	public Client emailClient(String emailClient) {
		this.emailClient = emailClient;
		return this;
	}

	public Client mdp(String mdp) {
		this.mdp = mdp;
		return this;
	}

	public Client nomClient(String nomClient) {
		this.nomClient = nomClient;
		return this;
	}

	public Client prenomClient(String prenomClient) {
		this.prenomClient = prenomClient;
		return this;
	}

	public Client adrPost(String adrPost) {
		this.adrPost = adrPost;
		return this;
	}

}
