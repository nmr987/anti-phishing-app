package com.example.demo.verification.service;

import com.example.demo.url.model.URLEntity;
import com.example.demo.url.dto.URLStatusDTO;
import com.example.demo.url.repository.URLsRepository;
import org.springframework.stereotype.Service;

@Service
public class VerificationService {

    private final URLsRepository urlsRepository;

    public VerificationService(URLsRepository urlsRepository) {
        this.urlsRepository = urlsRepository;
    }

    public URLStatusDTO getURLStatus(String url){
        return urlsRepository.findByUrl(url.toLowerCase())
                .map(entity -> new URLStatusDTO(entity.getUrl(), entity.isBlacklisted(), true))
                .orElse(new URLStatusDTO(url, false, false));
    }

    public void saveURLStatus(String url, boolean blacklisted) {
        urlsRepository.saveAndFlush(new URLEntity(url, blacklisted));
    }
}
