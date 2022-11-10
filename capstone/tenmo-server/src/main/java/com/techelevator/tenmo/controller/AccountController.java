package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    // Instantiate Dao(s)
    private AccountDao accountDao;

    // Place Constructor(s)
    public AccountController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    // View account info by userId
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/{userId}", method = RequestMethod.GET)
    public Account viewAccountInfoByUserId(@PathVariable long userId) {
        return accountDao.viewAccountInfoByUserId(userId);
    }

    // View balance by userId
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/{userId}/balance", method = RequestMethod.GET)
    public BigDecimal viewBalanceByUserId(@PathVariable long userId) {
        return accountDao.viewBalanceByUserId(userId);
    }
}
