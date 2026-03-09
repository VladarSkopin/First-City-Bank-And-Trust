package org.skopintsev.steps.common.popups;

import io.qameta.allure.Step;
import org.skopintsev.models.gui.common.popups.NotificationPopup;

public class NotificationPopupSteps {

    @Step("Close notification panel.")
    public static void clickCLoseBtn() {
        if(NotificationPopup.getNotificationPanel().isDisplayed()) {
            NotificationPopup.getCloseBtn().click();
        }
    }

}
