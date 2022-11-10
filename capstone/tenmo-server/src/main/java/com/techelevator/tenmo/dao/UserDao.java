package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

import java.util.List;

public interface UserDao {

    List<User> viewAllUsers();

    User viewUserInfoByUsername(String username);

    int viewUserIdByUsername(String username);

    boolean createUser(String username, String password);
}
