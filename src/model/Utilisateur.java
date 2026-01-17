package model;

public class Utilisateur {
    private int id;
    private String prenom;
    private String nom;
    private String login;
    private String motDePasse;

    public Utilisateur() {}

    public Utilisateur(String prenom, String nom, String login, String motDePasse) {
        this.prenom = prenom;
        this.nom = nom;
        this.login = login;
        this.motDePasse = motDePasse;
    }

    // Getters & Setters
    public int getId() {
		return id;
	}
    public String getPrenom() {
		return prenom;
	}
	public String getNom() {
		return nom;
	}
	public String getLogin() {
		return login;
	}
	public String getMotDePasse() {
		return motDePasse;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	
	@Override
	public String toString() {
		return prenom + " " + nom + " (" + login + ")";
	}
}
