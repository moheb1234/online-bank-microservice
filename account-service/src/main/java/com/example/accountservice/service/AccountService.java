package com.example.accountservice.service;

import com.example.accountservice.model.Account;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.tools.GenerateAccountNumber;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    @SneakyThrows
    public Account findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).orElseThrow(() ->
                new InstanceNotFoundException("no account founded"));
    }

    public Account create() {
        String accountNumber = GenerateAccountNumber.generateAccountNumber();
        Account account = new Account(accountNumber);
        return accountRepository.insert(account);
    }

    public Integer withdraw(int amount, String accountNumber) {
        if (amount <= 0)
            throw new IllegalArgumentException("amount: " + amount + " is invalid");
        Account account = findByAccountNumber(accountNumber);
        if (account.withDraw(amount)){
            accountRepository.save(account);
            return account.getBalance();
        }
        throw new IllegalArgumentException("balance is not enough");
    }

    public Integer deposit(int amount,String accountNumber){
        if (amount <= 0)
            throw new IllegalArgumentException("amount: " + amount + " is invalid");
        Account account = findByAccountNumber(accountNumber);
        account.setBalance(account.getBalance()+amount);
        accountRepository.save(account);
        return account.getBalance();
    }

    public String delete(String accountNumber){
        Account account = findByAccountNumber(accountNumber);
        accountRepository.delete(account);
        return "account deleted successfully";
    }
}
