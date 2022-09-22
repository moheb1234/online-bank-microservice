package com.example.accountservice.controller;

import com.example.accountservice.model.Account;
import com.example.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("account/read/{accountNumber}")
    public ResponseEntity<Account> read(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.findByAccountNumber(accountNumber));
    }

    @PostMapping("account/create")
    public ResponseEntity<Account> create() {
        return new ResponseEntity<>(accountService.create(), HttpStatus.CREATED);
    }

    @PutMapping("account/withdraw/{accountNumber}")
    public ResponseEntity<Integer> withdraw(@RequestParam Integer amount, @PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.withdraw(amount,accountNumber));
    }

    @PutMapping("account/deposit/{accountNumber}")
    public ResponseEntity<Integer> deposit(@RequestParam Integer amount, @PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.deposit(amount,accountNumber));
    }

    @DeleteMapping("account/delete/{accountNumber}")
    public ResponseEntity<String> delete(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.delete(accountNumber));
    }
}
