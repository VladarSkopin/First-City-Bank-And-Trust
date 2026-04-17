package org.skopintsev.models.gui.common.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static org.skopintsev.util.helpers.SelenideHelper.byTestId;

public class CheckboxElement {

    @Getter static final SelenideElement checkboxPanel = $(byTestId("popupPanel"));

    public static SelenideElement getCheckboxByLabel(String label) {
        return checkboxPanel.$$("label").find(text(label));
    }
}
