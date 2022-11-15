package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
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
    private UserDao userDao;
    // Place Constructor(s)
    public TransferController(TransferDao transferDao, AccountDao accountDao, UserDao userDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    // Execute transfer
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "", method = RequestMethod.POST)
    public boolean executeTransfer(@RequestParam(name="from") String fromUsername,
                                   @RequestParam(name = "to") String toUsername,
                                   @RequestParam(name = "amount") BigDecimal transferAmount) {
        Transfer transfer = new Transfer();
        transfer.setFromAccountId(accountDao.getAccountIdByUsername(fromUsername));
        transfer.setToAccountId(accountDao.getAccountIdByUsername(toUsername));
        transfer.setTransferAmount(transferAmount);
       // BigDecimal fromAccountBalance = accountDao.viewBalanceByUserId(transfer.getFromAccountId());

        Account fromAccount = new Account();
        Account toAccount = new Account();
        fromAccount.setBalance(accountDao.viewBalanceByUserId(userDao.viewUserIdByUsername(fromUsername)));
        toAccount.setBalance(accountDao.viewBalanceByUserId(userDao.viewUserIdByUsername(toUsername)));

        if (fromAccount.getBalance().compareTo(transferAmount) == -1)
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
    public List<Transfer> viewAllTransfersByUsername(@RequestParam(name="username") String username) {
        return transferDao.viewAllTransfersByUsername(username);
    }

    // View single transfer by transferId
    @RequestMapping(path = "/{transferId}", method = RequestMethod.GET)
    public Transfer viewTransferByTransferId(@PathVariable long transferId) {
        return transferDao.viewTransferByTransferId(transferId);
    }
}
