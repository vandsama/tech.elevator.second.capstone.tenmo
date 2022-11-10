package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    @Override
    public boolean executeTransfer(String fromUsername, String toUsername, BigDecimal transferAmount) {
        Account fromAccount = new Account();
        if(fromUsername.equalsIgnoreCase(toUsername) || transferAmount.compareTo(BigDecimal.ZERO) <= 0 || transferAmount.compareTo(fromAccount.getBalance(fromUsername)) == -1)
        {return false;}
        else {
            String sql1 = "INSERT INTO transfer (from_account_id, to_account_id, transfer_amount)" +
                    //Insert into the Transfer Table a row writing down the details
                    "VALUES ((SELECT account_id FROM account JOIN tenmo_user ON tenmo_user.user_id = account.user_id " +
                    "WHERE username = ?), (SELECT account_id FROM account JOIN tenmo_user ON tenmo_user.user_id = " +
                    "account.user_id WHERE username = ?), ?)" +
                    "RETURNING transfer_id;";
            Integer newTransactionId;
            try {
                newTransactionId = jdbcTemplate.queryForObject(sql1, Integer.class, fromUsername, toUsername, transferAmount);

                String sql2 =  //Update the FromAccount to decrease balance by TransferAmount
                        "UPDATE account" +
                                "SET account.balance = account.balance - ?" +
                                "WHERE from_account_id = (SELECT account_id FROM account JOIN tenmo_user ON " +
                                "tenmo_user.user_id = account.user_id WHERE username = ?);";
                jdbcTemplate.update(sql2, transferAmount, fromUsername);

                String sql3 = "UPDATE account" +  //Update the ToAccount to increase balance by TransferAmount
                        "SET account.balance = account.balance + ?" +
                        "WHERE to_account_id = (SELECT account_id FROM account JOIN tenmo_user ON tenmo_user.user_id " +
                        "= account.user_id WHERE username = ?);";
                jdbcTemplate.update(sql3, transferAmount, toUsername);
                return true;
            } catch (DataAccessException e) {
                return false;
            }
        }
    }

    @Override
    public List<Transfer> viewAllTransfersByUserId(long userId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, from_account_id, to_account_id, transfer_amount" +
                "FROM transfer" +
                "WHERE from_account_id = (SELECT account_id FROM account JOIN tenmo_user ON tenmo_user.user_id = account.user_id WHERE user_id = ?) OR to_account_id = (SELECT account_id FROM account JOIN tenmo_user ON tenmo_user.user_id = account.user_id WHERE user_id = ?);";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);
        while(results.next())
        {
            Transfer t = mapRowToTransfer(results);
            transfers.add(t);
        }

        return transfers;
    }

    @Override
    public Transfer viewTransferByTransferId(long transferId) {
        Transfer transfer = null;
        String sql = "SELECT transfer_id, from_account_id, to_account_id, transfer_amount" +
                "FROM transfer" +
                "WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if(results.next())
        {
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }

    // This is the mapper to create a transfer object and set its attributes to the name of the column in the database.
    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getLong("transfer_id"));
        transfer.setFromAccountId(rs.getLong("from_account_id"));
        transfer.setToAccountId(rs.getLong("to_account_id"));
        transfer.setTransferAmount(rs.getBigDecimal("transfer_amount"));
        return transfer;
    }
}