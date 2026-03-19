package org.skopintsev.enums;

import lombok.Getter;

@Getter
public enum ClientTypeName {

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

    private final String text;

    ClientTypeName(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
