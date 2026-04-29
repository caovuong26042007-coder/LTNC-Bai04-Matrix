/**
 * Tài khoản vãng lai.
 */
package com.practice.maven;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckingAccount extends Account {

    private static final Logger logger = LoggerFactory.getLogger(CheckingAccount.class);
    public CheckingAccount(long accountNumber, double balance) {
        super(accountNumber, balance);
    }

    @Override
    public void deposit(double amount) {
        double initialBalance = getBalance();
        try {
            doDepositing(amount);
            double finalBalance = getBalance();
            Transaction transaction = new Transaction(
                    Transaction.TYPE_DEPOSIT_CHECKING,
                    amount,
                    initialBalance,
                    finalBalance);
            addTransaction(transaction);
        } catch (BankException e) {
            logger.error("Loi giao dich {}",e.getMessage());
        }
    }

    @Override
    public void withdraw(double amount) {
        double initialBalance = getBalance();
        try {
            doWithdrawing(amount);
            double finalBalance = getBalance();
            Transaction t = new Transaction(
                    Transaction.TYPE_WITHDRAW_CHECKING,
                    amount,
                    initialBalance,
                    finalBalance);
            addTransaction(t);
        } catch (BankException e) {
            logger.error("Loi giao dich {}",e.getMessage());
        }
    }
}
