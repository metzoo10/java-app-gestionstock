package view;

import dao.ProduitDAO;
import model.Produit;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

@SuppressWarnings("serial")
public class ProduitFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtNom, txtQteEntree, txtPrixAchat, txtPrixVente;
    private ProduitDAO produitDAO = new ProduitDAO();

    @SuppressWarnings({ "unused" })
    public ProduitFrame() {
        setTitle("Gestion des produits");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ===== TABLE =====
        model = new DefaultTableModel(
                new String[]{"ID", "Produit", "Qté entrée", "Qté sortie", "Prix d'achat", "Prix de vente", "Stock"}, 0
        );
        table = new JTable(model);

        // alerte stock faible
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                int stock = Integer.parseInt(table.getValueAt(row, 6).toString());

                if (!isSelected) {
                    if (stock < 10) {
                        c.setBackground(Color.RED);
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                }
                return c;
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== FORMULAIRE CHAMPS =====
        JPanel panelLabels = new JPanel(new GridLayout(1, 4, 10, 10));
        panelLabels.add(new JLabel("Nom du produit"));
        panelLabels.add(new JLabel("Quantité entrée"));
        panelLabels.add(new JLabel("Prix d'achat"));
        panelLabels.add(new JLabel("Prix de vente"));

        JPanel panelChamps = new JPanel(new GridLayout(1, 4, 10, 10));
        txtNom = new JTextField();
        txtQteEntree = new JTextField();
        txtPrixAchat = new JTextField();
        txtPrixVente = new JTextField();
        panelChamps.add(txtNom);
        panelChamps.add(txtQteEntree);
        panelChamps.add(txtPrixAchat);
        panelChamps.add(txtPrixVente);

	     // =======================
	     // PANEL DES BOUTONS
	     // =======================
	     JPanel panelButtons = new JPanel();
	     panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
	     panelButtons.setBackground(Color.WHITE);
	
	     // =======================
	     // STYLE COMMUN
	     // =======================
	     Dimension btnSize = new Dimension(150, 38);
	     Font btnFont = new Font("Segoe UI", Font.BOLD, 13);
	
	     // =======================
	     // BOUTONS
	     // =======================
	     JButton btnAjouter = new JButton("Ajouter");
	     JButton btnSupprimer = new JButton("Supprimer");
	     JButton btnModifier = new JButton("Modifier");
	     JButton btnAjouterStock = new JButton("Ajouter le stock");
	
	     // =======================
	     // STYLE DES BOUTONS
	     // =======================
	     styleButton(btnAjouter, btnSize, btnFont, new Color(0, 153, 76));
	     styleButton(btnModifier, btnSize, btnFont, new Color(0, 102, 204));
	     styleButton(btnSupprimer, btnSize, btnFont, new Color(204, 0, 0));
	     styleButton(btnAjouterStock, btnSize, btnFont, new Color(120, 120, 120));
	
	     // =======================
	     // AJOUT AU PANEL
	     // =======================
	     panelButtons.add(btnAjouter);
	     panelButtons.add(btnSupprimer);
	     panelButtons.add(btnModifier);
	     panelButtons.add(btnAjouterStock);


        // ===== COMPOSER LE FORMULAIRE =====
        JPanel panelFormulaire = new JPanel(new BorderLayout(5, 5));
        panelFormulaire.add(panelLabels, BorderLayout.NORTH);
        panelFormulaire.add(panelChamps, BorderLayout.CENTER);
        panelFormulaire.add(panelButtons, BorderLayout.SOUTH);

        add(panelFormulaire, BorderLayout.SOUTH);

        // ===== ACTIONS BOUTONS =====
        btnAjouter.addActionListener(e -> ajouterProduit());
        btnSupprimer.addActionListener(e -> supprimerProduit());
        btnModifier.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                        "Veuillez sélectionner un produit à modifier.");
                return;
            }

            Produit produit = new Produit();
            produit.setId((int) table.getValueAt(selectedRow, 0));
            produit.setNom(table.getValueAt(selectedRow, 1).toString());
            produit.setQuantiteEntree(Integer.parseInt(
                    table.getValueAt(selectedRow, 2).toString()));
            produit.setQuantiteSortie(Integer.parseInt(
                    table.getValueAt(selectedRow, 3).toString()));
            produit.setPrixAchat(Double.parseDouble(
                    table.getValueAt(selectedRow, 4).toString()));
            produit.setPrixVente(Double.parseDouble(
                    table.getValueAt(selectedRow, 5).toString()));

            ModifierProduitFrame mpf =
                    new ModifierProduitFrame(this, produit);
            mpf.setVisible(true);
        });

        btnAjouterStock.addActionListener(e -> ajouterStock());

        chargerProduits();
    }

    // ===== MÉTHODE AJOUT =====
    private void ajouterProduit() {
        if (!validerChamps()) return;

        try {
            Produit p = new Produit();
            p.setNom(txtNom.getText().trim());
            p.setQuantiteEntree(Integer.parseInt(txtQteEntree.getText().trim()));
            p.setPrixAchat(Double.parseDouble(txtPrixAchat.getText().trim()));
            p.setPrixVente(Double.parseDouble(txtPrixVente.getText().trim()));

            boolean ok = produitDAO.ajouterProduit(p);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Le produit a été ajouté avec succès !");
                chargerProduits();
                viderChamps();
            } else {
                JOptionPane.showMessageDialog(this, "Le produit existe déjà !");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // ===== MÉTHODE SUPPRIMER =====
    private void supprimerProduit() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un produit à supprimer");
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        String nom = table.getValueAt(selectedRow, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Voulez-vous vraiment supprimer le produit : " + nom + " ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean ok = produitDAO.supprimerProduit(id);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Le produit a été supprimé avec succès !");
                    chargerProduits();
                } else {
                    JOptionPane.showMessageDialog(this, "Impossible de supprimer un produit déjà vendu !");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    // ===== MÉTHODE AJOUTER STOCK =====
    private void ajouterStock() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un produit pour ajouter du stock");
            return;
        }

        String qteStr = JOptionPane.showInputDialog(this, "Quantité à ajouter :");
        if (qteStr == null) return;

        try {
            int qte = Integer.parseInt(qteStr);
            if (qte <= 0) {
                JOptionPane.showMessageDialog(this, "Quantité invalide");
                return;
            }

            int id = (int) table.getValueAt(selectedRow, 0);
            boolean ok = produitDAO.ajouterStock(id, qte);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Le stock a été ajouté avec succès");
                chargerProduits();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de stock");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez saisir un nombre valide !");
        }
    }

    // ===== RAFRAÎCHIR TABLEAU =====
    void chargerProduits() {
        model.setRowCount(0);
        List<Produit> produits = produitDAO.getAllProduits();

        for (Produit p : produits) {
            model.addRow(new Object[]{
                    p.getId(),
                    p.getNom(),
                    p.getQuantiteEntree(),
                    p.getQuantiteSortie(),
                    p.getPrixAchat(),
                    p.getPrixVente(),
                    p.getStockRestant()
            });
        }
    }

    // ===== VIDER LES CHAMPS =====
    private void viderChamps() {
        txtNom.setText("");
        txtQteEntree.setText("");
        txtPrixAchat.setText("");
        txtPrixVente.setText("");
    }

    // ===== VALIDATION CHAMPS =====
    private boolean validerChamps() {
        String nom = txtNom.getText().trim();
        String qteStr = txtQteEntree.getText().trim();
        String prixAchatStr = txtPrixAchat.getText().trim();
        String prixVenteStr = txtPrixVente.getText().trim();

        if (nom.isEmpty() || qteStr.isEmpty() || prixAchatStr.isEmpty() || prixVenteStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs !");
            return false;
        }

        int qte;
        double prixAchat, prixVente;
        try {
            qte = Integer.parseInt(qteStr);
            prixAchat = Double.parseDouble(prixAchatStr);
            prixVente = Double.parseDouble(prixVenteStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez saisir des valeurs numériques valides !");
            return false;
        }

        if (qte < 0 || prixAchat < 0 || prixVente < 0) {
            JOptionPane.showMessageDialog(this, "Les valeurs doivent être positives !");
            return false;
        }

        if (prixVente < prixAchat) {
            JOptionPane.showMessageDialog(this, "Le prix de vente doit être supérieur ou égal au prix d'achat !");
            return false;
        }

        return true;
    }
    
    private void styleButton(JButton button, Dimension size, Font font, Color bgColor) {
        button.setPreferredSize(size);
        button.setFont(font);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
    }
}
