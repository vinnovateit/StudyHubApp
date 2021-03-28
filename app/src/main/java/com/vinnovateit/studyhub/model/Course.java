package com.vinnovateit.studyhub.model;

public class Course {
    String header;
    String details;
    String descUrl;

    public Course(String header, String details, String descUrl) {
        this.header = header;
        this.details = details;
        this.descUrl = descUrl;
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
}
