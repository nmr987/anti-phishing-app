package com.example.demo.subscription.repository;

import com.example.demo.subscription.model.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    Optional<Subscriber> findByNumber(long number);
}
