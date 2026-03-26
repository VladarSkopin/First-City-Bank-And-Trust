package org.skopintsev.steps.vault;

import io.qameta.allure.Step;
import org.skopintsev.models.gui.vaults.VaultOperationsWindow;

public class VaultOperationsWindowSteps {

    @Step("Click 'CANCEL' button.")
    public static void clickCancelBtn() {
        VaultOperationsWindow.getCancelBtn().click();
    }

    @Step("Click 'INFO' button.")
    public static void clickSubmitBtn() {
        VaultOperationsWindow.getSubmitButton().click();
    }
}
