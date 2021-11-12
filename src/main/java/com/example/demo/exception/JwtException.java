package com.example.demo.exception;

public class JwtException extends RuntimeException {
    public JwtException(String exMessage) {
        super(exMessage);
    }
}