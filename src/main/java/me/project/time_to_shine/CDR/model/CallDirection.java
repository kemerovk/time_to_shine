package me.project.time_to_shine.CDR.model;

import lombok.Getter;

@Getter
public enum CallDirection {
    OUTGOING("01"),
    INCOMING("02");

    private final String code;

    CallDirection(String code) {
        this.code = code;
    }

    public static CallDirection codeToString(String code){
        return code.equals("01")? OUTGOING: INCOMING;
    }
}
