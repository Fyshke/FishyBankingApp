package gui;

import database.Database;
import database.Transaction;
import database.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;

public class FishyBankingDialog extends JDialog implements ActionListener {

    private User user;
    private FishyBanking FishyBanking;
    private JLabel balanceLabel, enterAmountLabel, enterUserLabel;
    private JTextField enterAmountField, enterUserField;
    private JButton actionButton;
    private JPanel pastTransactionsPanel;
    private ArrayList<Transaction> pastTransactions;

    public FishyBankingDialog (FishyBanking FishyBanking, User user) {
        setSize(400,400);
        setModal(true);
        setLocationRelativeTo(FishyBanking);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        this.FishyBanking = FishyBanking;
        this.user = user;
    }

    public void addCurrentBalanceAndAmount(){
        //balance
        balanceLabel = new JLabel("Balance: €" + user.getCurrentBalance());
        balanceLabel.setBounds(0, 10, getWidth() - 20, 20);
        balanceLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(balanceLabel);
        //amount
        enterAmountLabel = new JLabel("Enter amount: ");
        enterAmountLabel.setBounds(0, 50, getWidth() - 20, 20);
        enterAmountLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        enterAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterAmountLabel);
        enterAmountField = new JTextField();
        enterAmountField.setBounds(15, 80, getWidth() - 50, 40);
        enterAmountField.setFont(new Font("Dialog", Font.BOLD, 20));
        enterAmountField.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterAmountField);
    }

    public void addActionButton(String actionTypeButton) {
        actionButton = new JButton(actionTypeButton);
        actionButton.setBounds(15, 300, getWidth() - 50, 40);
        actionButton.setFont(new Font("Dialog", Font.BOLD, 20));
        actionButton.addActionListener(this);
        add(actionButton);
    }

    public void addUserField() {
        enterUserLabel = new JLabel("Enter user: ");
        enterUserLabel.setBounds(0, 160, getWidth() - 20, 20);
        enterUserLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        enterUserLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterUserLabel);
        enterUserField = new JTextField();
        enterUserField.setBounds(15, 190, getWidth() - 50, 40);
        enterUserField.setFont(new Font("Dialog", Font.BOLD, 20));
        enterUserField.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterUserField);
    }

    public void addPastTransactions() {
        pastTransactionsPanel = new JPanel();
        pastTransactionsPanel.setLayout(new BoxLayout(pastTransactionsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(pastTransactionsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 20, getWidth() - 15, getHeight() - 80);
        pastTransactions = Database.getPastTransactions(user);
        //adds newer transactions on top
        for(int i = pastTransactions.size() - 1; i >= 0; i--) {
            Transaction pastTransaction = pastTransactions.get(i);
            JPanel pastTransactionsContainer = new JPanel();
            pastTransactionsContainer.setLayout(new BorderLayout());
            JLabel transactionTypeLabel = new JLabel(pastTransaction.transactionType());
            transactionTypeLabel.setFont(new Font("Dialog", Font.BOLD, 20));
            JLabel transactionAmountLabel = new JLabel(String.valueOf(pastTransaction.transactionAmount()));
            transactionAmountLabel.setFont(new Font("Dialog", Font.BOLD, 20));
            JLabel transactionDateLabel = new JLabel(String.valueOf(pastTransaction.transactionDate()));
            transactionDateLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            pastTransactionsContainer.add(transactionTypeLabel, BorderLayout.WEST);
            pastTransactionsContainer.add(transactionAmountLabel, BorderLayout.EAST);
            pastTransactionsContainer.add(transactionDateLabel, BorderLayout.SOUTH);
            pastTransactionsContainer.setBackground(new Color(169, 232, 252));
            pastTransactionsContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            pastTransactionsPanel.add(pastTransactionsContainer);
        }
        add(scrollPane);
    }

    private void handleTransaction (String transactionType, float amountValue) {
        Transaction transaction;
        if (transactionType.equalsIgnoreCase("Deposit")) {
            user.setCurrentBalance(user.getCurrentBalance().add(new BigDecimal(amountValue)));
            transaction = new Transaction(user.getId(), transactionType, new BigDecimal(amountValue), null);
        } else {
            user.setCurrentBalance(user.getCurrentBalance().subtract(new BigDecimal(amountValue)));
            transaction = new Transaction(user.getId(), transactionType, new BigDecimal(-amountValue), null);
        }
        if(Database.addTransactionToDatabase(transaction) && Database.updateCurrentBalance(user)) {
            JOptionPane.showMessageDialog(this, transactionType + " Success!");
            resetFieldAndUpdateBalance();
        } else {
            JOptionPane.showMessageDialog(this, transactionType + " Failed.");
        }
    }

    private void resetFieldAndUpdateBalance() {
        enterAmountField.setText("");
        if (enterUserField != null){
            enterUserField.setText("");
        }
        balanceLabel.setText("Balance: €" + user.getCurrentBalance());
        FishyBanking.getCurrentBalanceField().setText("€" + user.getCurrentBalance());
    }

    private void handleTransfer( User user, String transferredUser, float amount) {
        if (transferredUser.equalsIgnoreCase(user.getUsername())) {
            JOptionPane.showMessageDialog(this, "Error: You can't transfer money to yourself.");
        }
        else if(Database.transfer(user, transferredUser, amount)) {
            JOptionPane.showMessageDialog(this, "Transfer Successful!");
            resetFieldAndUpdateBalance();
        } else {
            JOptionPane.showMessageDialog(this, "Transfer Failed.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonPressed = e.getActionCommand();
        //amount value
        float amountValue = Float.parseFloat(enterAmountField.getText());
        if (amountValue <= 0) {
            JOptionPane.showMessageDialog(this, "Error: You can't enter a negative amount or 0.");
        }
        else if (buttonPressed.equalsIgnoreCase("Deposit")) {
            handleTransaction(buttonPressed, amountValue);
        } else {
            int result = user.getCurrentBalance().compareTo(BigDecimal.valueOf(amountValue));
            if (result < 0) {
                JOptionPane.showMessageDialog(this, "Error: Input value is more than your available balance.");
                return;
            }
            if(buttonPressed.equalsIgnoreCase("Withdraw")) {
                handleTransaction(buttonPressed, amountValue);
            } else {
                String transferredUser = enterUserField.getText();
                handleTransfer(user, transferredUser, amountValue);
            }
        }
    }
}
