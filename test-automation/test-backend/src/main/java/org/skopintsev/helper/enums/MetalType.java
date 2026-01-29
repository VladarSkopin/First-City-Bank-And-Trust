package org.skopintsev.helper.enums;

import lombok.Getter;

@Getter
public enum MetalType {

    UNKNOWN("UNKNOWN"),
    BRONZE("BRONZE"),
    GOLD("GOLD"),
    SILVER("SILVER"),
    COPPER("COPPER");

    private final String text;

    MetalType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
