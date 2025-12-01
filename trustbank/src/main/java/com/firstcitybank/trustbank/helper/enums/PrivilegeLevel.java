package com.firstcitybank.trustbank.helper.enums;

import lombok.Getter;

public enum PrivilegeLevel {

    HIGHEST(1, "Highest"),
    HIGH(2, "High"),
    ELEVATED(3, "Elevated"),
    STANDARD(4, "Standard"),
    RESTRICTED(5, "Restricted");

    @Getter
    private final int priority;
    @Getter
    private final String text;

    PrivilegeLevel(int priority, String text) {
        this.priority = priority;
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
