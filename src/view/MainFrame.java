package view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
	public MainFrame() {
        setTitle("Application de gestion de stock");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== BARRE DE MENU =====
        JMenuBar menuBar = new JMenuBar();
        menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        // --- Menu Produits ---
        JMenu menuProduit = new JMenu("Produits");
        JMenuItem itemProduit = new JMenuItem("Gestion des produits");
        itemProduit.addActionListener(e -> new ProduitFrame().setVisible(true));
        menuProduit.add(itemProduit);

        // --- Menu Ventes ---
        JMenu menuVentes = new JMenu("Ventes");
        JMenuItem itemVentes = new JMenuItem("Gestion des ventes");
        itemVentes.addActionListener(e -> new VenteFrame().setVisible(true));
        menuVentes.add(itemVentes);

        // --- Menu Utilisateurs ---
        JMenu menuUtilisateurs = new JMenu("Utilisateurs");
        JMenuItem itemUtilisateurs = new JMenuItem("Gestion des utilisateurs");
        itemUtilisateurs.addActionListener(e -> new UtilisateurFrame().setVisible(true));
        menuUtilisateurs.add(itemUtilisateurs);

        menuBar.add(menuProduit);
        menuBar.add(menuVentes);
        menuBar.add(menuUtilisateurs);
        setJMenuBar(menuBar);

        // ===== PANNEAU D’ACCUEIL =====
        JPanel panelAccueil = new JPanel(new GridBagLayout());
        panelAccueil.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel lblTitre = new JLabel("Application de Gestion de Stock");
        lblTitre.setFont(new Font("Segoe UI", Font.BOLD, 24));
        panelAccueil.add(lblTitre, gbc);

        gbc.gridy++;
        JLabel lblSousTitre = new JLabel(
                "<html><center>" +
                "Gérez efficacement vos produits, ventes et utilisateurs<br>" +
                "à partir du menu ci-dessus." +
                "</center></html>"
        );
        lblSousTitre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSousTitre.setForeground(Color.DARK_GRAY);
        panelAccueil.add(lblSousTitre, gbc);

        add(panelAccueil, BorderLayout.CENTER);
    }
}
