package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    boolean executeTransfer(String fromUsername, String toUsername, BigDecimal transferAmount);

    List<Transfer> viewAllTransfersByUserId(long userId);

    Transfer viewTransferByTransferId(long transferId);

}
