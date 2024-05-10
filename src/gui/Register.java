package gui;

import com.mysql.cj.log.Log;
import database.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Register extends Base{

    public Register() {
        super("FishyBanking Register");
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
        JLabel usernameLabel = new JLabel("Username (must be 6 characters long):");
        usernameLabel.setBounds(20,120, getWidth() - 30,24);
        usernameLabel.setFont(new Font("Dialog",Font.PLAIN, 20));
        add(usernameLabel);
        JTextField usernameField = new JTextField();
        usernameField.setBounds(20, 160, getWidth() - 50, 40);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(usernameField);
        //password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20,220, getWidth() - 50,24);
        passwordLabel.setFont(new Font("Dialog",Font.PLAIN, 20));
        add(passwordLabel);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(20, 260, getWidth() - 50, 40);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordField);
        //rePassword
        JLabel rePasswordLabel = new JLabel("Repeat the password:");
        rePasswordLabel.setBounds(20,320, getWidth() - 50,24);
        rePasswordLabel.setFont(new Font("Dialog",Font.PLAIN, 20));
        add(rePasswordLabel);
        JPasswordField rePasswordField = new JPasswordField();
        rePasswordField.setBounds(20, 360, getWidth() - 50, 40);
        rePasswordField.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(rePasswordField);
        //register
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(20, 460, getWidth() - 50, 40);
        registerButton.setFont(new Font("Dialog", Font.BOLD, 20));
        registerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                String rePassword = String.valueOf(rePasswordField.getPassword());
                if(validateUserInput(username, password, rePassword)) {
                    if (Database.register(username, password)) {
                        Register.this.dispose();
                        Login login = new Login();
                        login.setVisible(true);
                        JOptionPane.showMessageDialog(login, "Your account has been successfully registered!");
                    } else {
                        JOptionPane.showMessageDialog(Register.this, "That username is already used, try another.");
                    }
                } else {
                    JOptionPane.showMessageDialog(Register.this,
                            "Your username must be atleast 6 characters long\n" +
                            "or your passwords aren't matching.");
                }
            }
        });
        add(registerButton);
        //login
        JLabel loginLabel = new JLabel("<html><a href=\"#\">Login here</a>");
        loginLabel.setBounds(0, 510, getWidth() - 10, 30);
        loginLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Register.this.dispose();
                new Login().setVisible(true);
            }
        });
        add(loginLabel);
    }

    private boolean validateUserInput(String username, String password, String rePassword){
        if(username.isEmpty() || password.isEmpty() || rePassword.isEmpty())
            return false;
        if(username.length() < 6)
            return false;
        return password.equals(rePassword);
    }
}
