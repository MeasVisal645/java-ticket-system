package com.ticketsystem.Entities;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ADMIN"),
    USER("USER"),
    TECHNICAL("TECHNICAL"),
    AGENT("AGENT");

    private final String value;

    Role(String value){
        this.value = value;
    }
}
