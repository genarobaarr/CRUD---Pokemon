package com.pokemon.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import com.pokemon.dao.LoginFormDAO;
import com.pokemon.dao.LoginFormDAOImp;
import com.pokemon.model.Login;

public class LoginForm extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;

    LoginFormDAO loginFormDAO = new LoginFormDAOImp();

    public LoginForm() {
        setTitle("Inicio de Sesión");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana

        initComponents();
    }

    private void initComponents () {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));


        usernameField = new JTextField();
        passwordField = new JPasswordField();
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(Color.RED);

        JButton loginButton = new JButton("Iniciar Sesión");
        loginButton.addActionListener(new LoginAction());

        panel.add(new JLabel("Usuario:", SwingConstants.CENTER));
        panel.add(usernameField);
        panel.add(new JLabel("Contraseña:", SwingConstants.CENTER));
        panel.add(passwordField);

        add(panel, BorderLayout.CENTER);
        add(loginButton, BorderLayout.SOUTH);
        add(messageLabel, BorderLayout.NORTH);
    }

    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (authenticate(username, password)) {
                messageLabel.setText("Inicio de sesión exitoso!");
                JOptionPane.showMessageDialog(LoginForm.this, "Bienvenido " + username + "!");
                dispose();
                new PokemonManager();

            } else {
                messageLabel.setText("Usuario o contraseña incorrectos...");
            }
        }

        private boolean authenticate(String username, String password) {
            try {
                LoginFormDAO loginFormDAO = new LoginFormDAOImp();
                List<Login> users = loginFormDAO.readAll();

                for (Login user : users) {

                    if (username.equals(user.getUser()) && password.equals(user.getPassword())) {
                        return true;
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return false;
        }
    }

    public static void main (String[]args){
        SwingUtilities.invokeLater(() -> {
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
        });
    }

}

