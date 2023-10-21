package com.example.demo.subscription.service;

import com.example.demo.subscription.model.Subscriber;
import com.example.demo.subscription.repository.SubscriberRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriptionService {


    private final SubscriberRepository subscriberRepository;

    public SubscriptionService(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    public boolean isActiveSubscription(long number) {
        return subscriberRepository.findByNumber(number).map(Subscriber::isActiveSubscription).orElse(false);
    }
    private Subscriber getSubscriberAndSetSubscriptionStatus(long number, boolean activeSubscription){
        Optional<Subscriber> byNumber = subscriberRepository.findByNumber(number);
        Subscriber subscriber;
        if(byNumber.isPresent()){
            subscriber = byNumber.get();
            subscriber.setActiveSubscription(activeSubscription);
        }else {
            subscriber = new Subscriber(number, activeSubscription);
        }
        return subscriber;
    }

    @Transactional
    public void subscribe(long number) {
        Subscriber subscriber = getSubscriberAndSetSubscriptionStatus(number, true);
        subscriberRepository.saveAndFlush(subscriber);
    }


    @Transactional
    public void unsubscribe(long number) {
        Subscriber subscriber = getSubscriberAndSetSubscriptionStatus(number, false);
        subscriberRepository.saveAndFlush(subscriber);
    }
}
