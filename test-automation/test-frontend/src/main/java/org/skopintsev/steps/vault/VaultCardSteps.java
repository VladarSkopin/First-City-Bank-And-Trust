package org.skopintsev.steps.vault;

import io.qameta.allure.Step;
import org.skopintsev.models.gui.vaults.VaultCard;

public class VaultCardSteps {

    @Step("Click 'DEPOSIT' button.")
    public static void clickDepositBtn() {
        VaultCard.getDepositBtn().click();
    }

    @Step("Click 'WITHDRAW' button.")
    public static void clickWithdrawBtn() {
        VaultCard.getWithdrawBtn().click();
    }
}
