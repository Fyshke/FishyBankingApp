package gui;
import database.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FishyBanking extends Base implements ActionListener {

    private JTextField currentBalanceField;
    public JTextField getCurrentBalanceField() { return currentBalanceField; };
    public FishyBanking (User user) {
        super("FishyBanking", user);
    }

    @Override
    protected void addGuiComponents() {
        //welcome
        String welcome = "<html>" + "<body style = 'text-align:center'>" +
                "<b>Hello " + user.getUsername() + "," + "<b><br>" +
                "Welcome to FishyBanking!</body></html>";
        JLabel welcomeMsg = new JLabel(welcome);
        welcomeMsg.setBounds(0,20,getWidth() - 10,40);
        welcomeMsg.setFont(new Font("Dialog",Font.PLAIN,16));
        welcomeMsg.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeMsg);
        //current balance
        JLabel currentBalanceLabel = new JLabel("Current Balance");
        currentBalanceLabel.setBounds(0,80,getWidth() - 10,30);
        currentBalanceLabel.setFont(new Font("Dialog",Font.BOLD,22));
        currentBalanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(currentBalanceLabel);
        currentBalanceField = new JTextField("€" + user.getCurrentBalance());
        currentBalanceField.setBounds(15,120,getWidth() - 50,40);
        currentBalanceField.setFont(new Font("Dialog",Font.BOLD,28));
        currentBalanceField.setHorizontalAlignment(SwingConstants.RIGHT);
        currentBalanceField.setEditable(false);
        add(currentBalanceField);
        //deposit
        JButton depositButton = new JButton("Deposit");
        depositButton.setBounds(15,180,getWidth() - 50,50);
        depositButton.setFont(new Font("Dialog",Font.BOLD,22));
        depositButton.addActionListener(this);
        add(depositButton);
        //withdraw
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(15,240,getWidth() - 50,50);
        withdrawButton.setFont(new Font("Dialog",Font.BOLD,22));
        withdrawButton.addActionListener(this);
        add(withdrawButton);
        //past transactions
        JButton pastTransactionsButton = new JButton("Past Transactions");
        pastTransactionsButton.setBounds(15,360,getWidth() - 50,50);
        pastTransactionsButton.setFont(new Font("Dialog",Font.BOLD,22));
        pastTransactionsButton.addActionListener(this);
        add(pastTransactionsButton);
        //transfer
        JButton transferButton = new JButton("Transfer");
        transferButton.setBounds(15,300,getWidth() - 50,50);
        transferButton.setFont(new Font("Dialog",Font.BOLD,22));
        transferButton.addActionListener(this);
        add(transferButton);
        //logout
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(15,460,getWidth() - 50,50);
        logoutButton.setFont(new Font("Dialog",Font.BOLD,22));
        logoutButton.addActionListener(this);
        add(logoutButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonPressed = e.getActionCommand();
        if(buttonPressed.equalsIgnoreCase("Logout")) {

            new Login().setVisible(true);
            this.dispose();
            return;
        }
        FishyBankingDialog FishyBankingDialog = new FishyBankingDialog(this, user);
        FishyBankingDialog.setTitle(buttonPressed);
        if (buttonPressed.equalsIgnoreCase("Deposit") || buttonPressed.equalsIgnoreCase("Withdraw")
                || buttonPressed.equalsIgnoreCase("Transfer")) {
            FishyBankingDialog.addCurrentBalanceAndAmount();
            FishyBankingDialog.addActionButton(buttonPressed);
            if(buttonPressed.equalsIgnoreCase("Transfer")) {
                FishyBankingDialog.addUserField();
            }
        } else if (buttonPressed.equalsIgnoreCase("Past Transactions")){
            FishyBankingDialog.addPastTransactions();
        }
        FishyBankingDialog.setVisible(true);
    }
}
