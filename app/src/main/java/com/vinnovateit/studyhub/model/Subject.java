package com.vinnovateit.studyhub.model;

public class Subject {
    String module;
    String description;

    public Subject(String module, String description) {
        this.module = module;
        this.description = description;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
