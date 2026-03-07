package org.skopintsev.models.gui.common.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$;
import static org.skopintsev.util.helpers.SelenideHelper.byTestId;

public class LoadingSpinner {

    @Getter
    static final SelenideElement pageLoader = $(byTestId("loading-spinner"));
}
