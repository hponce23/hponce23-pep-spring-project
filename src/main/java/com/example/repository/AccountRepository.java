package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query(value = "SELECT * FROM account WHERE username=?", nativeQuery = true)
    Account findAccountByUsername(String username);

    @Query(value = "SELECT * FROM account WHERE account_id=?", nativeQuery = true)
    Account findAccountById(int account_id);
}
