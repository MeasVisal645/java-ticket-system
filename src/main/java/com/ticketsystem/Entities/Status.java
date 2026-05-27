package com.ticketsystem.Entities;

import lombok.Getter;

@Getter
public enum Status {
    OPEN("OPEN"),
    IN_PROGRESS("IN_PROGRESS"),
    RESOLVED("RESOLVED"),
    CLOSED("CLOSED"),
    REOPENED("REOPENED"),;

    private final String value;

    Status(String value) {
        this.value = value;
    }
}
