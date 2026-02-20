package org.skopintsev.helper.enums;

import lombok.Getter;

@Getter
public enum TransactionType {

    INSERT("INSERT"),
    WITHDRAW("WITHDRAW");

    private final String text;

    TransactionType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
