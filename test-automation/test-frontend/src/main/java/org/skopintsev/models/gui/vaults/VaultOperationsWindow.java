package org.skopintsev.models.gui.vaults;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$;
import static org.skopintsev.util.helpers.SelenideHelper.byTestId;

public class VaultOperationsWindow {

    @Getter static final SelenideElement clientLabel = $(byTestId("clientLabelWindow"));

    @Getter static final SelenideElement clientName = $(byTestId("clientNameWindow"));

    @Getter static final SelenideElement vaultLabel = $(byTestId("vaultLabelWindow"));

    @Getter static final SelenideElement vaultCode = $(byTestId("vaultCodeWindow"));

    @Getter static final SelenideElement currentBalanceLabel = $(byTestId("currentBalanceLabelWindow"));

    @Getter static final SelenideElement currentBalanceValue = $(byTestId("currentBalanceValueWindow"));

    @Getter static final SelenideElement amountLabel = $(byTestId("amountLabelWindow"));

    @Getter static final SelenideElement amountInput = $(byTestId("amountInput"));

    @Getter static final SelenideElement validationMessage = $(byTestId("validationMessage"));

    @Getter static final SelenideElement inputHint = $(byTestId("inputHint"));

    @Getter static final SelenideElement cancelBtn = $(byTestId("cancelButton"));

    @Getter static final SelenideElement submitBtn = $(byTestId("submitButton"));
}
