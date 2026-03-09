package org.skopintsev.models.gui.common.popups;

import com.codeborne.selenide.SelenideElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static org.skopintsev.util.helpers.SelenideHelper.byTestId;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationPopup {

    @Getter
    static final SelenideElement notificationPanel = $(byTestId("notification"));

    @Getter
    static final SelenideElement closeBtn = notificationPanel.$(byTestId("closeIcon"));

    public static SelenideElement getNotificationWithText(String text) {
        String xPath = String.format("//*[@data-testid='notification' and .//text()='%s']", text);
        return $(byXpath(xPath));
    }
}
