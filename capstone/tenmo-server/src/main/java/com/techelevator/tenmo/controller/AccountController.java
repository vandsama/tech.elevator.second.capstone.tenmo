package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@PreAuthorize("isActivated()")
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private AccountDao accountDao;

    public AccountController(AccountDao accountDao)
    {
        this.accountDao = accountDao;

    }

    // GET balance by id
    @PreAuthorize("isActivated()")
    @RequestMapping(path = "/accounts/{id}/balance", method = RequestMethod.GET)
    public BigDecimal viewBalanceById(@PathVariable long id)
    {
        return accountDao.viewBalanceById(id);
    }



}
