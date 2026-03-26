package org.skopintsev.vaults_page;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.skopintsev.assertions.common.elements.ButtonElementAssertions;
import org.skopintsev.assertions.common.windows.ModalWindowAssertions;
import org.skopintsev.assertions.vault.VaultOperationsWindowAssertions;
import org.skopintsev.models.api.factory.VaultFactory;
import org.skopintsev.models.api.vault.Vault;
import org.skopintsev.steps.vault.VaultCardSteps;
import org.skopintsev.steps.vault.VaultOperationsWindowSteps;
import org.skopintsev.transport.PostApiResponseHelper;

import java.math.BigInteger;
import java.util.List;


public class VaultOperationsDepositTest extends BaseVaultTest {

    @Test
    @Tag("smoke")
    @Description("Test checks the deposit operation with zero amount.")
    @Severity(SeverityLevel.BLOCKER)
    public void depositZeroAmountTest() {
        Vault vault = VaultFactory.generateVault(
                BASE_CURRENCIES_LIST.get(2).getCurrencyCode(),
                BASE_CLIENTS_LIST.get(1).getClientCode(),
                true);
        vault.setAmount(BigInteger.valueOf(0));
        PostApiResponseHelper.stubGetVaults(List.of(vault));
        Selenide.refresh();

        // todo: balance = 0, deposit = 0, confirm disabled
        VaultCardSteps.clickDepositBtn();

        ModalWindowAssertions.checkHeaderText("");
        ModalWindowAssertions.checkModalBodyMinHeight(300);
        ModalWindowAssertions.checkModalBodyMinWidth(600);
        ButtonElementAssertions.checkCrossCloseBtnIsVisible();
        ButtonElementAssertions.checkCrossCloseBtnEnabled(true);

        VaultOperationsWindowAssertions.checkClientLabel("");
        VaultOperationsWindowAssertions.checkClientName("");
        VaultOperationsWindowAssertions.checkVaultLabel("");
        VaultOperationsWindowAssertions.checkVaultCode("");
        VaultOperationsWindowAssertions.checkCurrentBalanceLabel("");
        VaultOperationsWindowAssertions.checkCurrentBalanceValue("");
        VaultOperationsWindowAssertions.checkAmountLabel("");
        VaultOperationsWindowAssertions.checkAmountInput("");
        VaultOperationsWindowAssertions.checkValidationMessage("");
        VaultOperationsWindowAssertions.checkInputHint("");

        VaultOperationsWindowAssertions.checkCancelBtnEnabled(true);
        VaultOperationsWindowAssertions.checkCancelBtnText("");
        VaultOperationsWindowAssertions.checkSubmitBtnEnabled(true);
        VaultOperationsWindowAssertions.checkSubmitBtnText("");
    }

    @Test
    @Tag("smoke")
    @Description("Test checks the deposit operation with empty vault.")
    @Severity(SeverityLevel.BLOCKER)
    public void depositPositiveAmountEmptyVaultTest() {
        // todo: balance = 0, deposit > 0 random, confirm enabled, operation new balance, click cancel, check current balance
        VaultCardSteps.clickDepositBtn();

        VaultOperationsWindowSteps.clickCancelBtn();

    }

    @Test
    @Tag("smoke")
    @Description("Test checks the deposit operation with non-empty vault.")
    @Severity(SeverityLevel.BLOCKER)
    public void depositPositiveAmountNonEmptyVaultTest() {
        // todo: balance > 0, deposit > 0 random, confirm enabled, operation new balance, click confirm, check current balance
        VaultCardSteps.clickDepositBtn();

        VaultOperationsWindowSteps.clickSubmitBtn();
        // todo: check API request and response were correct
    }
}
