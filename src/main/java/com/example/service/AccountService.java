package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Persists Account to the account database table.
     * 
     * @param account The Account to persist.
     * @return The persisted Account.
     */
    public Account registerAccount(Account account) {
        return accountRepository.save(account);
    }

    /**
     * This method checks if an account with the given username exists in the
     * database.
     * 
     * @param username The username to check.
     * @return true if the account exists.
     */
    public Boolean accountExists(String username) {
        Account possibleAccount = accountRepository.findAccountByUsername(username);
        if (possibleAccount != null) {
            return true;
        }
        return false;
    }

    /**
     * This method checks if an account with the given ID exists in the database.
     * 
     * @param id The ID to check.
     * @return true if account exists.
     */
    public Boolean accountExists(int id) {
        Account possibleAccount = accountRepository.findAccountById(id);
        if (possibleAccount != null) {
            return true;
        }
        return false;
    }

    /**
     * This method checks if the given account exists in the database and verifies
     * the username and password.
     * 
     * @param account The account to check.
     * @return The account if authentication is successful.
     */
    public Account login(Account account) {
        Account possibleAccount = accountRepository.findAccountByUsername(account.getUsername());
        if (possibleAccount != null) {
            if (possibleAccount.getPassword().equals(account.getPassword())) {
                return possibleAccount;
            }
        }
        return null;
    }

}
