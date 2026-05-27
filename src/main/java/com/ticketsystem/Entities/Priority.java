package com.ticketsystem.Entities;

import lombok.Getter;

@Getter
public enum Priority {
    LOW("LOW"),
    MEDIUM("MEDIUM"),
    HIGH("HIGH"),
    CRITICAL("CRITICAL");

    private final String value;

    Priority(String value) {
        this.value = value;
    }
}
