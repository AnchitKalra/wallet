package com.example.demo.service.impl;


import com.example.demo.domain.TransactionType;
import com.example.demo.domain.Wallet;
import com.example.demo.exception.WalletException;
import com.example.demo.repository.WalletRepository;
import com.example.demo.service.WalletService;
import com.example.demo.service.resource.TransactionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);
    @Autowired
    WalletRepository walletRepository;



    @Override
    public void createWallet(Long userId) {
        try {
            Optional<Wallet> walletOptional = walletRepository.findWalletByUserId(userId);
            if(walletOptional.isPresent()) {
                throw new WalletException("Wallet Already exists");
            }
            Wallet wallet = new Wallet();
            wallet.setUserId(userId);
            wallet.setBalance(0.0);
            walletRepository.save(wallet);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void deleteWallet(Long userId) {

    }

    @Override

    public Optional<Wallet> getWallet(Long userId) {
        return walletRepository.findWalletByUserId(userId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public Boolean performTransaction(Wallet wallet1, Wallet wallet2, TransactionRequest transactionRequest) throws WalletException{
       if(transactionRequest.getType().toString().equalsIgnoreCase(TransactionType.Deposit.name().toString())) {
           wallet2.setBalance(wallet1.getBalance() + transactionRequest.getAmount());
           walletRepository.save(wallet1);

           return true;
       }
       else if(transactionRequest.getType().toString().equalsIgnoreCase(TransactionType.Withdrawal.name().toString())) {
           Double balance = wallet1.getBalance();
           if(balance< transactionRequest.getAmount()) {
               throw new WalletException("Amount Inadequate");
           }
           wallet1.setBalance(wallet1.getBalance() - transactionRequest.getAmount());
           walletRepository.save(wallet1);
           return true;
       }
       else {
           Double balance = wallet1.getBalance();
           if(balance< transactionRequest.getAmount()) {
               throw new WalletException("Amount Inadequate");
           }
           wallet1.setBalance(wallet1.getBalance() - transactionRequest.getAmount());

           wallet2.setBalance(transactionRequest.getAmount() + wallet2.getBalance());
           walletRepository.save(wallet1);
           walletRepository.save(wallet2);
           return true;
       }

    }

}
