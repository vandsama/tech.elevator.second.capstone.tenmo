package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

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


    private User mapRowToUser(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        //transfer.setTransferId(rs.getLong("transfer_id"));
        return null;
    }

}
