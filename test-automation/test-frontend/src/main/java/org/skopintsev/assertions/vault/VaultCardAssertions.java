package org.skopintsev.assertions.vault;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.skopintsev.models.gui.vaults.VaultCard;

public class VaultCardAssertions {

    @Step("Check vault title is displayed with text '{0}'.")
    public static void checkVaultTitle(String titleText) {
        VaultCard.getVaultTitle().shouldBe(Condition.visible).shouldHave(Condition.text(titleText));
    }

    @Step("Check currency name is displayed with text '{0}'.")
    public static void checkCurrencyName(String currencyName) {
        VaultCard.getVaultCurrencyName().shouldBe(Condition.visible).shouldHave(Condition.text(currencyName));
    }

    @Step("Check client id label is displayed with text '{0}'.")
    public static void checkClientIdLabelText(String labelText) {
        VaultCard.getClientIdLabel().shouldBe(Condition.visible).shouldHave(Condition.text(labelText));
    }

    @Step("Check client id value is displayed with text '{0}'.")
    public static void checkClientCodeText(String valueText) {
        VaultCard.getClientIdCode().shouldBe(Condition.visible).shouldHave(Condition.text(valueText));
    }

    @Step("Check client name label is displayed with text '{0}'.")
    public static void checkClientNameLabelText(String labelText) {
        VaultCard.getClientNameLabel().shouldBe(Condition.visible).shouldHave(Condition.text(labelText));
    }

    @Step("Check client name value is displayed with text '{0}'.")
    public static void checkClientNameValueText(String valueText) {
        VaultCard.getClientNameValue().shouldBe(Condition.visible).shouldHave(Condition.text(valueText));
    }

    @Step("Check status label is displayed with text '{0}'.")
    public static void checkStatusLabelText(String labelText) {
        VaultCard.getStatusLabel().shouldBe(Condition.visible).shouldHave(Condition.text(labelText));
    }

    @Step("Check status value is displayed with text '{0}'.")
    public static void checkStatusValueText(String valueText) {
        VaultCard.getStatusValue().shouldBe(Condition.visible).shouldHave(Condition.text(valueText));
    }

    @Step("Check last access label is displayed with text '{0}'.")
    public static void checkLastAccessLabelText(String labelText) {
        VaultCard.getLastAccessLabel().shouldBe(Condition.visible).shouldHave(Condition.text(labelText));
    }

    @Step("Check last access value is displayed with text '{0}'.")
    public static void checkLastAccessDateTimeText(String valueText) {
        VaultCard.getLastAccessDateTime().shouldBe(Condition.visible).shouldHave(Condition.text(valueText));
    }

    @Step("Check balance label is displayed with text '{0}'.")
    public static void checkBalanceLabelText(String labelText) {
        VaultCard.getBalanceLabel().shouldBe(Condition.visible).shouldHave(Condition.text(labelText));
    }

    @Step("Check balance value is displayed with text '{0}'.")
    public static void checkBalanceValueText(String valueText) {
        VaultCard.getBalanceValue().shouldBe(Condition.visible).shouldHave(Condition.text(valueText));
    }

    @Step("Check 'DEPOSIT' button has text '{0}'.")
    public static void checkDepositBtnText(String buttonText) {
        VaultCard.getDepositBtn().shouldBe(Condition.visible).shouldHave(Condition.text(buttonText));
    }

    @Step("Check 'DEPOSIT' button is enabled = '{0}'.")
    public static void checkDepositBtnEnabled(boolean shouldBeEnabled) {
        VaultCard.getDepositBtn().shouldBe(shouldBeEnabled ? Condition.enabled : Condition.disabled);
    }

    @Step("Check 'WITHDRAW' button has text '{0}'.")
    public static void checkWithdrawBtnText(String buttonText) {
        VaultCard.getWithdrawBtn().shouldBe(Condition.visible).shouldHave(Condition.text(buttonText));
    }

    @Step("Check 'WITHDRAW' button is enabled = '{0}'.")
    public static void checkWithdrawBtnEnabled(boolean shouldBeEnabled) {
        VaultCard.getWithdrawBtn().shouldBe(shouldBeEnabled ? Condition.enabled : Condition.disabled);
    }
}
