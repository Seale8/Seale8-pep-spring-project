package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account registerAccount(Account account) throws Exception {
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            throw new Exception("Username cannot be blank");
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            throw new Exception("Password must be at least 4 characters long");
        }
        if (accountRepository.findAccountByUsername(account.getUsername()).isPresent()) {
            throw new Exception("Account with this username already exists");
        }
        return accountRepository.save(account);
    }

    public Account verifyAccount(Account account) throws Exception {
        Optional<Account> findAcc = accountRepository.findAccountByUsername(account.getUsername());
        String pass  = findAcc.get().getPassword();
        if (findAcc.isPresent() && pass.equals(account.getPassword())){
            return findAcc.get();
        }
        else{
            throw new Exception("Wrong Credentials");
        }
        
    }
}
