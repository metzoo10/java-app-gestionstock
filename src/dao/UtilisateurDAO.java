package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Utilisateur;
import utils.DBConnexion;

public class UtilisateurDAO {

    // authentification simple
    public boolean login(String login, String password) {
        String sql = "SELECT id FROM utilisateurs WHERE login=? AND mot_de_passe=?";
        try (Connection conn = DBConnexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, login);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) {
            System.err.println("Erreur de nom d'utilisateur : " + e.getMessage());
            return false;
        }
    }

    // Ajouter un utilisateur
    public boolean ajouterUtilisateur(Utilisateur u) throws Exception {
        // vérifier si login existe déjà
        String checkSql = "SELECT id FROM utilisateurs WHERE login=?";
        try (Connection conn = DBConnexion.getConnection();
             PreparedStatement checkPs = conn.prepareStatement(checkSql)) {

            checkPs.setString(1, u.getLogin());
            try (ResultSet rs = checkPs.executeQuery()) {
                if (rs.next()) {
                    return false;
                }
            }

            // Ajouter l'utilisateur
            String insertSql = "INSERT INTO utilisateurs (prenom, nom, login, mot_de_passe) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                insertPs.setString(1, u.getPrenom());
                insertPs.setString(2, u.getNom());
                insertPs.setString(3, u.getLogin());
                insertPs.setString(4, u.getMotDePasse());

                return insertPs.executeUpdate() > 0;
            }

        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
            throw e;
        }
    }
}
