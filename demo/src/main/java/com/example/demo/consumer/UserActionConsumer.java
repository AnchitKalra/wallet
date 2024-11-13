package com.example.demo.consumer;


import com.example.demo.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;



@Service
public class UserActionConsumer {

    @Autowired
    WalletService walletService;

    Logger logger = LoggerFactory.getLogger(UserActionConsumer.class);




    @KafkaListener(topics = "user_created", groupId = "user")
    public void receiveMessage(String message) {
        logger.info(message);
        walletService.createWallet(Long.parseLong(message));
    }

}
