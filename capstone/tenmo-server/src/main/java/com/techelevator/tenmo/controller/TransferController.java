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

@PreAuthorize("isActivated()")
@RestController
@RequestMapping("http://localhost:8080/")
public class TransferController {

    private AccountDao accountDao;
    private TransferDao transferDao;

    public TransferController(TransferDao transferDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
    }

    // GET list of transfers by User ID
    @PreAuthorize("isActivated()")
    @RequestMapping(path = "users/{id}/transfers", method = RequestMethod.GET)
    public List<Transfer> viewAllTransfersByUserId(@PathVariable long id)
    {
        return transferDao.viewAllTransfersByUserId(id);
    }

    // GET list of transfers by Transfer ID
    @PreAuthorize("isActivated()")
    @RequestMapping(path = "transfers/{transferId}", method = RequestMethod.GET)
    public List<Transfer> viewTransferByTransferId(@PathVariable long transferId)
    {
        return transferDao.viewTransferByTransferId(transferId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isActivated()")
    @RequestMapping(path = "transfers/execute}", method = RequestMethod.POST)
    public boolean executeTransfer(@Valid @RequestBody Transfer transfer)
    {
        BigDecimal fromAccountBalance = accountDao.viewBalanceByUserId(transfer.getFromAccountId());
        if (fromAccountBalance.compareTo(transfer.getTransferAmount()) == -1)
        {throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "The amount you are transferring cannot exceed your current balance.");}
        else if (transfer.getFromAccountId() == transfer.getToAccountId())
        {throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your account cannot send money to itself.");}
        boolean result = transferDao.executeTransfer(transfer.getFromAccountId(), transfer.getToAccountId(), transfer.getTransferAmount());
        return result;
    }

}
