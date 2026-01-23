package view;

import dao.UtilisateurDAO;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField txtLogin;
    private JPasswordField txtPassword;

    @SuppressWarnings("unused")
	public LoginFrame() {
        setTitle("Authentification");
        setSize(380, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font font = new Font("Segoe UI", Font.PLAIN, 13);

        // ===== TITRE =====
        JLabel lblTitre = new JLabel("Connexion à l'application", SwingConstants.CENTER);
        lblTitre.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitre, gbc);

        // ===== LOGIN =====
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Nom d'utilisateur"), gbc);

        txtLogin = new JTextField(15);
        txtLogin.setFont(font);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(txtLogin, gbc);

        // ===== MOT DE PASSE =====
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        panel.add(new JLabel("Mot de passe"), gbc);

        txtPassword = new JPasswordField(15);
        txtPassword.setFont(font);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(txtPassword, gbc);

        // ===== BOUTON =====
        JButton btnLogin = new JButton("Se connecter");
        btnLogin.setPreferredSize(new Dimension(160, 35));
        btnLogin.setBackground(new Color(0, 102, 204));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnLogin, gbc);

        add(panel);

        // ===== ACTION =====
        btnLogin.addActionListener(e -> authentifier());

        // Appuyer sur Entrée déclenche la connexion
        
        getRootPane().setDefaultButton(btnLogin);
    }

    private void authentifier() {
        String login = txtLogin.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (login.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs !");
            return;
        }

        UtilisateurDAO dao = new UtilisateurDAO();
        if (dao.login(login, password)) {
            new MainFrame().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Nom d'utilisateur ou mot de passe incorrect !",
                    "Erreur d'authentification",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
