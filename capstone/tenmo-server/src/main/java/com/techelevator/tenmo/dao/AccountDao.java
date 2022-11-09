package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface AccountDao {

    // create Account


    public BigDecimal viewBalanceByUserId(long id);


    BigDecimal viewAccountInfoByUserId(long id);
}
