package org.skopintsev.helper.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SearchByEnum {
    SECTOR("SECTOR"),
    SUBSECTOR("SUBSECTOR");

    private final String text;
}
