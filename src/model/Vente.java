package model;

import java.time.LocalDateTime;

public class Vente {
    private int id;
    private int produitId;
    private int quantite;
    private LocalDateTime date;
    private String nomProduit;


    // Getters & Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getProduitId() {
        return produitId;
    }
    public void setProduitId(int produitId) {
        this.produitId = produitId;
    }

    public int getQuantite() {
        return quantite;
    }
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public String getNomProduit() {
    	return nomProduit;
    }
    public void setNomProduit(String nomProduit) {
    	this.nomProduit = nomProduit; 
    }
}
