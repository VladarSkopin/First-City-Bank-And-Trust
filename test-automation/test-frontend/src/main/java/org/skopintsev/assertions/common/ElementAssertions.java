package org.skopintsev.assertions.common;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

public class ElementAssertions {

    @Step("Waiting for the disappearance of loading spinner withing 4 seconds.")
    public static void checkDisappear(SelenideElement element, Duration timeout) {
        if (element.exists()) {
            element.should(Condition.disappear, timeout);
        }
    }

    @Step("Check input element is visible and has empty input field.")
    public static void checkInputEmpty(SelenideElement element) {
        element.shouldBe(Condition.visible).shouldBe(Condition.empty);
    }

}
