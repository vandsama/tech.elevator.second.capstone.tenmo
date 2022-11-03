package com.techelevator.tenmo.model;

import javax.validation.constraints.NotEmpty;

public class RegisterUserDTO {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    // create account_id (long) upon registration -- use insert statement in PGAdmin
    // set account balance to $1K (BigDecimal in IntelliJ)
    // set user_id in account to be *equal* to user_id in tenmo_user (using subquery)

}
