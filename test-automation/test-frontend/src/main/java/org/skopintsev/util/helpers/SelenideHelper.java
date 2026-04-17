package org.skopintsev.util.helpers;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.by;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

public class SelenideHelper {

    @Step("Get web element by its data-testid.")
    public static By byTestId(String testIdValue) {
        return by("data-testid", testIdValue);
    }

    @Step("Get web element from a list by its text.")
    public static SelenideElement selectBoxElementByText(String text) {
        return $(byXpath("//li[text()='" + text + "']"));
    }

    @Step("Get web element by its data-testid and text.")
    public static By byXpathDataTestIdAndText(String testIdValue, String text) {
        return byXpath("//*[@data-testid='" + testIdValue + "'][text()='" + text + "']");
    }

}
