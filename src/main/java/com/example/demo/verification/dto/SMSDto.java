package com.example.demo.verification.dto;

public class SMSDto {
    //TODO validation
    private final long sender;
    //TODO validation
    private final long recipient;
    private final String message;

    public long getSender() {
        return sender;
    }

    public long getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public SMSDto(long sender, long recipient, String message) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
    }
}
