package model;

public class Produit {

    private int id;
    private String nom;
    private int quantiteEntree;
    private int quantiteSortie;
    private double prixAchat;
    private double prixVente;

    public Produit() {
    }

    public Produit(String nom, int quantiteEntree, double prixAchat, double prixVente) {
        this.nom = nom;
        this.quantiteEntree = quantiteEntree;
        this.prixAchat = prixAchat;
        this.prixVente = prixVente;
        this.quantiteSortie = 0;
    }

    // ===== Getters =====
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public int getQuantiteEntree() {
        return quantiteEntree;
    }

    public int getQuantiteSortie() {
        return quantiteSortie;
    }

    public double getPrixAchat() {
        return prixAchat;
    }

    public double getPrixVente() {
        return prixVente;
    }

    public int getStockRestant() {
        return quantiteEntree - quantiteSortie;
    }

    // ===== Setters =====
    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setQuantiteEntree(int quantiteEntree) {
        this.quantiteEntree = quantiteEntree;
    }

    public void setQuantiteSortie(int quantiteSortie) {
        this.quantiteSortie = quantiteSortie;
    }

    public void setPrixAchat(double prixAchat) {
        this.prixAchat = prixAchat;
    }

    public void setPrixVente(double prixVente) {
        this.prixVente = prixVente;
    }

    @Override
    public String toString() {
        return nom;
    }
}
