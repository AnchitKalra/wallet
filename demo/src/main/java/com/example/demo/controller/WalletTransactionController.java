package com.example.demo.controller;


import com.example.demo.domain.Wallet;
import com.example.demo.service.WalletService;
import com.example.demo.service.resource.TransactionRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transaction;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class WalletTransactionController {


    @Autowired
    WalletService walletService;



    @PostMapping("wallet/transaction")

    public ResponseEntity<Boolean> performTransaction(@RequestBody @Valid TransactionRequest transactionRequest) {
        try {
            Optional<Wallet> wallet1 = walletService.getWallet(transactionRequest.getSenderId());
            Optional<Wallet> wallet2 = walletService.getWallet(transactionRequest.getReceiverId());
            if (wallet1.isPresent() && wallet2.isPresent()) {
                Boolean success = walletService.performTransaction(wallet1.get(), wallet2.get(), transactionRequest);
                return new ResponseEntity<>(success, HttpStatus.OK);
            }
        }catch (Exception e) {
            System.out.println(e);
        }
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }


    }

