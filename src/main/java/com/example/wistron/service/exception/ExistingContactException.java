package com.example.wistron.service.exception;

import java.time.LocalDateTime;

public class ExistingContactException extends Exception{
    private final LocalDateTime time;

    public ExistingContactException(String errorMessage, LocalDateTime time) {
        super(errorMessage);
        this.time = time;
    }
}
