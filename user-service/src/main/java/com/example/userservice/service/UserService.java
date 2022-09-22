package com.example.userservice.service;

import com.example.userservice.dto.Account;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final AccountFeign accountFeign;

    private final JwtUtils jwtUtils;

    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new InstanceNotFoundException("user not found"));
    }

    public String signing(String username, String password,AuthenticationManager authenticationManager) {
        User user = (User) loadUserByUsername(username);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return jwtUtils.generateJwtToken(user);
    }

    public String signup(User user) {
        User savedUser = userRepository.save(user);
        ResponseEntity<Account> userAccount = accountFeign.create();
        Account account = userAccount.getBody();
        savedUser.setAccountNumber(Objects.requireNonNull(account).getAccountNumber());
        userRepository.save(savedUser);
        return "signup done successfully";
    }

    public Account readAccount(User user){
        return accountFeign.read(user.getAccountNumber()).getBody();
    }

    public Integer withdraw(User user , int amount){
        return accountFeign.withdraw(amount,user.getAccountNumber()).getBody();
    }

    public Integer deposit(User user , int amount){
        return accountFeign.deposit(amount,user.getAccountNumber()).getBody();
    }

    public String deleteAccount(User user){
        return accountFeign.delete(user.getAccountNumber()).getBody();
    }
}
