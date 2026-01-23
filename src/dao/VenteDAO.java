package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Vente;
import utils.DBConnexion;

public class VenteDAO {

    // enregistrer une vente
	
    public boolean enregistrerVente(Vente v) {
        String sqlUpdate = "UPDATE produits SET quantite_sortie = quantite_sortie + ? " +
                           "WHERE id=? AND (quantite_entree - quantite_sortie) >= ?";
        String sqlInsert = "INSERT INTO ventes(produit_id, quantite) VALUES (?, ?)";

        try (Connection conn = DBConnexion.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
                 PreparedStatement psInsert = conn.prepareStatement(sqlInsert)) {

                // mise à jour du stock
                psUpdate.setInt(1, v.getQuantite());
                psUpdate.setInt(2, v.getProduitId());
                psUpdate.setInt(3, v.getQuantite());

                int updated = psUpdate.executeUpdate();
                if (updated == 0) {
                    conn.rollback();
                    return false;
                }

                psInsert.setInt(1, v.getProduitId());
                psInsert.setInt(2, v.getQuantite());
                psInsert.executeUpdate();

                conn.commit();
                return true;

            } catch (Exception ex) {
                conn.rollback();
                System.err.println("Erreur lors de l'enregistrement de la vente : " + ex.getMessage());
                return false;
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (Exception e) {
            System.err.println("Erreur de connexion ou transaction : " + e.getMessage());
            return false;
        }
    }

    // récupérer les ventes avec date et nom du produit
    
    public List<Vente> getAllVentes() {
        List<Vente> ventes = new ArrayList<>();
        String sql = "SELECT v.id, v.produit_id, v.quantite, v.date_vente, p.nom AS nomProduit " +
                     "FROM ventes v JOIN produits p ON v.produit_id = p.id " +
                     "ORDER BY v.date_vente DESC";

        try (Connection conn = DBConnexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Vente v = new Vente();
                v.setId(rs.getInt("id"));
                v.setProduitId(rs.getInt("produit_id"));
                v.setQuantite(rs.getInt("quantite"));
                v.setDate(rs.getTimestamp("date_vente").toLocalDateTime());
                v.setNomProduit(rs.getString("nomProduit"));
                ventes.add(v);
            }

        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des ventes : " + e.getMessage());
        }

        return ventes;
    }
}
