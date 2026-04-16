package org.skopintsev.models.gui.vaults;

import com.codeborne.selenide.SelenideElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static com.codeborne.selenide.Selenide.$;
import static org.skopintsev.util.helpers.SelenideHelper.byTestId;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class VaultCard {

    @Getter static final SelenideElement vaultTitle = $(byTestId("vaultTitle"));

    @Getter static final SelenideElement vaultCurrencyName = $(byTestId("vaultCurrencyName"));

    @Getter static final SelenideElement clientIdLabel = $(byTestId("clientIdLabel"));

    @Getter static final SelenideElement clientIdCode = $(byTestId("clientIdCode"));

    @Getter static final SelenideElement clientNameLabel = $(byTestId("clientNameLabel"));

    @Getter static final SelenideElement clientNameValue = $(byTestId("clientNameValue"));

    @Getter static final SelenideElement statusLabel = $(byTestId("statusLabel"));

    @Getter static final SelenideElement statusValue = $(byTestId("statusValue"));

    @Getter static final SelenideElement lastAccessLabel = $(byTestId("lastAccessLabel"));

    @Getter static final SelenideElement lastAccessDateTime = $(byTestId("lastAccessDateTime"));

    @Getter static final SelenideElement balanceLabel = $(byTestId("balanceLabel"));

    @Getter static final SelenideElement balanceValue = $(byTestId("balanceValue"));

    @Getter static final SelenideElement depositBtn = $(byTestId("depositBtn"));

    @Getter static final SelenideElement withdrawBtn = $(byTestId("withdrawBtn"));
}
