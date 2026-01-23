package view;

import dao.ProduitDAO;
import dao.VenteDAO;
import model.Produit;
import model.Vente;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VenteFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JComboBox<Produit> comboProduits;
    private JTextField txtQuantite;
    private JTable tableVentes;
    private DefaultTableModel modelVentes;

    private final ProduitDAO produitDAO = new ProduitDAO();
    private final VenteDAO venteDAO = new VenteDAO();

    public VenteFrame() {
        setTitle("Gestion des ventes");
        setSize(700, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        add(creerPanelFormulaire(), BorderLayout.SOUTH);
        add(creerTableVentes(), BorderLayout.CENTER);

        chargerProduits();
        chargerVentes();
    }

    // ===== FORMULAIRE DE VENTE =====
    @SuppressWarnings("unused")
	private JPanel creerPanelFormulaire() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);

        JLabel lblProduit = new JLabel("Produit");
        lblProduit.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblProduit, gbc);

        comboProduits = new JComboBox<>();
        gbc.gridx = 1;
        panel.add(comboProduits, gbc);

        JLabel lblQte = new JLabel("Quantité");
        lblQte.setFont(labelFont);
        gbc.gridx = 2;
        panel.add(lblQte, gbc);

        txtQuantite = new JTextField(10);
        gbc.gridx = 3;
        panel.add(txtQuantite, gbc);

        JButton btnEnregistrer = new JButton("Enregistrer la vente");
        styleButton(btnEnregistrer, new Dimension(200, 38),
                new Color(0, 153, 76));

        btnEnregistrer.addActionListener(e -> enregistrerVente());

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnEnregistrer, gbc);

        return panel;
    }

    // ===== TABLE DES VENTES =====
    private JScrollPane creerTableVentes() {
        modelVentes = new DefaultTableModel(
                new String[]{"ID", "Produit", "Quantité", "Date"}, 0
        );

        tableVentes = new JTable(modelVentes);
        tableVentes.setRowHeight(24);
        tableVentes.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        tableVentes.setDefaultRenderer(Object.class,
                new DefaultTableCellRenderer() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table, Object value, boolean isSelected,
                            boolean hasFocus, int row, int column) {

                        Component c = super.getTableCellRendererComponent(
                                table, value, isSelected, hasFocus, row, column);

                        c.setBackground(isSelected
                                ? new Color(220, 235, 250)
                                : Color.WHITE);
                        c.setForeground(Color.BLACK);

                        return c;
                    }
                });

        return new JScrollPane(tableVentes);
    }

    // ===== logique métier =====
    private void chargerProduits() {
        comboProduits.removeAllItems();
        List<Produit> produits = produitDAO.getAllProduits();
        for (Produit p : produits) {
            if (p.getStockRestant() > 0) {
                comboProduits.addItem(p);
            }
        }
    }

    private void chargerVentes() {
        modelVentes.setRowCount(0);
        List<Vente> ventes = venteDAO.getAllVentes();
        DateTimeFormatter fmt =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Map<Integer, String> produitMap =
                produitDAO.getAllProduits().stream()
                        .collect(Collectors.toMap(
                                Produit::getId,
                                Produit::getNom));

        for (Vente v : ventes) {
            modelVentes.addRow(new Object[]{
                    v.getId(),
                    produitMap.getOrDefault(
                            v.getProduitId(), "Produit supprimé"),
                    v.getQuantite(),
                    v.getDate().format(fmt)
            });
        }
    }

    private void enregistrerVente() {
        Produit p = (Produit) comboProduits.getSelectedItem();
        if (p == null) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner un produit");
            return;
        }

        try {
            int qte = Integer.parseInt(txtQuantite.getText().trim());
            if (qte <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Quantité invalide");
                return;
            }

            Vente v = new Vente();
            v.setProduitId(p.getId());
            v.setQuantite(qte);

            if (venteDAO.enregistrerVente(v)) {
                JOptionPane.showMessageDialog(this,
                        "Vente enregistrée avec succès");
                txtQuantite.setText("");
                chargerVentes();
                chargerProduits();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Stock insuffisant");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez saisir un nombre valide");
        }
    }

    // ===== style boutons =====
    private void styleButton(JButton btn, Dimension size, Color bgColor) {
        btn.setPreferredSize(size);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
    }
}
