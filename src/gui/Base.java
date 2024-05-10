package gui;
import database.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public abstract class Base extends JFrame {
    protected User user;
    public Base(String title) {
        initialize(title);
    }

    public Base(String title, User user) {
        this.user = user;
        initialize(title);
    }

    private void initialize(String title) {
        setTitle(title);
        setSize(500, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        //change icon
        try {
            Image icon = ImageIO.read(getClass().getResource("/gui/FishyBankingIconTBv2.png"));
            setIconImage(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setOceanTheme();
        addGuiComponents();
    }
    protected abstract void addGuiComponents();
    private void setOceanTheme() {
        Color oceanBlue = new Color(86, 146, 220);
        Color oceanLightBlue = new Color(136, 208, 243);
        Color oceanLighterBlue = new Color(207, 242, 255);
        getContentPane().setBackground(oceanLightBlue);
        UIManager.put("OptionPane.background", oceanLightBlue);
        UIManager.put("Panel.background", oceanLightBlue);
        UIManager.put("Button.background", oceanBlue);
        UIManager.put("TextField.background", oceanLighterBlue);
        UIManager.put("PasswordField.background", oceanLighterBlue);
    }
}
