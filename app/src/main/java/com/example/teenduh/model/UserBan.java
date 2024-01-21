package com.example.teenduh.model;

import java.time.LocalDate;

public class UserBan {
    private String id;
    private String reason;
    private LocalDate deadline;

    public UserBan(String id, String reason, LocalDate deadline) {
        this.id = id;
        this.reason = reason;
        this.deadline = deadline;
    }

    public String getId() {
        return id;
    }

    public String getReason() {
        return reason;
    }

    public LocalDate getDeadline() {
        return deadline;
    }
}
