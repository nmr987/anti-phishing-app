package com.example.demo.subscription.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Subscriber {

    @Id
    private long number;

    private boolean activeSubscription;

    public Subscriber(long number, boolean activeSubscription) {
        this.number = number;
        this.activeSubscription = activeSubscription;
    }

    public Subscriber() {
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public boolean isActiveSubscription() {
        return activeSubscription;
    }

    public void setActiveSubscription(boolean activeSubscription) {
        this.activeSubscription = activeSubscription;
    }

}
