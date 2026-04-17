package org.skopintsev.steps.common.elements;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.skopintsev.models.gui.common.elements.CheckboxElement;

import java.util.List;

public class CheckboxElementSteps {

    @Step("Click checkbox with label '{0}'.")
    public static void clickCheckboxByLabel(String labelValue) {
        CheckboxElement.getCheckboxByLabel(labelValue).click();
    }

    @Step("Click multiple checkboxes.")
    public static void clickMultipleCheckboxesByLabels(List<String> labelValues) {
        if (labelValues != null) {
            labelValues.stream()
                    .map(CheckboxElement::getCheckboxByLabel)
                    .forEach(SelenideElement::click);
        }
    }

}
