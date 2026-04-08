package org.skopintsev.helper.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PrivilegeLevelEnum {

    HIGHEST(1, "HIGHEST"),
    HIGH(2, "HIGH"),
    ELEVATED(3, "ELEVATED"),
    STANDARD(4, "STANDARD"),
    RESTRICTED(5, "RESTRICTED");

    private final int priority;
    private final String text;

    @Override
    public String toString() {
        return text;
    }
}
