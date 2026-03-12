package org.skopintsev.assertions.common.elements;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.skopintsev.models.gui.common.elements.ButtonElement;

import static com.codeborne.selenide.Condition.not;

public class ButtonElementAssertions {

    @Step("Check that 'Retry' button is visible.")
    public static void checkRetryBtnIsVisible() {
        ButtonElement.getRetryBtn().shouldBe(Condition.visible);
    }

    @Step("Check that 'Ok' button is visible.")
    public static void checkOkBtnIsVisible() {
        ButtonElement.getOkBtn().shouldBe(Condition.visible);
    }

    @Step("Check that cross '[X]' button is visible.")
    public static void checkCrossCloseBtnIsVisible() {
        ButtonElement.getCrossCloseBtn().shouldBe(Condition.visible);
    }

    @Step("Check that 'Retry' button should exist = '{0}'.")
    public static void checkRetryBtnExists(boolean shouldExist) {
        ButtonElement.getRetryBtn().shouldBe(shouldExist ? Condition.exist : not(Condition.exist));
    }

    @Step("Check that 'Retry' button is enabled = '{0}'.")
    public static void checkRetryBtnEnabled(boolean shouldBeEnabled) {
        ButtonElement.getRetryBtn().shouldBe(shouldBeEnabled ? Condition.enabled : Condition.disabled);
    }

    @Step("Check that 'Ok' button is enabled = '{0}'.")
    public static void checkOkBtnEnabled(boolean shouldBeEnabled) {
        ButtonElement.getOkBtn().shouldBe(shouldBeEnabled ? Condition.enabled : Condition.disabled);
    }

    @Step("Check that cross '[X]' button is enabled = '{0}'.")
    public static void checkCrossCloseBtnEnabled(boolean shouldBeEnabled) {
        ButtonElement.getCrossCloseBtn().shouldBe(shouldBeEnabled ? Condition.enabled : Condition.disabled);
    }

}
