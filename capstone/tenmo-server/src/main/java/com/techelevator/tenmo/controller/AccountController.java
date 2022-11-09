package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

//@PreAuthorize("isActivated()")
@RestController
@RequestMapping("/accounts/")
public class AccountController {

    private AccountDao accountDao;

    public AccountController(AccountDao accountDao)
    {
        this.accountDao = accountDao;

    }

    // GET balance by AccountId
    @PreAuthorize("isActivated()")
    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public BigDecimal viewAccountInfoByAccountId(@PathVariable long id)
    {
        return accountDao.viewAccountInfoByUserId(id);
    }




    // GET balance by AccountId
//    @PreAuthorize("isActivated()")
    @RequestMapping(path = "{id}/balance", method = RequestMethod.GET)
    public BigDecimal viewBalanceByUserId(@PathVariable long id)
    {
        return accountDao.viewBalanceByUserId(id);
    }



}
