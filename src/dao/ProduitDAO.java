package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Produit;
import utils.DBConnexion;

public class ProduitDAO {

    // ajouter un produit
    public boolean ajouterProduit(Produit p) {
        if (p.getNom().trim().isEmpty() || p.getQuantiteEntree() < 0 || p.getPrixAchat() < 0 || p.getPrixVente() < 0) {
            return false;
        }

        String checkSql = "SELECT id FROM produits WHERE nom=?";
        String insertSql = "INSERT INTO produits(nom, quantite_entree, quantite_sortie, prix_achat, prix_vente) VALUES (?,?,?,?,?)";

        try (Connection conn = DBConnexion.getConnection();
             PreparedStatement checkPs = conn.prepareStatement(checkSql);
             PreparedStatement insertPs = conn.prepareStatement(insertSql)) {

            checkPs.setString(1, p.getNom());
            ResultSet rs = checkPs.executeQuery();
            if (rs.next()) {
                System.out.println("Produit déjà existant");
                return false;
            }

            insertPs.setString(1, p.getNom());
            insertPs.setInt(2, p.getQuantiteEntree());
            insertPs.setInt(3, 0);
            insertPs.setDouble(4, p.getPrixAchat());
            insertPs.setDouble(5, p.getPrixVente());

            return insertPs.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // supprimer un produit
    public boolean supprimerProduit(int id) {
        String sql = "DELETE FROM produits WHERE id=? AND quantite_sortie=0";
        try (Connection conn = DBConnexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // modifier un produit
    public boolean modifierProduit(Produit p) {
        if (p.getNom().trim().isEmpty() || p.getQuantiteEntree() < 0 || p.getPrixAchat() < 0 || p.getPrixVente() < 0) {
            return false;
        }

        String sql = "UPDATE produits SET nom=?, quantite_entree=?, prix_achat=?, prix_vente=? WHERE id=?";
        try (Connection conn = DBConnexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNom());
            ps.setInt(2, p.getQuantiteEntree());
            ps.setDouble(3, p.getPrixAchat());
            ps.setDouble(4, p.getPrixVente());
            ps.setInt(5, p.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ajouter du stock
    public boolean ajouterStock(int id, int qte) {
        if (qte <= 0) return false;

        String sql = "UPDATE produits SET quantite_entree = quantite_entree + ? WHERE id=?";
        try (Connection conn = DBConnexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, qte);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // récupérer tous les produits
    public List<Produit> getAllProduits() {
        List<Produit> list = new ArrayList<>();
        String sql = "SELECT * FROM produits";

        try (Connection conn = DBConnexion.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Produit p = new Produit();
                p.setId(rs.getInt("id"));
                p.setNom(rs.getString("nom"));
                p.setQuantiteEntree(rs.getInt("quantite_entree"));
                p.setQuantiteSortie(rs.getInt("quantite_sortie"));
                p.setPrixAchat(rs.getDouble("prix_achat"));
                p.setPrixVente(rs.getDouble("prix_vente"));
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // détecter le stock faible
    public boolean isStockFaible(Produit p) {
        return p.getStockRestant() < 10;
    }
}
