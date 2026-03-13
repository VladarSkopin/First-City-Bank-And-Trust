package org.skopintsev.steps.common.elements;

import io.qameta.allure.Step;
import org.skopintsev.models.gui.common.elements.LoadingSpinner;
import org.skopintsev.util.helpers.ElementHelper;

public class LoadingSpinnerSteps {

    @Step("Wait for the web page to load.")
    public static void waitingForPageLoading() {
        ElementHelper.waitingForLoading4Sec(LoadingSpinner.getPageLoader());
    }

}
