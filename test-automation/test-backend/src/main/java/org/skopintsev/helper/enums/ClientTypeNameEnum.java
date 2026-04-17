package org.skopintsev.helper.enums;

import lombok.Getter;

@Getter
public enum ClientTypeNameEnum {

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

    ClientTypeNameEnum(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
