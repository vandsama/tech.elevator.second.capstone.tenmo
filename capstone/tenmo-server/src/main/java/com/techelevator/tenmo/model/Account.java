package com.techelevator.tenmo.model;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {

    // accountId (long) - pull from RegisterUserDTO class
    // we will make a method for a list of account_id's
    // balance - pull from the RegisterUserDTO class ($1K starting balance) then store the current balance in here
    // "id" (user_id) (long) - pull from User class

    private Long accountId;
    private Long userId;
    private BigDecimal balance = new BigDecimal(0.00);

    public Account()
    {
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
    }
    public Account(Long accountId, Long userId, BigDecimal balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }
    public BigDecimal getBalance(long accountId) {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void deposit(BigDecimal amount)
    {
        this.setBalance(balance.add(amount));
    }

    public void withdraw(BigDecimal amount)
    {
        this.setBalance(balance.subtract(amount));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountId == account.accountId &&
                userId == account.userId &&
                Objects.equals(accountId, account.accountId) &&
                Objects.equals(userId, account.userId) &&
                Objects.equals(balance, account.balance);
    }



}
