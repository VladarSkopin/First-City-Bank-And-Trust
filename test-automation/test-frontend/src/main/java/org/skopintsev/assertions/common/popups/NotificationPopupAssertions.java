package org.skopintsev.assertions.common.popups;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.skopintsev.models.gui.common.popups.NotificationPopup;

public class NotificationPopupAssertions {

    @Step("Check the notification display with text '{0}'.")
    public static void checkNotificationWithTextDisplayed(String content) {
        NotificationPopup.getNotificationWithText(content).shouldBe(Condition.visible);
    }

}
