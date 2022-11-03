package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
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
    public void reviewTransfer(Account fromAccount, Account toAccount, BigDecimal amount) {

    }

    @Override
    public void executeTransfer(Account fromAccount, Account toAccount, BigDecimal amount) {

    }



    @Override
    public List<Transfer> viewTransfersById(long accountId, long transferId) {
        List<Transfer> transfers = new ArrayList<>();
        return transfers;
    }

    @Override
    public List<Transfer> viewTransfers(long accountId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, from_account_id, to_account_id, transfer_amount" +
                "FROM transfer" +
                "WHERE from_account_id = ? OR to_account_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId, accountId);
        while(results.next())
        {
            //WE DONT HAVE A MapRowToTransfer METHOD. MAKE ONE.
            transfers.add(transfer);
        }

        return transfers;
    }


    private User mapRowToUser(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        //transfer.setTransferId(rs.getLong("transfer_id"));
        return null;
    }

}
