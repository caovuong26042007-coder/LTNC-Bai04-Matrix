/**
 * Ngoại lệ chung trong hệ thống ngân hàng.
 */
package com.practice.maven;
public class BankException extends Exception {
    public BankException(String message) {
        super(message);
    }
}
