package com.example.demo.url.repository;

import com.example.demo.url.model.URLEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface URLsRepository extends JpaRepository<URLEntity, String> {

    Optional<URLEntity> findByUrl(String url);
}
