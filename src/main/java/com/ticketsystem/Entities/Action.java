package com.ticketsystem.Entities;

import lombok.Getter;

@Getter
public enum Action {
    INSERT("INSERT"),
    UPDATE("UPDATE"),
    DELETE("DELETE");

    private final String value;

    Action(String value) {
        this.value = value;
    }
}
