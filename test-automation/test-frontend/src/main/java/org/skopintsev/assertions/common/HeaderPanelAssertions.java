package org.skopintsev.assertions.common;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

public class HeaderPanelAssertions {

    @Step("Check header panel tab should have class 'nav-link active' when clicked.")
    public static void checkHeaderPanelTabActive(SelenideElement element) {
        element.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .shouldHave(Condition.cssClass("nav-link"))
                .shouldHave(Condition.cssClass("active"));
    }

    @Step("Check header panel tab should have class 'nav-link' when NOT clicked.")
    public static void checkHeaderPanelTabNotActive(SelenideElement element) {
        element.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .shouldHave(Condition.cssClass("nav-link"))
                .shouldNotHave(Condition.cssClass("active"));
    }

}
