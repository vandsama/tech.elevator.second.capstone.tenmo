package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

    // create Account


    public BigDecimal viewBalanceById(long id);

    public void sendTransfer(Account fromAccount);
    //the main method in which all other below methods shall be stored and called.

    public List<User> selectFromUserList();
    //Retrieves the list of users by their user_id, and allows the user to select the user to send money to.
    //The user should NOT be able to view or select its own username or user_id.

    public BigDecimal selectAmount(Account toAccount);
    //Allows user to input an amount to send to another user.
    //User should NOT be able to send money to itself
    //User should NOT be able to send a zero or negative amount.
    //User should NOT be able to send more money than is in their account.

    public void reviewTransfer(Account fromAccount, Account toAccount, BigDecimal amount);
    //takes the info from sendTransfer(), selectFromUserList(), and selectAmount() and displays all the information
    //prompts the user with a message saying "Please verify your transaction details." and the ability to say Submit or Cancel.

    public void executeTransfer(Account fromAccount, Account toAccount, BigDecimal amount);
    //Completes the transfer by withdrawing the amount from the fromAccount, depositing the same amount into the toAccount.
    //
    //When it successfully sends the money, it logs the transaction in the transfer database table, logging the fromAccount, toAccount, and amount sent.

}
