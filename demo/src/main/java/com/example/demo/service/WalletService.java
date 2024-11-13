package com.example.demo.service;

import com.example.demo.domain.Wallet;
import com.example.demo.exception.WalletException;
import com.example.demo.service.resource.TransactionRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface WalletService {


    public void createWallet(Long userId);

    public void deleteWallet(Long userId);

    public Optional<Wallet> getWallet(Long userId);

    public Boolean performTransaction(Wallet wallet1, Wallet wallet2, TransactionRequest transactionRequest) throws WalletException;

}
