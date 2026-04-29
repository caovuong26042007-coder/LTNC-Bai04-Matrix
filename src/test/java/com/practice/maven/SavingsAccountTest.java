package com.practice.maven;

import org.junit.Assert;
import org.junit.Test;

/**
 * Lớp kiểm thử cho các chức năng của SavingsAccount.
 */
public class SavingsAccountTest {

  @Test
  public void testDepositSuccess() {
    // 1. Chuẩn bị dữ liệu (Arrange): Tạo tài khoản với số dư ban đầu là 1000
    SavingsAccount account = new SavingsAccount(123456789L, 1000.0);

    // 2. Thực thi hành động (Act): Nạp thêm 500
    account.deposit(500.0);

    // 3. Kiểm tra kết quả (Assert): Số dư mới phải là 1500
    // Tham số thứ 3 (0.001) là độ sai số cho phép khi so sánh 2 số thực (double)
    Assert.assertEquals(1500.0, account.getBalance(), 0.001);
  }

  @Test
  public void testInvalidDepositAmount() {
    // 1. Chuẩn bị: Tài khoản có 1000
    SavingsAccount account = new SavingsAccount(123456789L, 1000.0);

    // 2. Thực thi: Cố tình nạp số tiền âm (không hợp lệ)
    // Lưu ý: Hàm deposit của bạn bắt lỗi bên trong và ghi log, chứ không làm sập chương trình
    account.deposit(-500.0);

    // 3. Kiểm tra: Số dư phải được giữ nguyên là 1000 (không bị trừ đi)
    Assert.assertEquals(1000.0, account.getBalance(), 0.001);
  }

  @Test
  public void testWithdrawExceedsMaxLimit() {
    // 1. Chuẩn bị: Tài khoản có 10000 (rất nhiều tiền)
    SavingsAccount account = new SavingsAccount(123456789L, 10000.0);

    // 2. Thực thi: Cố tình rút 2000 (Vượt quá MAX_WITHDRAW = 1000 của tài khoản tiết kiệm)
    account.withdraw(2000.0);

    // 3. Kiểm tra: Giao dịch bị từ chối, số dư vẫn phải là 10000
    Assert.assertEquals(10000.0, account.getBalance(), 0.001);
  }
}