package org.skopintsev.helper.enums;

import lombok.Getter;

@Getter
public enum TransactionTypeEnum {

    INSERT("INSERT"),
    WITHDRAW("WITHDRAW");

    private final String text;

    TransactionTypeEnum(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
