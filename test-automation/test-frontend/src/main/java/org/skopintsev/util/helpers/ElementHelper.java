package org.skopintsev.util.helpers;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.skopintsev.assertions.common.ElementAssertions;

import java.time.Duration;

public class ElementHelper {

    @Step("Waiting for the disappearance of loading spinner withing 4 seconds.")
    public static void waitingForLoading4Sec(SelenideElement loader) {
        ElementAssertions.checkDisappear(loader, Duration.ofSeconds(4));
    }
}
