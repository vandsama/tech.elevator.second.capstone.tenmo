package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {

    Account viewAccountInfoByUserId(long userId);

    long getAccountIdByUsername(String username);

    BigDecimal viewBalanceByUserId(long userId);

}
