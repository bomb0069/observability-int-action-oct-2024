package com.example.user;

import java.time.LocalDateTime;

public class User {
    private Long id;

    private Integer orgId;
    private String name;
    private LocalDateTime created;
    private LocalDateTime updated;

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
        this.created = LocalDateTime.now();
        this.updated = LocalDateTime.now();
    }

    // Constructors, Getters, and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

}