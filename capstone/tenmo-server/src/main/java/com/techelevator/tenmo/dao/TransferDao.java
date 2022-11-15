package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    boolean executeTransfer(String fromUsername, String toUsername, BigDecimal transferAmount);

    List<Transfer> viewAllTransfersByUsername(String username);

    Transfer viewTransferByTransferId(long transferId);

}
