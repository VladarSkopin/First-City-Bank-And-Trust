package com.firstcitybank.trustbank.helper.enums;

import lombok.Getter;

public enum ClientType {

    INDV("Individual"),
    CORP("Corporation"),
    PART("Partnership"),
    TRST("Trust"),
    NPO("Non-Profit Organization"),
    GOVT("Government Entity"),
    UNK("Unknown"),
    SS("Secret Society"),
    RO("Religious Order"),
    DC("Diseased Clients");

    @Getter
    private final String text;

    ClientType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
