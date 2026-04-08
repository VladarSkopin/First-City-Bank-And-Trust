package org.skopintsev.steps.vault;

import io.qameta.allure.Step;
import org.skopintsev.models.gui.vaults.VaultOperationsWindow;

public class VaultOperationsWindowSteps {

    @Step("Click 'CANCEL' button.")
    public static void clickCancelBtn() {
        VaultOperationsWindow.getCancelBtn().click();
    }

    @Step("Click 'CONFIRM' button.")
    public static void clickSubmitBtn() {
        VaultOperationsWindow.getSubmitBtn().click();
    }

    @Step("Input amount = '{0}'.")
    public static void typeInputAmount(int amount) {
        VaultOperationsWindow.getAmountInput().type(String.valueOf(amount));
    }
}
