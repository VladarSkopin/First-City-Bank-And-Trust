package org.skopintsev.vaults_page;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.skopintsev.assertions.common.elements.ButtonElementAssertions;
import org.skopintsev.assertions.common.windows.ModalWindowAssertions;
import org.skopintsev.steps.vault.VaultCardSteps;

public class VaultOperationsDepositTest extends BaseVaultTest {

    @Test
    @Tag("smoke")
    @Description("Test checks the deposit operation with zero amount.")
    @Severity(SeverityLevel.BLOCKER)
    public void depositZeroAmountTest() {
        // todo: balance = 0, deposit = 0, confirm disabled
        VaultCardSteps.clickDepositBtn();

        ModalWindowAssertions.checkHeaderText("");
        ModalWindowAssertions.checkModalBodyMinHeight(300);
        ModalWindowAssertions.checkModalBodyMinWidth(600);
        ButtonElementAssertions.checkCrossCloseBtnIsVisible();
        ButtonElementAssertions.checkCrossCloseBtnEnabled(true);

    }

    @Test
    @Tag("smoke")
    @Description("Test checks the deposit operation with empty vault.")
    @Severity(SeverityLevel.BLOCKER)
    public void depositPositiveAmountEmptyVaultTest() {
        // todo: balance = 0, deposit > 0 random, confirm enabled, operation new balance, click cancel, check current balance
        VaultCardSteps.clickDepositBtn();



    }

    @Test
    @Tag("smoke")
    @Description("Test checks the deposit operation with non-empty vault.")
    @Severity(SeverityLevel.BLOCKER)
    public void depositPositiveAmountNonEmptyVaultTest() {
        // todo: balance > 0, deposit > 0 random, confirm enabled, operation new balance, click confirm, check current balance
        VaultCardSteps.clickDepositBtn();



    }
}
