package com.example.accountservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document("account")
public class Account {

    @Id
    private String id;

    @Indexed(unique = true)
    private String accountNumber;

    private int balance;

    @LastModifiedDate
    private Date lastModifiedAt;

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
        balance = 0;
    }

    public boolean withDraw(int amount) {
        if (amount > balance)
            return false;
        balance -= amount;
        return true;
    }

}
