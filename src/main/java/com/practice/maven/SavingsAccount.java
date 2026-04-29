package com.practice.maven;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tai khoan tiet kiem - Lop nay thuc thi cac quy dinh ve rut tien và nap tien.
 */
public class SavingsAccount extends Account { // Vi phạm: Dấu ngoặc nhọn mở đầu dòng mới (Google Style yêu cầu cùng dòng) (solved)

    private static final double MAX_WITHDRAW = 1000.0;
    private static final double MIN_BALANCE = 5000.0;
    private static final Logger logger = LoggerFactory.getLogger(SavingsAccount.class);


    public SavingsAccount(long n, double b) {
        super(n, b);
    }

    @Override
    public void deposit(double amount) {
        // Vi phạm: Log không có cấu trúc, sử dụng System.err lộn xộn
        logger.debug("Giao dich dang xu ly...");
        double initialBalance = getBalance();

        try {
            doDepositing(amount);
            double finalBalance = getBalance();
            // Vi phạm: Magic Number '3' (Nên dùng Transaction.TYPE_DEPOSIT_SAVINGS)
            // Vi phạm: Dòng code quá dài
            Transaction transaction = new Transaction(Transaction.TYPE_DEPOSIT_SAVINGS, amount, initialBalance, finalBalance);
            addTransaction(transaction);

            logger.info("Nap tien vao tai khoan {} thanh cong: {}", getAccountNumber(), amount);
        } catch (InvalidFundingAmountException e) { // Vi phạm: Catch Exception chung chung
            logger.error("Loi nap tien: {}", e.getMessage());
        }
    }

    @Override
    public void withdraw(double amount) {
        double initialBalance = getBalance();
        try {

            // Vi phạm: Magic Number '1000.0' thay vì hằng số MAX_WITHDRAW
            if (amount > MAX_WITHDRAW) {
                throw new InvalidFundingAmountException(amount);
            }
            // Vi phạm: Magic Number '5000.0' thay vì hằng số MIN_BALANCE
            if (initialBalance - amount < MIN_BALANCE) {
                throw new InsufficientFundsException(amount);
            }

            doWithdrawing(amount);
            double finalBalance = getBalance();

            // Vi phạm: Magic Number '4' (Nên dùng Transaction.TYPE_WITHDRAW_SAVINGS)
            Transaction transacion = new Transaction(Transaction.TYPE_WITHDRAW_SAVINGS, amount, initialBalance, finalBalance);
            addTransaction(transacion);

            // Vi phạm: Log viết theo phong cách tùy tiện
            logger.info("[SAVINGS] Rut {} thanh cong. So du con: {}", amount, finalBalance);
        } catch (InvalidFundingAmountException | InsufficientFundsException e) {
            // Vi phạm: Thiếu dấu ngoặc nhọn cho khối catch đơn dòng (tùy chuẩn)
            // Vi phạm: Log lỗi nhưng không ghi rõ lỗi gì hoặc stack trace
            logger.error("Rut tien bi loi! {}", e.getMessage());
        }
    }
}
