package gui;

import database.Database;
import database.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends Base {

    public Login() {
        super("FishyBanking Login");
    }
    @Override
    protected void addGuiComponents() {
        //FishyBanking
        JLabel FishyBankingLabel = new JLabel("FishyBanking");
        FishyBankingLabel.setBounds(0, 20, super.getWidth(), 40);
        FishyBankingLabel.setFont(new Font("Dialog", Font.BOLD, 32));
        FishyBankingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(FishyBankingLabel);
        //username
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20,120, getWidth() - 30,24);
        usernameLabel.setFont(new Font("Dialog",Font.PLAIN, 20));
        add(usernameLabel);
        JTextField usernameField = new JTextField();
        usernameField.setBounds(20, 160, getWidth() - 50, 40);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(usernameField);
        //password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20,280, getWidth() - 50,24);
        passwordLabel.setFont(new Font("Dialog",Font.PLAIN, 20));
        add(passwordLabel);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(20, 320, getWidth() - 50, 40);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordField);
        //login
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(20, 460, getWidth() - 50, 40);
        loginButton.setFont(new Font("Dialog", Font.BOLD, 20));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                User user = Database.validateLogin(username, password);
                if (user != null) {
                    Login.this.dispose();
                    FishyBanking FishyBanking = new FishyBanking(user);
                    FishyBanking.setVisible(true);
                    JOptionPane.showMessageDialog(FishyBanking, "Login successful!");
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Login failed.");
                }
            }
        });
        add(loginButton);
        //register
        JLabel registerLabel = new JLabel("<html><a href=\"#\">Register here if you don't have an account</a>");
        registerLabel.setBounds(0, 510, getWidth() - 10, 30);
        registerLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Login.this.dispose();
                new Register().setVisible(true);
            }
        });
        add(registerLabel);
    }
}
