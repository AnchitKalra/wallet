package com.example.demo.exception;

public class WalletException extends Exception{



    private String message;

    public WalletException(String message) {
        super(message);
    }


}
