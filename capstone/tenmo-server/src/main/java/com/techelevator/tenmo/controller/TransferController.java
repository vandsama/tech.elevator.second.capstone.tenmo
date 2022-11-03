package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@PreAuthorize("isActivated()")
@RestController
@RequestMapping("/transfers")
public class TransferController {

    private TransferDao transferDao;

    public TransferController(TransferDao transferDao) {
        this.transferDao = transferDao;
    }

    // GET list of transfers
    @PreAuthorize("isActivated()")
    @RequestMapping(path = "accounts/{id}/transfers", method = RequestMethod.GET)
    public List<Transfer> viewTransfers(@PathVariable long id)
    {
        return transferDao.viewTransfers(id);
    }

    // GET list of transfers by id
    @PreAuthorize("isActivated()")
    @RequestMapping(path = "accounts/{id}/transfers/{id}", method = RequestMethod.GET)
    public List<Transfer> viewTransfersById(@PathVariable long accountId, @PathVariable long transferId)
    {
        return transferDao.viewTransfersById(accountId, transferId);
    }



}
