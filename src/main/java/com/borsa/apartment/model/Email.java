package com.borsa.apartment.model;

public class Email {
    private String receiver;
    private String subject;
    private String body;

    public static Email listingCreatedEmail(String receiver) {
        return new Email(
                receiver,
                "Apartment Hunter Listing",
                "Your listing has been created successfully, to view it please navigate to my listings page.");
    }

    public Email(String receiver, String subject, String body) {
        this.receiver = receiver;
        this.subject = subject;
        this.body = body;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
