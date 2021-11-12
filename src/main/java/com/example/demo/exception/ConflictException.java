package com.example.demo.exception;

public class ConflictException extends RuntimeException {

    private String msg;

    public String getCustomMsg() {
        return msg;
    }

    public ConflictException(String exception) {
        this.msg = exception;
    }


}
