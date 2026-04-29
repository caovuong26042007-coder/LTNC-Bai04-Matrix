package com.practice.maven;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bank {

    // Vi phạm: Tên biến không rõ nghĩa, viết tắt sai chuẩn camelCase (solved) 
    private static final Logger logger = LoggerFactory.getLogger(Bank.class);
    private List<Customer> customerList;
    private static final String ID_REGEX = "\\d{9}";

    /**
     * Khởi tạo đối tượng Bank với danh sách khách hàng rỗng
     */
    public Bank() {
        this.customerList = new ArrayList<Customer>();
    }

    /**
     * Lấy danh sách khách hàng hiện tại của ngân hàng.
     * * @return Danh sách khách hàng (List)
     */
    public List<Customer> getCustomerList() {
        return customerList;
    }

    // Vi phạm: Thụt đầu dòng (Indentation) lung tung và Javadoc thiếu tag @param (solved)
    /**
     * Thiết lập danh sách khách hàng mới.
     * Set danh sach khach hang
     *
     * @param customerList danh sách khách hàng cần thiết lập
     */
    public void setCustomerList(List<Customer> customerList) {
        if (customerList == null) {
            this.customerList = new ArrayList<Customer>();
        } else {
            this.customerList = customerList;
        }
    }

    /**
     * Đọc dữ liệu danh sách khách hàng
     * Xử lý các trường hợp sai trước --> xử lý dữ liệu
     * @param inputStream
     * @throws NumberFormatException trong quá trình parse từ String -> Long, ném ra lỗi khi sai định dạng
     * @throws IOException ném ra lỗi khi đọc xuất file
     */
    public void readCustomerList(InputStream inputStream) {
        // Vi phạm: Log trực tiếp bằng System.out
        logger.debug("DEBUG: Bat dau doc du lieu...");
        if (inputStream == null) {
            return;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        Customer current = null;
        try {
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                int last = line.lastIndexOf(' ');
                if (last <= 0) {
                    continue;
                }

                String token = line.substring(last + 1).trim();

                if (token.matches(ID_REGEX)) {
                    String name = line.substring(0, last).trim();
                    current = new Customer(Long.parseLong(token), name);
                    customerList.add(current);
                    logger.info("Them khach hang {}", name);
                } else {
                    if (current == null) {
                        logger.warn("Ta khoan khong co khach hang {}", line);
                        continue;
                    }

                    String[] parts = line.split("\\s+");
                    if (parts.length < 3) {
                        logger.warn("Du lieu khong du 3 thanh phan: {}", line);
                        continue;
                    }
                    long num = Long.parseLong(parts[0]);
                    String accountType = parts[1];
                    double bal = Double.parseDouble(parts[2]);

                    if (Account.CHECKING_TYPE.equals(accountType)) {
                        current.addAccount(new CheckingAccount(num, bal));
                    } else if (Account.SAVING_TYPE.equals(accountType)) {
                        current.addAccount(new SavingsAccount(num, bal));
                    }
                }
            }
            } catch (NumberFormatException e) {
                // Bắt lỗi ép kiểu số
                logger.error("Dữ liệu file bị sai định dạng số: {}", e.getMessage());
            } catch (IOException e) {
                // Bắt lỗi đọc file
                logger.error("Lỗi luồng I/O khi đọc file: {}", e.getMessage());
            }
        }

    

    private String buildCustomerListString(List<Customer> listToBuild) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < listToBuild.size(); i++) {
            sb.append(listToBuild.get(i).getCustomerInfo());
            if (i < listToBuild.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * 
     * @return danh sách khách hàng sau khi sắp xếp bằng ID
     */
    public String getCustomersInfoByIdOrder() {
        // Vi phạm: Dùng Anonymous class thay vì Lambda, thụt lề sai (solved)
        Collections.sort(customerList, (o1, o2) -> Long.compare(o1.getIdNumber(), o2.getIdNumber()));

        // Vi phạm: Cộng chuỗi (String concatenation) trong vòng lặp - Cực tệ cho performance
        return buildCustomerListString(customerList);
    }

    /**
     *  
     * @return danh sách khách hàng sau khi sắp xếp bằng tên
     */
    public String getCustomersInfoByNameOrder() {
        // Vi phạm: Logic trùng lặp nhiều với hàm trên (Code Duplication) (solved)
        List<Customer> copy = new ArrayList<>(customerList);

        copy.sort((c1, c2) -> {
            // So sánh theo tên 
            int nameCompare = c1.getFullName().compareTo(c2.getFullName());

            // Nếu tên khác nhau, trả về kết quả so sánh tên
            if (nameCompare != 0) {
                return nameCompare;
            }

            // Nếu tên giống nhau, tiếp tục so sánh theo ID
            return Long.compare(c1.getIdNumber(), c2.getIdNumber());
        });

        // Vi phạm: Dòng code quá dài, không ngắt dòng (solved)
        return buildCustomerListString(copy);
    }
}
