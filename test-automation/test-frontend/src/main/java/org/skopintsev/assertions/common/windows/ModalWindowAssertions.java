package org.skopintsev.assertions.common.windows;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.skopintsev.models.gui.common.windows.ModalWindow;

public class ModalWindowAssertions {

    @Step("Check the modal window header has text '{0}'.")
    public static void checkHeaderText(String expectedText) {
        ModalWindow.getModalHeader().shouldBe(Condition.visible).shouldHave(Condition.text(expectedText));
    }

    @Step("Check the modal window body has width at least '{0}' pixels.")
    public static void checkModalBodyMinWidth(int expectedMinWidth) {
        SelenideElement modalBody = ModalWindow.getModalBody();
        modalBody.shouldBe(Condition.visible);
        int actualWidth = modalBody.getSize().getWidth();
        Assertions.assertThat(actualWidth).isGreaterThanOrEqualTo(expectedMinWidth);
    }

    @Step("Check the modal window body has height at least '{0}' pixels.")
    public static void checkModalBodyMinHeight(int expectedMinHeight) {
        SelenideElement modalBody = ModalWindow.getModalBody();
        modalBody.shouldBe(Condition.visible);
        int actualHeight = modalBody.getSize().getHeight();
        Assertions.assertThat(actualHeight).isGreaterThanOrEqualTo(expectedMinHeight);
    }
}
