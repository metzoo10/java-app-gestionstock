package view;

import dao.UtilisateurDAO;
import model.Utilisateur;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class UtilisateurFrame extends JFrame {

    private JTextField txtPrenom, txtNom, txtLogin;
    private JPasswordField txtMotDePasse;
    private JButton btnAjouter, btnAnnuler;
    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    @SuppressWarnings("unused")
	public UtilisateurFrame() {
        setTitle("Ajout d'utilisateur");
        setSize(450, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font font = new Font("Segoe UI", Font.BOLD, 13);

        // ===== PRENOM =====
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Prénom"), gbc);

        txtPrenom = new JTextField(15);
        txtPrenom.setFont(font);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(txtPrenom, gbc);

        // ===== NOM =====
        gbc.gridx = 0; gbc.gridy++;
        gbc.weightx = 0;
        panel.add(new JLabel("Nom"), gbc);

        txtNom = new JTextField(15);
        txtNom.setFont(font);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(txtNom, gbc);

        // ===== LOGIN =====
        gbc.gridx = 0; gbc.gridy++;
        gbc.weightx = 0;
        panel.add(new JLabel("Nom d'utilisateur"), gbc);

        txtLogin = new JTextField(15);
        txtLogin.setFont(font);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(txtLogin, gbc);

        // ===== MOT DE PASSE =====
        gbc.gridx = 0; gbc.gridy++;
        gbc.weightx = 0;
        panel.add(new JLabel("Mot de passe"), gbc);

        txtMotDePasse = new JPasswordField(15);
        txtMotDePasse.setFont(font);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(txtMotDePasse, gbc);

        // ===== BOUTONS =====
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));

        btnAjouter = new JButton("Ajouter");
        btnAjouter.setPreferredSize(new Dimension(120, 32));
        btnAjouter.setBackground(new Color(0, 153, 76));
        btnAjouter.setForeground(Color.WHITE);

        btnAnnuler = new JButton("Annuler");
        btnAnnuler.setPreferredSize(new Dimension(120, 32));
        btnAnnuler.setBackground(Color.RED);
        btnAnnuler.setForeground(Color.WHITE);

        panelBoutons.add(btnAjouter);
        panelBoutons.add(btnAnnuler);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        panel.add(panelBoutons, gbc);

        add(panel);

        // ===== ACTIONS =====
        btnAjouter.addActionListener(e -> ajouterUtilisateur());
        btnAnnuler.addActionListener(e -> dispose());
    }

    // ===== METHODE AJOUT UTILISATEUR =====
    private void ajouterUtilisateur() {
        String prenom = txtPrenom.getText().trim();
        String nom = txtNom.getText().trim();
        String login = txtLogin.getText().trim();
        String motDePasse = new String(txtMotDePasse.getPassword()).trim();

        if (prenom.isEmpty() || nom.isEmpty() || login.isEmpty() || motDePasse.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs !");
            return;
        }

        if (login.length() < 4) {
            JOptionPane.showMessageDialog(this, "Le nom d'utilisateur doit contenir au moins 4 caractères");
            return;
        }

        btnAjouter.setEnabled(false);

        try {
            Utilisateur u = new Utilisateur(prenom, nom, login, motDePasse);
            boolean ok = utilisateurDAO.ajouterUtilisateur(u);

            if (ok) {
                JOptionPane.showMessageDialog(this, "L'utilisateur a été ajouté avec succès !");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Le nom d'utilisateur est déjà utilisé !");
                btnAjouter.setEnabled(true);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
            btnAjouter.setEnabled(true);
        }
    }
}
