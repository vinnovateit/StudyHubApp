package com.vinnovateit.studyhub.model;

public class Search {
    String header;
    String details;
    String descUrl;
    Integer pos;

    public Search(String header, String details, String descUrl, Integer pos) {
        this.header = header;
        this.details = details;
        this.descUrl = descUrl;
        this.pos = pos;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDescUrl() {
        return descUrl;
    }

    public void setDescUrl(String descUrl) {
        this.descUrl = descUrl;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }
}
