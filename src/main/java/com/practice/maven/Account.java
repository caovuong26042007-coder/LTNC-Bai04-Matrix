package com.practice.maven;

import java.util.List;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp này đại diện cho tài khoản nhưng viết Javadoc rất sơ sài và sai format
 */
public abstract class Account {

    // Vi phạm: Đặt tên hằng số không đúng chuẩn (phải là UPPER_SNAKE_CASE) (solved)
    public static final String CHECKING_TYPE = "CHECKING";
    public static final String SAVING_TYPE = "SAVINGS";
    private static final Logger logger = LoggerFactory.getLogger(Account.class);

    // Vi phạm: Tên biến instance bắt đầu bằng dấu gạch dưới hoặc quá ngắn, không rõ nghĩa (solved)
    private long accountNumber;
    private double balance;
    protected List<Transaction> list;

    // Vi phạm: Thụt lề (Indentation) không đồng nhất, không dùng 2 spaces theo chuẩn Google (?)
    public Account(long accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.list = new ArrayList<Transaction>();
    }

    // Vi phạm: Viết hàm trên một dòng, thiếu khoảng trắng giữa các toán tử/ngoặc (solved)
    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    protected void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactionList() {
        return list;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        // Vi phạm: Thiếu dấu ngoặc nhọn cho câu lệnh if (mặc dù vẫn chạy đúng) (solved)
        if (transactionList == null) {
            this.list = new ArrayList<Transaction>();
        } else {
            this.list = transactionList;
        }

    }

    // Vi phạm: Thiếu Javadoc cho phương thức public
    /**
     *
     * @param amount: số tiền cần rút hoặc nạp
     * @throws InvalidFundingAmountException khi giá trị cần nạp/rút không hợp
     * lệ.
     * @throws InsufficientFundsException khi mà số tiền cần rút lại lớn hơn số
     * dư.
     */
    public abstract void deposit(double amount);

    public abstract void withdraw(double amount);

    protected void doDepositing(double amount) throws InvalidFundingAmountException {
        // Vi phạm: Whitespace quanh toán tử (amount<=0)
        if (amount <= 0) {
            throw new InvalidFundingAmountException(amount);
        }
        balance += amount;
    }

    protected void doWithdrawing(double amount) throws InvalidFundingAmountException, InsufficientFundsException {
        // Vi phạm: Tung ra Exception quá chung chung thay vì Exception cụ thể (solved)
        if (amount <= 0) {
            throw new InvalidFundingAmountException(amount);
        }
        if (amount > balance) {
            throw new InsufficientFundsException(amount);
        }
        balance -= amount;
    }

    public void addTransaction(Transaction transaction) {
        if (transaction != null) {
            list.add(transaction);
        }
    }

    public String getTransactionHistory() {
        // Vi phạm: Dòng code quá dài (Line length) và dùng cộng chuỗi trong vòng lặp (Performance smell)
        StringBuilder sb = new StringBuilder();
        sb.append("Lịch sử giao dịch của tài khoản ").append(accountNumber).append(":\n");

        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).getTransactionSummary()); // Vi phạm: Không dùng StringBuilder (fixed)
            if (i < list.size() - 1) {
                sb.append("\n");
            }

        }
        

        // Vi phạm: In log trực tiếp ra console để debug (Thay vì dùng Logger) (solved)
        logger.debug("Đã lấy lịch sử cho tài khoản: {}", accountNumber);
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Account)) {
            return false;
        }
        Account other = (Account) obj;
        return this.accountNumber == other.accountNumber;
    }

    @Override
    public int hashCode() {
        // Vi phạm: Format code lộn xộn
        return (int) (accountNumber ^ (accountNumber >>> 32));
    }
    
}
