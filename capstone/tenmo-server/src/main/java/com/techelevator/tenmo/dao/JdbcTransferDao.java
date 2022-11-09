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

    // findAll()
    // findByTransferId()
    // create()

//6. As an authenticated user of the system, I need to be able to see transfers I have sent or received.
//7. As an authenticated user of the system, I need to be able to retrieve the details of any transfer based upon the transfer ID.

//    @Override
//    private int

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
    public boolean reviewTransfer(long fromAccountId, long toAccountId, BigDecimal amount) {
        return false;
    }

    @Override
    public boolean executeTransfer(long fromUserId, long toUserId, BigDecimal transferAmount) {
        Account fromAccount = new Account();
        if(fromUserId == toUserId || transferAmount.compareTo(BigDecimal.ZERO) <= 0 || transferAmount.compareTo(fromAccount.getBalance(fromUserId)) == -1)
        {return false;}
        else {
            String sql1 = "INSERT INTO transfer (from_account_id, to_account_id, transfer_amount)" +
                    //Insert into the Transfer Table a row writing down the details
                    "VALUES ((SELECT account_id FROM account JOIN tenmo_user ON tenmo_user.user_id = account.user_id WHERE user_id = ?), (SELECT account_id FROM account JOIN tenmo_user ON tenmo_user.user_id = account.user_id WHERE user_id = ?), ?)" +
                    "RETURNING transfer_id;";
            Integer newTransactionId;
            try {
                newTransactionId = jdbcTemplate.queryForObject(sql1, Integer.class, fromUserId, toUserId, transferAmount);

                String sql2 =  //Update the FromAccount to decrease balance by TransferAmount
                        "UPDATE account" +
                                "SET account.balance = account.balance - ?" +
                                "WHERE from_account_id = (SELECT account_id FROM account JOIN tenmo_user ON tenmo_user.user_id = account.user_id WHERE user_id = ?);";
                jdbcTemplate.update(sql2, transferAmount, fromUserId);

                String sql3 = "UPDATE account" +  //Update the ToAccount to increase balance by TransferAmount
                        "SET account.balance = account.balance + ?" +
                        "WHERE to_account_id = (SELECT account_id FROM account JOIN tenmo_user ON tenmo_user.user_id = account.user_id WHERE user_id = ?);";
                jdbcTemplate.update(sql3, transferAmount, toUserId);

                return true;
            } catch (DataAccessException e) {
                return false;
            }


        }
    }



    @Override
    public List<Transfer> viewTransferByTransferId(long transferId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, from_account_id, to_account_id, transfer_amount" +
                "FROM transfer" +
                "WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        while(results.next())
        {
            Transfer t = mapRowToTransfer(results);
            transfers.add(t);
        }
        return transfers;
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


    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getLong("transfer_id"));
        transfer.setFromAccountId(rs.getLong("from_account_id"));
        transfer.setToAccountId(rs.getLong("to_account_id"));
        transfer.setTransferAmount(rs.getBigDecimal("transfer_amount"));
        return transfer;
    }

}

//    BEGIN TRANSACTION
//    UPDATE account
//    SET account.balance = (account.balance - ?)
//        WHERE from_account_id = ?;
//        UPDATE account
//        SET account.balance = (account.balance + ?)
//        WHERE to_account_id = ?;
//        INSERT INTO transfer (from_account_id, to_account_id, transfer_amount)
//        VALUES (?, ?, ?)
//        RETURNING transfer_id;
// COMMIT;