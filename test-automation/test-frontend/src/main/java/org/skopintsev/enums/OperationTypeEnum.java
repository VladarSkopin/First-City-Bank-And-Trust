package org.skopintsev.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperationTypeEnum {

    WITHDRAW("WITHDRAW"),
    DEPOSIT("INSERT"),
    TRANSFER("TRANSFER");

    private final String text;
}
