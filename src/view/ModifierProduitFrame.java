package view;

import dao.ProduitDAO;
import model.Produit;

import javax.swing.*;
import java.awt.*;

public class ModifierProduitFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField txtNom, txtQteEntree, txtPrixAchat, txtPrixVente;
    private JButton btnModifier, btnAnnuler;

    private final ProduitDAO produitDAO = new ProduitDAO();
    private final ProduitFrame produitFrame;
    private final Produit produit;

    @SuppressWarnings("unused")
	public ModifierProduitFrame(ProduitFrame produitFrame, Produit produit) {
        this.produitFrame = produitFrame;
        this.produit = produit;

        setTitle("Modifier le produit");
        setSize(460, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font font = new Font("Segoe UI", Font.PLAIN, 13);

        // ===== NOM =====
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nom du produit"), gbc);

        txtNom = new JTextField(15);
        txtNom.setFont(font);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(txtNom, gbc);

        // ===== QUANTITÉ =====
        gbc.gridx = 0; gbc.gridy++;
        gbc.weightx = 0;
        panel.add(new JLabel("Quantité entrée"), gbc);

        txtQteEntree = new JTextField(15);
        txtQteEntree.setFont(font);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(txtQteEntree, gbc);

        // ===== PRIX ACHAT =====
        gbc.gridx = 0; gbc.gridy++;
        gbc.weightx = 0;
        panel.add(new JLabel("Prix d'achat"), gbc);

        txtPrixAchat = new JTextField(15);
        txtPrixAchat.setFont(font);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(txtPrixAchat, gbc);

        // ===== PRIX VENTE =====
        gbc.gridx = 0; gbc.gridy++;
        gbc.weightx = 0;
        panel.add(new JLabel("Prix de vente"), gbc);

        txtPrixVente = new JTextField(15);
        txtPrixVente.setFont(font);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(txtPrixVente, gbc);

        // ===== BOUTONS =====
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));

        btnModifier = new JButton("Modifier");
        btnModifier.setPreferredSize(new Dimension(120, 32));
        btnModifier.setBackground(new Color(0, 102, 204));
        btnModifier.setForeground(Color.WHITE);

        btnAnnuler = new JButton("Annuler");
        btnAnnuler.setPreferredSize(new Dimension(120, 32));
        btnAnnuler.setBackground(Color.RED);
        btnAnnuler.setForeground(Color.WHITE);

        panelBoutons.add(btnModifier);
        panelBoutons.add(btnAnnuler);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        panel.add(panelBoutons, gbc);

        add(panel);

        remplirChamps();

        // ===== ACTIONS =====
        btnModifier.addActionListener(e -> modifierProduit());
        btnAnnuler.addActionListener(e -> dispose());
    }

    private void remplirChamps() {
        if (produit != null) {
            txtNom.setText(produit.getNom());
            txtQteEntree.setText(String.valueOf(produit.getQuantiteEntree()));
            txtPrixAchat.setText(String.valueOf(produit.getPrixAchat()));
            txtPrixVente.setText(String.valueOf(produit.getPrixVente()));
        }
    }

    private boolean validerChamps() {
        if (txtNom.getText().trim().isEmpty()
                || txtQteEntree.getText().trim().isEmpty()
                || txtPrixAchat.getText().trim().isEmpty()
                || txtPrixVente.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Tous les champs doivent être remplis.");
            return false;
        }

        try {
            int qte = Integer.parseInt(txtQteEntree.getText().trim());
            double prixAchat = Double.parseDouble(txtPrixAchat.getText().trim());
            double prixVente = Double.parseDouble(txtPrixVente.getText().trim());

            if (qte < 0 || prixAchat < 0 || prixVente < 0) {
                JOptionPane.showMessageDialog(this, "Les valeurs doivent être positives.");
                return false;
            }

            if (prixVente < prixAchat) {
                JOptionPane.showMessageDialog(
                        this,
                        "Le prix de vente ne peut pas être inférieur au prix d'achat."
                );
                return false;
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Veuillez saisir des valeurs numériques valides."
            );
            return false;
        }

        return true;
    }

    private void modifierProduit() {
        if (!validerChamps()) return;

        produit.setNom(txtNom.getText().trim());
        produit.setQuantiteEntree(Integer.parseInt(txtQteEntree.getText().trim()));
        produit.setPrixAchat(Double.parseDouble(txtPrixAchat.getText().trim()));
        produit.setPrixVente(Double.parseDouble(txtPrixVente.getText().trim()));

        boolean ok = produitDAO.modifierProduit(produit);

        if (ok) {
            JOptionPane.showMessageDialog(this, "Le produit a été modifié avec succès.");
            if (produitFrame != null) {
                produitFrame.chargerProduits();
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification du produit, veuillez réessayer !");
        }
    }
}
