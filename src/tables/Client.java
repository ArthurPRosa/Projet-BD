package demo.BD.tables;

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

    public static void parseList() {
    }

    public static void parseAdd() {
    }

    public static void parseDel() {
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

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public void setAdrPost(String adrPost) {
        this.adrPost = adrPost;
    }

    @Override
    public String toString() {
        return "Client{" +
                "idUtil=" + idUtil +
                ", emailClient='" + emailClient + '\'' +
                ", mdp='" + mdp + '\'' +
                ", nomClient='" + nomClient + '\'' +
                ", prenomClient='" + prenomClient + '\'' +
                ", adrPost='" + adrPost + '\'' +
                '}';
    }
}
