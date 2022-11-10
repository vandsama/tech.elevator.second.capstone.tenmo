package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    // Instantiate Dao(s)
    private UserDao userDao;

    // Place Constructor(s)
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    // View all users
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public List<User> viewAllUsers() {
        return userDao.viewAllUsers();
    }

    // View user info by username
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/{username}", method = RequestMethod.GET)
    public User viewUserInfoByUsername(@PathVariable String username) {
        return userDao.viewUserInfoByUsername(username);
    }

    // View userId by username
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/userid/{username}", method = RequestMethod.GET)
    public int viewUserIdByUsername(@PathVariable String username) {
        return userDao.viewUserIdByUsername(username);
    }

    // Create user
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.POST)
    public boolean createUser(@PathVariable String username, @PathVariable String password) {
        return userDao.createUser(username, password);
    }
}
