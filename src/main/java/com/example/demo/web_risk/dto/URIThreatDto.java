package com.example.demo.web_risk.dto;

import com.example.demo.web_risk.enums.ThreatType;

public class URIThreatDto {
    private String uri;
    private ThreatType[] threatTypes;
    private boolean allowScan;

    public URIThreatDto(String uri, ThreatType[] threatTypes, boolean allowScan) {
        this.uri = uri;
        this.threatTypes = threatTypes;
        this.allowScan = allowScan;
    }

    public URIThreatDto() {
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public ThreatType[] getThreatTypes() {
        return threatTypes;
    }

    public void setThreatTypes(ThreatType[] threatTypes) {
        this.threatTypes = threatTypes;
    }

    public boolean isAllowScan() {
        return allowScan;
    }

    public void setAllowScan(boolean allowScan) {
        this.allowScan = allowScan;
    }
}
