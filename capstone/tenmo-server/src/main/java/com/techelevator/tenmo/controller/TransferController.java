package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/transfers")
public class TransferController {

    // Instantiate Dao(s)
    private AccountDao accountDao;
    private TransferDao transferDao;

    // Place Constructor(s)
    public TransferController(TransferDao transferDao, AccountDao accountDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
    }

    // Execute transfer
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "", method = RequestMethod.POST)
    public boolean executeTransfer(@Valid @RequestBody Transfer transfer,
                                   @RequestParam(name="from") String fromUsername,
                                   @RequestParam(name = "to") String toUsername,
                                   @RequestParam(name = "amount") BigDecimal transferAmount) {
        BigDecimal fromAccountBalance = accountDao.viewBalanceByUserId(transfer.getFromAccountId());


        if (fromAccountBalance.compareTo(transfer.getTransferAmount()) == -1)
        {throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "The amount you are transferring cannot exceed your current balance.");}
        else if (transfer.getFromAccountId() == transfer.getToAccountId())
        {throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your account cannot send money to itself.");}
        boolean result = transferDao.executeTransfer(fromUsername, toUsername,
                transferAmount);
        return result;
    }
    //TODO: Execute Transfer edits: make use of Principal class verify "fromUser" is currently logged in, use either
    // 3 RequestParams (fromId, toId, amount) or use DTO object


    // View all transfers by userId
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Transfer> viewAllTransfersByUserId(@PathVariable long userId) {
        return transferDao.viewAllTransfersByUserId(userId);
    }

    // View single transfer by transferId
    @RequestMapping(path = "/{transferId}", method = RequestMethod.GET)
    public Transfer viewTransferByTransferId(@PathVariable long transferId) {
        return transferDao.viewTransferByTransferId(transferId);
    }
}
