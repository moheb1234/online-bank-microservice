package com.example.userservice.service;

import com.example.userservice.dto.Account;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient("account-service")
@LoadBalancerClient(name = "account-service")
public interface AccountFeign {

    @GetMapping("account/read/{accountNumber}")
    ResponseEntity<Account> read(@PathVariable String accountNumber);

    @PostMapping("account/create")
    ResponseEntity<Account> create();

    @PutMapping("account/withdraw/{accountNumber}")
    ResponseEntity<Integer> withdraw(@RequestParam Integer amount, @PathVariable String accountNumber);

    @PutMapping("account/deposit/{accountNumber}")
    ResponseEntity<Integer> deposit(@RequestParam Integer amount, @PathVariable String accountNumber);

    @DeleteMapping("account/delete/{accountNumber}")
    ResponseEntity<String> delete(@PathVariable String accountNumber);
}
