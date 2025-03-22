package me.project.time_to_shine.CDR.model;

import lombok.Getter;

@Getter
public enum CallDirection {
    OUTGOING("02"),
    INCOMING("01");

    private final String code;

    CallDirection(String code) {
        this.code = code;
    }

}
