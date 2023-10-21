package com.example.demo.url.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "URL")
public class URLEntity {

    @Id
    public String url;
    public boolean blacklisted;

    public URLEntity(String url, boolean blacklisted) {
        this.url = url;
        this.blacklisted = blacklisted;
    }

    public URLEntity() {
    }

    public String getUrl() {
        return url;
    }

    public boolean isBlacklisted() {
        return blacklisted;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setBlacklisted(boolean blacklisted) {
        this.blacklisted = blacklisted;
    }
}
