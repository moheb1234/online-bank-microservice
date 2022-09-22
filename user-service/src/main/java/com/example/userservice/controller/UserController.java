package com.example.userservice.controller;

import com.example.userservice.dto.Account;
import com.example.userservice.model.User;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("user/signup")
    public ResponseEntity<String> signup(@RequestBody User user){
        return new ResponseEntity<>(userService.signup(user), HttpStatus.CREATED);
    }

    @PostMapping("user/signing")
    public ResponseEntity<String> signing(@RequestParam String username , @RequestParam String password){
        return ResponseEntity.ok(userService.signing(username, password,authenticationManager));
    }

    @GetMapping("user/read/account")
    public ResponseEntity<Account> readAccount(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(userService.readAccount(user));
    }

    @PutMapping("user/account/withdraw")
    public ResponseEntity<Integer> withdrawAccount(@AuthenticationPrincipal User user,@RequestParam int amount){
        return ResponseEntity.ok(userService.withdraw(user, amount));
    }

    @PutMapping("user/account/deposit")
    public ResponseEntity<Integer> depositAccount(@AuthenticationPrincipal User user,@RequestParam int amount){
        return ResponseEntity.ok(userService.deposit(user, amount));
    }

    @DeleteMapping("user/account/delete")
    public ResponseEntity<String> deleteAccount(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(userService.deleteAccount(user));
    }
}
