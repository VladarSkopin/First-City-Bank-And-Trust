package org.skopintsev.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperationTypeEnum {

    WITHDRAW("WITHDRAW"),
    DEPOSIT("DEPOSIT"),
    TRANSFER("TRANSFER");

    private final String text;
}
