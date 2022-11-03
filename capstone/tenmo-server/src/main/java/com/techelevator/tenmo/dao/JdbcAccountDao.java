package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    @Override
//    public boolean createNewAccount(String username)
//    {
//
//    }

    public BigDecimal viewBalanceById(long id)
    {
        String sql = "SELECT balance FROM account" +
                "JOIN tenmo_user ON tenmo_user.user_id = account.user_id" +
                "WHERE tenmo_user.user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        return this.mapRowToAccount(results).getBalance();
    }

    @Override
    public void sendTransfer(Account fromAccount) {

    }

    @Override
    public List<User> selectFromUserList() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT";

        return users;
    }

    @Override
    public BigDecimal selectAmount(Account toAccount) {
        return null;
    }

    @Override
    public void reviewTransfer(Account fromAccount, Account toAccount, BigDecimal amount) {

    }

    @Override
    public void executeTransfer(Account fromAccount, Account toAccount, BigDecimal amount) {

    }


    public Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountId(rs.getLong("account_id"));
        account.setUserId(rs.getLong("user_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    }

    }
