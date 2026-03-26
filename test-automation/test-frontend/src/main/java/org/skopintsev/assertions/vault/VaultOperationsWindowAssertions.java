package org.skopintsev.assertions.vault;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.skopintsev.models.gui.vaults.VaultOperationsWindow;

public class VaultOperationsWindowAssertions {

    @Step("Check client label is displayed with text '{0}'.")
    public static void checkClientLabel(String labelText) {
        VaultOperationsWindow.getClientLabel().shouldBe(Condition.visible).shouldHave(Condition.text(labelText));
    }

    @Step("Check client name is displayed with text '{0}'.")
    public static void checkClientName(String clientName) {
        VaultOperationsWindow.getClientName().shouldBe(Condition.visible).shouldHave(Condition.text(clientName));
    }

    @Step("Check vault label is displayed with text '{0}'.")
    public static void checkVaultLabel(String labelText) {
        VaultOperationsWindow.getVaultLabel().shouldBe(Condition.visible).shouldHave(Condition.text(labelText));
    }

    @Step("Check vault code is displayed with text '{0}'.")
    public static void checkVaultCode(String vaultCode) {
        VaultOperationsWindow.getVaultCode().shouldBe(Condition.visible).shouldHave(Condition.text(vaultCode));
    }

    @Step("Check balance label is displayed with text '{0}'.")
    public static void checkCurrentBalanceLabel(String labelText) {
        VaultOperationsWindow.getCurrentBalanceLabel().shouldBe(Condition.visible).shouldHave(Condition.text(labelText));
    }

    @Step("Check balance value is displayed with text '{0}'.")
    public static void checkCurrentBalanceValue(String balanceText) {
        VaultOperationsWindow.getCurrentBalanceValue().shouldBe(Condition.visible).shouldHave(Condition.text(balanceText));
    }

    @Step("Check amount label is displayed with text '{0}'.")
    public static void checkAmountLabel(String labelText) {
        VaultOperationsWindow.getAmountLabel().shouldBe(Condition.visible).shouldHave(Condition.text(labelText));
    }

    @Step("Check amount input is displayed with text '{0}'.")
    public static void checkAmountInput(String amountInputText) {
        VaultOperationsWindow.getAmountInput().shouldBe(Condition.visible).shouldHave(Condition.text(amountInputText));
    }

    @Step("Check validation message is displayed with text '{0}'.")
    public static void checkValidationMessage(String validationMessageText) {
        VaultOperationsWindow.getValidationMessage().shouldBe(Condition.visible).shouldHave(Condition.text(validationMessageText));
    }

    @Step("Check input hint is displayed with text '{0}'.")
    public static void checkInputHint(String inputHintText) {
        VaultOperationsWindow.getInputHint().shouldBe(Condition.visible).shouldHave(Condition.text(inputHintText));
    }

    @Step("Check 'CANCEL' button has text '{0}'.")
    public static void checkCancelBtnText(String buttonText) {
        VaultOperationsWindow.getCancelBtn().shouldBe(Condition.visible).shouldHave(Condition.text(buttonText));
    }

    @Step("Check 'CANCEL' should be enabled = '{0}'.")
    public static void checkCancelBtnEnabled(boolean shouldBeEnabled) {
        VaultOperationsWindow.getCancelBtn().shouldBe(shouldBeEnabled ? Condition.enabled : Condition.disabled);
    }

    @Step("Check 'SUBMIT' button has text '{0}'.")
    public static void checkSubmitBtnText(String buttonText) {
        VaultOperationsWindow.getSubmitButton().shouldBe(Condition.visible).shouldHave(Condition.text(buttonText));
    }

    @Step("Check 'SUBMIT' should be enabled = '{0}'.")
    public static void checkSubmitBtnEnabled(boolean shouldBeEnabled) {
        VaultOperationsWindow.getSubmitButton().shouldBe(shouldBeEnabled ? Condition.enabled : Condition.disabled);
    }
}
