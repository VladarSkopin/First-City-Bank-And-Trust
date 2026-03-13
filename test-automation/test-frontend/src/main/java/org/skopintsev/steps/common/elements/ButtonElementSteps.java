package org.skopintsev.steps.common.elements;

import io.qameta.allure.Step;
import org.skopintsev.models.gui.common.elements.ButtonElement;

public class ButtonElementSteps {

    @Step("Click the 'Retry' button.")
    public static void clickRetryButton() {
        ButtonElement.getRetryBtn().click();
    }

    @Step("Click the 'Ok' button.")
    public static void clickConfirmSelectBtn() {
        ButtonElement.getOkBtn().click();
    }

    @Step("Click the cross '[X]' button.")
    public static void clickCrossCloseBtn() {
        ButtonElement.getCrossCloseBtn().click();
    }

}
